package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Telefone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    Page<Telefone> findByNumero(String numero, Pageable pageable);

    Page<Telefone> findByUsuario_IdUsuario(Long usuarioId, Pageable pageable);

    Page<Telefone> findByTipoIgnoreCase(String tipo, Pageable pageable);
}

