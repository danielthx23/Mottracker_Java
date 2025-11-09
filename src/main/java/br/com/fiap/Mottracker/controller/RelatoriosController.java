package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.service.RelatoriosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios usando procedures Oracle")
public class RelatoriosController {

    private final RelatoriosService relatoriosService;

    @GetMapping("/json")
    @Operation(summary = "Gera relatório JSON de motos, contratos e pátios", 
               description = "Utiliza a função criaJson para gerar JSON de 3 tabelas unidas")
    public ResponseEntity<String> gerarJsonRelatorio() {
        try {
            String json = relatoriosService.gerarJsonRelatorio();
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/motos-por-patio")
    @Operation(summary = "Gera relatório de motos por pátio", 
               description = "Utiliza a procedure proc_motos_p_patio para gerar relatório de quilometragem por pátio e contrato")
    public ResponseEntity<List<Map<String, Object>>> gerarRelatorioMotosPorPatio() {
        try {
            List<Map<String, Object>> relatorio = relatoriosService.gerarRelatorioMotosPorPatio();
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/json/package")
    @Operation(summary = "Gera relatório JSON usando package", 
               description = "Utiliza pkg_relatorios.proc_json para gerar JSON")
    public ResponseEntity<String> gerarJsonRelatorioFromPackage() {
        try {
            String json = relatoriosService.gerarJsonRelatorioFromPackage();
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}

