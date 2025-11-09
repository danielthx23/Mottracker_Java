package br.com.fiap.Mottracker.service;

import br.com.fiap.Mottracker.model.MtAuditoria;
import br.com.fiap.Mottracker.repository.MtAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MtAuditoriaService {

    private final MtAuditoriaRepository auditoriaRepository;

    public Page<MtAuditoria> getAll(Pageable pageable) {
        return auditoriaRepository.findAll(pageable);
    }

    public MtAuditoria getById(Long id) {
        return auditoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auditoria não encontrada com ID: " + id));
    }

    public Page<MtAuditoria> getByTabela(String tabelaNome, Pageable pageable) {
        return auditoriaRepository.findByTabelaNome(tabelaNome, pageable);
    }

    public Page<MtAuditoria> getByTipoOperacao(String tipoOperacao, Pageable pageable) {
        return auditoriaRepository.findByTipoOperacao(tipoOperacao, pageable);
    }

    public Page<MtAuditoria> getByUsuario(String usuarioNome, Pageable pageable) {
        return auditoriaRepository.findByUsuarioNome(usuarioNome, pageable);
    }

    public Page<MtAuditoria> getByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        Date inicio = Date.from(dataInicio.atZone(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(dataFim.atZone(ZoneId.systemDefault()).toInstant());
        return auditoriaRepository.findByDataHoraBetween(inicio, fim, pageable);
    }

    public List<MtAuditoria> getByTabelaAndOperacao(String tabela, String operacao) {
        return auditoriaRepository.findByTabelaAndOperacao(tabela, operacao);
    }
}

