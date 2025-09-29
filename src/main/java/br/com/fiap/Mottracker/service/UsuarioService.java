2package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.TelefoneRequestDto;
import br.com.fiap.Mottracker.dto.UsuarioLoginDto;
import br.com.fiap.Mottracker.dto.UsuarioRequestDto;
import br.com.fiap.Mottracker.model.Telefone;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.model.Permissao;
import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.model.UsuarioPermissaoId;
import br.com.fiap.Mottracker.repository.TelefoneRepository;
import br.com.fiap.Mottracker.repository.UsuarioRepository;
import br.com.fiap.Mottracker.repository.PermissaoRepository;
import br.com.fiap.Mottracker.repository.UsuarioPermissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissaoRepository permissaoRepository;
    private final UsuarioPermissaoRepository usuarioPermissaoRepository;

    public Page<Usuario> getAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Usuario create(UsuarioRequestDto dto) {
        Usuario usuario = fromDto(dto);
        usuario.setCriadoEmUsuario(LocalDateTime.now());
        // Encode password before saving
        usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
        Usuario salvo = usuarioRepository.save(usuario);
        salvarTelefones(dto.telefones(), salvo);
        
        // Associate all permissions for testing
        associarTodasPermissoes(salvo);
        
        return salvo;
    }

    public Usuario update(Long id, UsuarioRequestDto dto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        existente.setNomeUsuario(dto.nomeUsuario());
        existente.setCpfUsuario(dto.cpfUsuario());
        // Encode password before saving
        existente.setSenhaUsuario(passwordEncoder.encode(dto.senhaUsuario()));
        existente.setCnhUsuario(dto.cnhUsuario());
        existente.setEmailUsuario(dto.emailUsuario());
        existente.setDataNascimentoUsuario(dto.dataNascimentoUsuario());

        Usuario atualizado = usuarioRepository.save(existente);

        telefoneRepository.deleteAll(existente.getTelefones());
        salvarTelefones(dto.telefones(), atualizado);

        return atualizado;
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private Usuario fromDto(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(dto.nomeUsuario());
        usuario.setCpfUsuario(dto.cpfUsuario());
        usuario.setSenhaUsuario(dto.senhaUsuario());
        usuario.setCnhUsuario(dto.cnhUsuario());
        usuario.setEmailUsuario(dto.emailUsuario());
        usuario.setDataNascimentoUsuario(dto.dataNascimentoUsuario());
        return usuario;
    }

    private void salvarTelefones(List<TelefoneRequestDto> telefonesDto, Usuario usuario) {
        if (telefonesDto == null) return;

        List<Telefone> telefones = telefonesDto.stream().map(tDto -> {
            Telefone tel = new Telefone();
            tel.setNumero(tDto.numero());
            tel.setTipo(tDto.tipo());
            tel.setUsuario(usuario);
            return tel;
        }).collect(Collectors.toList());

        telefoneRepository.saveAll(telefones);
        usuario.setTelefones(telefones);
    }

    public Usuario login(UsuarioLoginDto loginDto) {
        return usuarioRepository.findByEmailUsuario(loginDto.emailUsuario())
                .filter(u -> passwordEncoder.matches(loginDto.senhaUsuario(), u.getSenhaUsuario()))
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailUsuario(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        // Força o carregamento das permissões
        usuario.getUsuarioPermissoes().size();
        
        List<GrantedAuthority> authorities = usuario.getUsuarioPermissoes().stream()
                .map(up -> new SimpleGrantedAuthority("ROLE_" + up.getPermissao().getNomePermissao()))
                .collect(Collectors.toList());

        return new User(usuario.getEmailUsuario(), usuario.getSenhaUsuario(), authorities);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmailUsuario(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private void associarPermissaoUsuario(Usuario usuario, String nomePermissao) {
        try {
            Permissao permissao = permissaoRepository.findByNomePermissao(nomePermissao)
                    .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada: " + nomePermissao));
            
            UsuarioPermissaoId id = new UsuarioPermissaoId(usuario.getIdUsuario(), permissao.getIdPermissao());
            
            if (!usuarioPermissaoRepository.existsById(id)) {
                UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
                usuarioPermissao.setUsuarioId(usuario.getIdUsuario());
                usuarioPermissao.setPermissaoId(permissao.getIdPermissao());
                usuarioPermissao.setPapel(nomePermissao);
                
                usuarioPermissaoRepository.save(usuarioPermissao);
            }
        } catch (Exception e) {
            System.err.println("Erro ao associar permissão: " + e.getMessage());
        }
    }

    private void associarTodasPermissoes(Usuario usuario) {
        try {
            // Lista de todas as permissões disponíveis
            String[] permissoes = {"ADMIN", "GERENTE", "OPERADOR", "USER"};
            
            for (String permissao : permissoes) {
                associarPermissaoUsuario(usuario, permissao);
            }
            
            System.out.println("Todas as permissões associadas ao usuário: " + usuario.getEmailUsuario());
        } catch (Exception e) {
            System.err.println("Erro ao associar todas as permissões: " + e.getMessage());
        }
    }
}
