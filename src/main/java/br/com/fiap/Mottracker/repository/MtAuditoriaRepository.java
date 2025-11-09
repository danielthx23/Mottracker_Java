package br.com.fiap.Mottracker.repository;

import br.com.fiap.Mottracker.model.MtAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MtAuditoriaRepository extends JpaRepository<MtAuditoria, Long> {
    
    Page<MtAuditoria> findByTabelaNome(String tabelaNome, Pageable pageable);
    
    Page<MtAuditoria> findByTipoOperacao(String tipoOperacao, Pageable pageable);
    
    Page<MtAuditoria> findByUsuarioNome(String usuarioNome, Pageable pageable);
    
    @Query("SELECT a FROM MtAuditoria a WHERE a.dataHora BETWEEN :dataInicio AND :dataFim")
    Page<MtAuditoria> findByDataHoraBetween(
        @Param("dataInicio") Date dataInicio,
        @Param("dataFim") Date dataFim,
        Pageable pageable
    );
    
    @Query("SELECT a FROM MtAuditoria a WHERE a.tabelaNome = :tabela AND a.tipoOperacao = :operacao")
    List<MtAuditoria> findByTabelaAndOperacao(@Param("tabela") String tabela, @Param("operacao") String operacao);
}

