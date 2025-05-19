package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.TelefoneRequestDto;
import br.com.fiap.Mottracker.dto.UsuarioLoginDto;
import br.com.fiap.Mottracker.dto.UsuarioRequestDto;
import br.com.fiap.Mottracker.model.Telefone;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.repository.TelefoneRepository;
import br.com.fiap.Mottracker.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;

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
        Usuario salvo = usuarioRepository.save(usuario);
        salvarTelefones(dto.telefones(), salvo);
        return salvo;
    }

    public Usuario update(Long id, UsuarioRequestDto dto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        existente.setNomeUsuario(dto.nomeUsuario());
        existente.setCpfUsuario(dto.cpfUsuario());
        existente.setSenhaUsuario(dto.senhaUsuario());
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
                .filter(u -> u.getSenhaUsuario().equals(loginDto.senhaUsuario()))
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));
    }
}
