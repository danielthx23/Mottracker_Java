package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.QrCodePontoRequestDto;
import br.com.fiap.Mottracker.model.QrCodePonto;
import br.com.fiap.Mottracker.service.QrCodePontoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrcodes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "QR Codes", description = "Operações relacionadas aos pontos de QR Code no layout do pátio")
public class QrCodePontoController {

    private final QrCodePontoService qrCodePontoService;

    @GetMapping
    @Operation(summary = "Listar todos os QR Codes com paginação")
    @Cacheable(value = "qrcodespontos")
    public ResponseEntity<Page<QrCodePonto>> getAll(
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"idQrCodePonto,asc"` ou `"posX,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["idQrCodePonto,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "idQrCodePonto") Pageable pageable) {
        return ResponseEntity.ok(qrCodePontoService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar QR Code por ID")
    public ResponseEntity<QrCodePonto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(qrCodePontoService.getById(id));
    }

    @GetMapping("/identificador")
    @Operation(summary = "Buscar QR Code pelo identificador")
    public ResponseEntity<QrCodePonto> getByIdentificador(@RequestParam String identificador) {
        return ResponseEntity.ok(qrCodePontoService.getByIdentificador(identificador));
    }

    @GetMapping("/layout")
    @Operation(summary = "Listar QR Codes por ID do layout com paginação")
    public ResponseEntity<Page<QrCodePonto>> getByLayoutPatioId(
            @RequestParam Long layoutPatioId,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"posX,asc"` ou `"posY,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["posX,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(qrCodePontoService.findByLayoutPatioId(layoutPatioId, pageable));
    }

    @GetMapping("/posx")
    @Operation(summary = "Buscar QR Codes por intervalo de posição X")
    public ResponseEntity<Page<QrCodePonto>> getByPosXBetween(
            @RequestParam float start,
            @RequestParam float end,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"posX,asc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["posX,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(qrCodePontoService.findByPosXBetween(start, end, pageable));
    }

    @GetMapping("/posy")
    @Operation(summary = "Buscar QR Codes por intervalo de posição Y")
    public ResponseEntity<Page<QrCodePonto>> getByPosYBetween(
            @RequestParam float start,
            @RequestParam float end,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"posY,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["posY,desc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(qrCodePontoService.findByPosYBetween(start, end, pageable));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo QR Code", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para criar QR Code",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "identificadorQrCode": "QR-12345",
                              "posX": 0.5,
                              "posY": 0.75,
                              "layoutPatioId": 1
                            }
                            """)
            )
    ))
    @CacheEvict(value = "qrcodespontos", allEntries = true)
    public ResponseEntity<QrCodePonto> create(@Valid @RequestBody QrCodePontoRequestDto qrCodePonto) {
        QrCodePonto created = qrCodePontoService.create(qrCodePonto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar QR Code existente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para atualizar QR Code",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "identificadorQrCode": "QR-98765",
                              "posX": 0.2,
                              "posY": 0.4,
                              "layoutPatioId": 1
                            }
                            """)
            )
    ))
    @CacheEvict(value = "qrcodespontos", allEntries = true)
    public ResponseEntity<QrCodePonto> update(@PathVariable Long id, @Valid @RequestBody QrCodePontoRequestDto qrCodePonto) {
        QrCodePonto updated = qrCodePontoService.update(id, qrCodePonto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir QR Code por ID")
    @CacheEvict(value = "qrcodespontos", allEntries = true)
    public void delete(@PathVariable Long id) {
        qrCodePontoService.delete(id);
    }
}
