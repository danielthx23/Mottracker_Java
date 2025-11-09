package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.model.MtAuditoria;
import br.com.fiap.Mottracker.service.MtAuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@Tag(name = "Auditoria", description = "Endpoints para consulta de registros de auditoria")
public class AuditoriaController {

    private final MtAuditoriaService auditoriaService;

    @GetMapping
    @Operation(summary = "Lista todos os registros de auditoria", 
               description = "Retorna uma página de registros de auditoria")
    public ResponseEntity<Page<MtAuditoria>> getAll(Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca registro de auditoria por ID")
    public ResponseEntity<MtAuditoria> getById(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.getById(id));
    }

    @GetMapping("/tabela/{tabelaNome}")
    @Operation(summary = "Busca registros de auditoria por tabela")
    public ResponseEntity<Page<MtAuditoria>> getByTabela(
            @PathVariable String tabelaNome, 
            Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.getByTabela(tabelaNome, pageable));
    }

    @GetMapping("/operacao/{tipoOperacao}")
    @Operation(summary = "Busca registros de auditoria por tipo de operação")
    public ResponseEntity<Page<MtAuditoria>> getByTipoOperacao(
            @PathVariable String tipoOperacao, 
            Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.getByTipoOperacao(tipoOperacao, pageable));
    }

    @GetMapping("/usuario/{usuarioNome}")
    @Operation(summary = "Busca registros de auditoria por usuário")
    public ResponseEntity<Page<MtAuditoria>> getByUsuario(
            @PathVariable String usuarioNome, 
            Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.getByUsuario(usuarioNome, pageable));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Busca registros de auditoria por período")
    public ResponseEntity<Page<MtAuditoria>> getByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.getByPeriodo(dataInicio, dataFim, pageable));
    }

    @GetMapping("/tabela/{tabela}/operacao/{operacao}")
    @Operation(summary = "Busca registros de auditoria por tabela e operação")
    public ResponseEntity<List<MtAuditoria>> getByTabelaAndOperacao(
            @PathVariable String tabela,
            @PathVariable String operacao) {
        return ResponseEntity.ok(auditoriaService.getByTabelaAndOperacao(tabela, operacao));
    }
}

