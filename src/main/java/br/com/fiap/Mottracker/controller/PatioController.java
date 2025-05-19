package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.PatioRequestDto;
import br.com.fiap.Mottracker.model.Patio;
import br.com.fiap.Mottracker.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/patios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Patios", description = "Operações relacionadas aos pátios")
public class PatioController {

    private final PatioService patioService;

    @GetMapping
    @Operation(summary = "Listar todos os pátios com paginação")
    @Cacheable(value = "patios")
    public ResponseEntity<Page<Patio>> getAll(
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
                  "sort": ["nomePatio,asc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "idPatio") Pageable pageable) {
        return ResponseEntity.ok(patioService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pátio por ID")
    public ResponseEntity<Patio> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patioService.getById(id));
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar pátios por nome com paginação")
    public ResponseEntity<Page<Patio>> findByNome(
            @RequestParam String nome,
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
                  "sort": ["nomePatio,asc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patioService.findByNomePatioContainingIgnoreCase(nome, pageable));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Buscar pátios com motos disponíveis acima de um número")
    public ResponseEntity<Page<Patio>> findByMotosDisponiveisGreaterThan(
            @RequestParam int quantidade,
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
                  "sort": ["nomePatio,asc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patioService.findByMotosDisponiveisPatioGreaterThan(quantidade, pageable));
    }

    @GetMapping("/data-after")
    @Operation(summary = "Buscar pátios cadastrados após uma data")
    public ResponseEntity<Page<Patio>> findByDataAfter(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data,
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
                  "sort": ["nomePatio,asc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patioService.findByDataPatioAfter(data, pageable));
    }

    @GetMapping("/data-before")
    @Operation(summary = "Buscar pátios cadastrados antes de uma data")
    public ResponseEntity<Page<Patio>> findByDataBefore(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data,
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
                  "sort": ["nomePatio,asc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(patioService.findByDataPatioBefore(data, pageable));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo pátio", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para criar pátio",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "nomePatio": "Pátio Central",
                              "motosTotaisPatio": 100,
                              "motosDisponiveisPatio": 75,
                              "dataPatio": "2025-05-19T12:58:49.709Z",
                              "layoutPatio": {
                                "descricao": "Layout padrão",
                                "largura": 50,
                                "comprimento": 100,
                                "altura": 5,
                                "patioId": 1,
                                "qrCodesLayoutPatio": [
                                  {
                                    "identificadorQrCode": "QR001",
                                    "posX": 1.5,
                                    "posY": 2.5,
                                    "layoutPatioId": 1
                                  }
                                ]
                              },
                              "enderecoPatio": {
                                "logradouro": "Rua das Flores",
                                "numero": "123",
                                "complemento": "Bloco A",
                                "bairro": "Centro",
                                "cidade": "São Paulo",
                                "estado": "SP",
                                "cep": "01001-000",
                                "referencia": "Próximo ao metrô",
                                "patioId": 1
                              },
                              "camerasPatio": [
                                {
                                  "nomeCamera": "Cam01",
                                  "ipCamera": "192.168.0.10",
                                  "status": "ATIVA",
                                  "posX": 10.0,
                                  "posY": 20.0,
                                  "patioId": 1
                                }
                              ],
                              "motosPatioAtual": [
                                {
                                  "placaMoto": "ABC1234",
                                  "modeloMoto": "Honda CG 160",
                                  "anoMoto": 2022,
                                  "identificadorMoto": "MOT123456",
                                  "quilometragemMoto": 15000,
                                  "estadoMoto": "DISPONIVEL",
                                  "condicoesMoto": "Nova",
                                  "contratoMotoId": 101,
                                  "motoPatioAtualId": 1,
                                  "motoPatioOrigemId": 2
                                }
                              ]
                            }
                            """)
            )
    ))
    @CacheEvict(value = "patios", allEntries = true)
    public ResponseEntity<Patio> create(@Valid @RequestBody PatioRequestDto patio) {
        Patio criado = patioService.create(patio);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do pátio", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para atualizar pátio",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "nomePatio": "Pátio Norte",
                              "motosTotaisPatio": 120,
                              "motosDisponiveisPatio": 90,
                              "dataPatio": "2025-05-20T10:30:00Z",
                              "layoutPatio": {
                                "descricao": "Layout expandido",
                                "largura": 60,
                                "comprimento": 120,
                                "altura": 6,
                                "patioId": 2,
                                "qrCodesLayoutPatio": [
                                  {
                                    "identificadorQrCode": "QR002",
                                    "posX": 3.0,
                                    "posY": 4.0,
                                    "layoutPatioId": 2
                                  }
                                ]
                              },
                              "enderecoPatio": {
                                "logradouro": "Av. Brasil",
                                "numero": "456",
                                "complemento": "Galpão B",
                                "bairro": "Industrial",
                                "cidade": "Campinas",
                                "estado": "SP",
                                "cep": "13050-000",
                                "referencia": "Próximo ao terminal",
                                "patioId": 2
                              },
                              "camerasPatio": [
                                {
                                  "nomeCamera": "Cam02",
                                  "ipCamera": "192.168.0.20",
                                  "status": "ATIVA",
                                  "posX": 15.0,
                                  "posY": 25.0,
                                  "patioId": 2
                                }
                              ],
                              "motosPatioAtual": [
                                {
                                  "placaMoto": "XYZ5678",
                                  "modeloMoto": "Yamaha Fazer 250",
                                  "anoMoto": 2023,
                                  "identificadorMoto": "MOT654321",
                                  "quilometragemMoto": 8000,
                                  "estadoMoto": "MANUTENCAO",
                                  "condicoesMoto": "Necessita revisão",
                                  "contratoMotoId": 102,
                                  "motoPatioAtualId": 2,
                                  "motoPatioOrigemId": 3
                                }
                              ]
                            }
                            """)
            )
    ))
    @CacheEvict(value = "patios", allEntries = true)
    public ResponseEntity<Patio> update(@PathVariable Long id, @Valid @RequestBody PatioRequestDto patio) {
        Patio atualizado = patioService.update(id, patio);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizado);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pátio por ID")
    @CacheEvict(value = "patios", allEntries = true)
    public void delete(@PathVariable Long id) {
        patioService.delete(id);
    }
}
