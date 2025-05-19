package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {

    Optional<Moto> findByPlacaMoto(String placaMoto);

    Page<Moto> findByEstadoMoto(String estadoMoto, Pageable pagable);

    Page<Moto> findByContratoMoto_IdContrato(Long contratoId, Pageable pagable);

}
