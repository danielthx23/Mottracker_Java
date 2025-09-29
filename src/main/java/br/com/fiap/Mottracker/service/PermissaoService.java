package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.PermissaoRequestDto;
import br.com.fiap.Mottracker.model.Permissao;
import br.com.fiap.Mottracker.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public Page<Permissao> getAll(Pageable pageable) {
        return permissaoRepository.findAll(pageable);
    }

    public List<Permissao> getAll() {
        return permissaoRepository.findAll();
    }

    public Permissao getById(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permissão não encontrada com ID: " + id));
    }

    public Page<Permissao> findByNomePermissaoContaining(String nomePermissao, Pageable pageable) {
        return permissaoRepository.findByNomePermissaoContainingIgnoreCase(nomePermissao, pageable);
    }

    public Page<Permissao> findByDescricaoContaining(String descricao, Pageable pageable) {
        return permissaoRepository.findByDescricaoContainingIgnoreCase(descricao, pageable);
    }

    public Permissao create(PermissaoRequestDto dto) {
        Permissao permissao = fromDto(dto);
        return permissaoRepository.save(permissao);
    }

    public Permissao update(Long id, PermissaoRequestDto dto) {
        if (!permissaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Permissão não encontrada com ID: " + id);
        }
        Permissao permissao = fromDto(dto);
        permissao.setIdPermissao(id);
        return permissaoRepository.save(permissao);
    }

    public void delete(Long id) {
        if (!permissaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Permissão não encontrada com ID: " + id);
        }
        permissaoRepository.deleteById(id);
    }

    public Permissao fromDto(PermissaoRequestDto dto) {
        Permissao permissao = new Permissao();
        permissao.setNomePermissao(dto.nomePermissao());
        permissao.setDescricao(dto.descricao());
        return permissao;
    }
}
