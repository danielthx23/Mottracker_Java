package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.dto.EnderecoRequestDto;
import br.com.fiap.Mottracker.model.Endereco;
import br.com.fiap.Mottracker.model.Patio;
import br.com.fiap.Mottracker.repository.EnderecoRepository;
import br.com.fiap.Mottracker.repository.PatioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PatioRepository patioRepository;

    public Page<Endereco> getAll(Pageable pageable) {
        return enderecoRepository.findAll(pageable);
    }

    public Endereco getById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));
    }

    public Page<Endereco> findByCidade(String cidade, Pageable pageable) {
        return enderecoRepository.findByCidade(cidade, pageable);
    }

    public Page<Endereco> findByCidadeAndEstado(String cidade, String estado, Pageable pageable) {
        return enderecoRepository.findByCidadeAndEstado(cidade, estado, pageable);
    }

    public Page<Endereco> findByEstado(String estado, Pageable pageable) {
        return enderecoRepository.findByEstado(estado, pageable);
    }

    public Endereco findByCep(String cep) {
        return enderecoRepository.findByCep(cep);
    }

    public Page<Endereco> findByBairro(String bairro, Pageable pageable) {
        return enderecoRepository.findByBairro(bairro, pageable);
    }

    public Page<Endereco> findByLogradouroContaining(String logradouro, Pageable pageable) {
        return enderecoRepository.findByLogradouroContainingIgnoreCase(logradouro, pageable);
    }

    public Endereco findByPatioId(Long patioId) {
        return enderecoRepository.findByPatioEndereco_IdPatio(patioId);
    }

    public Endereco create(EnderecoRequestDto dto) {
        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.patioId()));

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setReferencia(dto.referencia());
        endereco.setPatioEndereco(patio);

        return enderecoRepository.save(endereco);
    }

    public Endereco update(Long id, EnderecoRequestDto dto) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));

        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.patioId()));

        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setReferencia(dto.referencia());
        endereco.setPatioEndereco(patio);

        return enderecoRepository.save(endereco);
    }

    public void delete(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}
