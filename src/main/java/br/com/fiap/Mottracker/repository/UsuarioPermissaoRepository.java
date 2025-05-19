package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.model.UsuarioPermissaoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, UsuarioPermissaoId> {

    Page<UsuarioPermissao> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<UsuarioPermissao> findByPermissaoId(Long permissaoId, Pageable pageable);

    Optional<UsuarioPermissao> findByUsuarioIdAndPermissaoId(Long usuarioId, Long permissaoId);
}
