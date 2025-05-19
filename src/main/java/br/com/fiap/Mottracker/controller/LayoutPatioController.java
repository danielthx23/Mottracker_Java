package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.LayoutPatioRequestDto;
import br.com.fiap.Mottracker.model.LayoutPatio;
import br.com.fiap.Mottracker.service.LayoutPatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/layoutpatios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "LayoutPatios", description = "Operações relacionadas aos layouts de patios")
public class LayoutPatioController {

    private final LayoutPatioService layoutPatioService;

    @GetMapping
    @Cacheable(value = "layoutpatios")
    @Operation(summary = "Listar todos os layouts de patios")
    public ResponseEntity<Page<LayoutPatio>> getAll(@Parameter(
            description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nomePatio,asc"` ou `"idPatio,desc"`)
                """,
            examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idLayoutPatio,desc"]
                }
                """)
    )
            @PageableDefault(size = 10, sort = "idLayoutPatio") Pageable pageable) {
        return ResponseEntity.ok(layoutPatioService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar layout patio por ID")
    public ResponseEntity<LayoutPatio> getById(@PathVariable Long id) {
        return ResponseEntity.ok(layoutPatioService.getById(id));
    }

    @GetMapping("/patio/{patioId}")
    @Operation(summary = "Buscar layout patio por ID do patio com paginação")
    public ResponseEntity<Page<LayoutPatio>> getByPatioId(
            @PathVariable Long patioId,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nomePatio,asc"` ou `"idPatio,desc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idLayoutPatio,desc"]
                }
                """))
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(layoutPatioService.findByPatioId(patioId, pageable));
    }

    @GetMapping("/data-criacao")
    @Operation(summary = "Buscar layout patio entre datas com paginação")
    public ResponseEntity<Page<LayoutPatio>> getByDataCriacaoBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nomePatio,asc"` ou `"idPatio,desc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idLayoutPatio,desc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(layoutPatioService.findByDataCriacaoBetween(start, end, pageable));
    }

    @PostMapping
    @CacheEvict(value = "layoutpatios", allEntries = true)
    @Operation(summary = "Criar novo LayoutPatio")
    @RequestBody(
            description = "Dados do layout do pátio a ser criado",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                {
                  "descricao": "Layout padrão do pátio central",
                  "largura": 20,
                  "comprimento": 50,
                  "altura": 5,
                  "patioId": 1,
                  "qrCodesLayoutPatio": [
                    {
                      "identificadorQrCode": "QR123456789",
                      "posX": 0.25,
                      "posY": 0.75
                    },
                    {
                      "identificadorQrCode": "QR987654321",
                      "posX": 0.75,
                      "posY": 0.25
                    }
                  ]
                }
                """
                    )
            )
    )
    public ResponseEntity<LayoutPatio> create(@Valid @RequestBody LayoutPatioRequestDto layoutPatio) {
        LayoutPatio criado = layoutPatioService.create(layoutPatio);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "layoutpatios", allEntries = true)
    @Operation(summary = "Atualizar LayoutPatio existente")
    @RequestBody(
            description = "Dados atualizados do layout do pátio",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                {
                  "descricao": "Layout atualizado do pátio central",
                  "largura": 22,
                  "comprimento": 55,
                  "altura": 6,
                  "patioId": 1,
                  "qrCodesLayoutPatio": [
                    {
                      "identificadorQrCode": "QR123456789",
                      "posX": 0.30,
                      "posY": 0.80
                    }
                  ]
                }
                """
                    )
            )
    )
    public ResponseEntity<LayoutPatio> update(@PathVariable Long id, @Valid @RequestBody LayoutPatioRequestDto layoutPatio) {
        LayoutPatio atualizado = layoutPatioService.update(id, layoutPatio);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizado);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleter layout patio por Id")
    @CacheEvict(value = "layoutpatios", allEntries = true)
    public void delete(@PathVariable Long id) {
        layoutPatioService.delete(id);
    }
}
