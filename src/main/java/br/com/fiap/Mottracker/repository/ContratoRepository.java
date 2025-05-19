package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.Contrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    Page<Contrato> findByAtivoContrato(Integer ativoContrato, Pageable pageable);

    Page<Contrato> findByUsuarioContrato_IdUsuario(Long usuarioId, Pageable pageable);

    Page<Contrato> findByMotoContrato_IdMoto(Long motoId, Pageable pageable);

    Page<Contrato> findByDataDeExpiracaoContratoAfter(LocalDateTime now, Pageable pageable);

    Page<Contrato> findByRenovacaoAutomaticaContrato(Integer renovacaoAutomatica, Pageable pageable);

    Page<Contrato> findByDataDeEntradaContratoBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}

