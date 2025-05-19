package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.LayoutPatio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LayoutPatioRepository extends JpaRepository<LayoutPatio, Long> {

    Page<LayoutPatio> findByPatioLayoutPatio_IdPatio(Long patioId, Pageable pageable);

    Page<LayoutPatio> findByDataCriacaoBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
