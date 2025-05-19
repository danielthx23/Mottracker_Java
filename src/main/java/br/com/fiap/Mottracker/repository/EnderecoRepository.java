package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Page<Endereco> findByCidade(String cidade, Pageable pageable);

    Page<Endereco> findByCidadeAndEstado(String cidade, String estado, Pageable pageable);

    Page<Endereco> findByEstado(String estado, Pageable pageable);

    Endereco findByCep(String cep);

    Page<Endereco> findByBairro(String bairro, Pageable pageable);

    Page<Endereco> findByLogradouroContainingIgnoreCase(String logradouro, Pageable pageable);

    Endereco findByPatioEndereco_IdPatio(Long patioId);
}
