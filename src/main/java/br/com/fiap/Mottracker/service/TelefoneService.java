package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.TelefoneRequestDto;
import br.com.fiap.Mottracker.model.Telefone;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.repository.TelefoneRepository;
import br.com.fiap.Mottracker.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<Telefone> getAll(Pageable pageable) {
        return telefoneRepository.findAll(pageable);
    }

    public Telefone getById(Long id) {
        return telefoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Telefone não encontrado com ID: " + id));
    }

    public Page<Telefone> getByNumero(String numero, Pageable pageable) {
        return telefoneRepository.findByNumero(numero, pageable);
    }

    public Page<Telefone> findByUsuarioId(Long usuarioId, Pageable pageable) {
        return telefoneRepository.findByUsuario_IdUsuario(usuarioId, pageable);
    }

    public Page<Telefone> findByTipo(String tipo, Pageable pageable) {
        return telefoneRepository.findByTipoIgnoreCase(tipo, pageable);
    }

    public Telefone create(TelefoneRequestDto dto) {
        Telefone telefone = fromDto(dto);
        return telefoneRepository.save(telefone);
    }

    public Telefone update(Long id, TelefoneRequestDto dto) {
        if (!telefoneRepository.existsById(id)) {
            throw new EntityNotFoundException("Telefone não encontrado com ID: " + id);
        }
        Telefone telefone = fromDto(dto);
        telefone.setIdTelefone(id);
        return telefoneRepository.save(telefone);
    }

    public void delete(Long id) {
        if (!telefoneRepository.existsById(id)) {
            throw new EntityNotFoundException("Telefone não encontrado com ID: " + id);
        }
        telefoneRepository.deleteById(id);
    }

    private Telefone fromDto(TelefoneRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.usuarioId()));

        Telefone telefone = new Telefone();
        telefone.setNumero(dto.numero());
        telefone.setTipo(dto.tipo());
        telefone.setUsuario(usuario);
        return telefone;
    }
}
