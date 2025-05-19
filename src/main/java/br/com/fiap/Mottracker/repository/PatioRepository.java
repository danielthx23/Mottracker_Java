package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Patio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {

    Page<Patio> findByNomePatioContainingIgnoreCase(String nomePatio, Pageable pageable);

    Page<Patio> findByMotosDisponiveisPatioGreaterThan(int quantidade, Pageable pageable);

    Page<Patio> findByDataPatioAfter(LocalDateTime data, Pageable pageable);

    Page<Patio> findByDataPatioBefore(LocalDateTime data, Pageable pageable);
}
