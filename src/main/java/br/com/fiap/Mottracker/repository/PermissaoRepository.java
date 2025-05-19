package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Permissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Page<Permissao> findByNomePermissaoContainingIgnoreCase(String nomePermissao, Pageable pageable);

    Page<Permissao> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
