package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.UsuarioPermissaoRequestDto;
import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.model.UsuarioPermissaoId;
import br.com.fiap.Mottracker.repository.UsuarioPermissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioPermissaoService {

    private final UsuarioPermissaoRepository usuarioPermissaoRepository;

    public Page<UsuarioPermissao> getAll(Pageable pageable) {
        return usuarioPermissaoRepository.findAll(pageable);
    }

    public UsuarioPermissao getById(Long usuarioId, Long permissaoId) {
        return usuarioPermissaoRepository.findById(new UsuarioPermissaoId(usuarioId, permissaoId))
                .orElseThrow(() -> new EntityNotFoundException("Permissão de usuário não encontrada"));
    }

    public UsuarioPermissao create(UsuarioPermissaoRequestDto dto) {
        UsuarioPermissaoId id = new UsuarioPermissaoId(dto.usuarioId(), dto.permissaoId());
        if (usuarioPermissaoRepository.existsById(id)) {
            throw new IllegalStateException("Permissão de usuário já existe");
        }

        UsuarioPermissao up = new UsuarioPermissao();
        up.setUsuarioId(dto.usuarioId());
        up.setPermissaoId(dto.permissaoId());
        up.setPapel(dto.papel());

        return usuarioPermissaoRepository.save(up);
    }

    public UsuarioPermissao update(Long usuarioId, Long permissaoId, UsuarioPermissaoRequestDto dto) {
        UsuarioPermissaoId id = new UsuarioPermissaoId(usuarioId, permissaoId);
        return usuarioPermissaoRepository.findById(id)
                .map(existing -> {
                    existing.setPapel(dto.papel());
                    return usuarioPermissaoRepository.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Permissão de usuário não encontrada"));
    }

    public void delete(Long usuarioId, Long permissaoId) {
        UsuarioPermissaoId id = new UsuarioPermissaoId(usuarioId, permissaoId);
        if (!usuarioPermissaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Permissão de usuário não encontrada");
        }
        usuarioPermissaoRepository.deleteById(id);
    }

    public Page<UsuarioPermissao> findByUsuarioId(Long usuarioId, Pageable pageable) {
        return usuarioPermissaoRepository.findByUsuarioId(usuarioId, pageable);
    }

    public Page<UsuarioPermissao> findByPermissaoId(Long permissaoId, Pageable pageable) {
        return usuarioPermissaoRepository.findByPermissaoId(permissaoId, pageable);
    }

    public UsuarioPermissao getByUsuarioIdAndPermissaoId(Long usuarioId, Long permissaoId) {
        return usuarioPermissaoRepository.findByUsuarioIdAndPermissaoId(usuarioId, permissaoId)
                .orElseThrow(() -> new EntityNotFoundException("Permissão de usuário não encontrada"));
    }
}