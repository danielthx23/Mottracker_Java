package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.ContratoRequestDto;
import br.com.fiap.Mottracker.model.Contrato;
import br.com.fiap.Mottracker.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/contratos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Contratos", description = "Operações relacionadas aos contratos de aluguel de motos")
public class ContratoController {

    private final ContratoService contratoService;

    @GetMapping
    @Cacheable(value = "contratos")
    @Operation(summary = "Listar todos os contratos")
    public ResponseEntity<Page<Contrato>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contrato por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
            @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Contrato> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(contratoService.getById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar contratos ativos")
    public ResponseEntity<Page<Contrato>> getAtivos(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getAtivos(pageable));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar contratos por ID do usuário")
    public ResponseEntity<Page<Contrato>> getByUsuarioId(
            @PathVariable Long usuarioId,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getByUsuarioId(usuarioId, pageable));
    }

    @GetMapping("/moto/{motoId}")
    @Operation(summary = "Listar contratos por ID da moto")
    public ResponseEntity<Page<Contrato>> getByMotoId(
            @PathVariable Long motoId,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getByMotoId(motoId, pageable));
    }

    @GetMapping("/renovacao")
    @Operation(summary = "Listar contratos com renovação automática")
    public ResponseEntity<Page<Contrato>> getRenovacaoAutomatica(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getRenovacaoAutomatica(pageable));
    }

    @GetMapping("/validos")
    @Operation(summary = "Listar contratos válidos")
    public ResponseEntity<Page<Contrato>> getValidos(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getValidos(pageable));
    }

    @GetMapping("/entrada")
    @Operation(summary = "Buscar contratos por intervalo de datas de entrada")
    public ResponseEntity<Page<Contrato>> getByDataEntrada(
            @Parameter(description = "Data inicial no formato yyyy-MM-dd") @RequestParam("inicio") String inicio,
            @Parameter(description = "Data final no formato yyyy-MM-dd") @RequestParam("fim") String fim,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"dataDeEntradaContrato,desc` ou `"dataDeEntradaContrato,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["dataDeEntradaContrato,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "dataDeEntradaContrato", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contratoService.getByDataEntradaBetween(inicio, fim, pageable));
    }

    @PostMapping
    @CacheEvict(value = "contratos", allEntries = true)
    @Operation(summary = "Criar novo contrato")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do contrato a ser criado",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "usuarioId": 1,
                                      "motoId": 1,
                                      "clausulasContrato": "1,2,3",
                                      "dataDeEntradaContrato": "2025-05-19T11:55:49.270Z",
                                      "horarioDeDevolucaoContrato": "2025-05-19T11:55:49.270Z",
                                      "dataDeExpiracaoContrato": "2025-05-19T11:55:49.270Z",
                                      "renovacaoAutomatica": true,
                                      "valorToralContrato": 0,
                                      "quantidadeParcelas": 1
                                    }
                                    """
                    )
            )
    )
    public ResponseEntity<Contrato> create(@Valid @RequestBody ContratoRequestDto contrato) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.create(contrato));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "contratos", allEntries = true)
    @Operation(summary = "Atualizar contrato existente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do contrato",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                     {
                                      "usuarioId": 1,
                                      "motoId": 1,
                                      "clausulasContrato": "1,2,3",
                                      "dataDeEntradaContrato": "2025-05-19T11:55:49.270Z",
                                      "horarioDeDevolucaoContrato": "2025-05-19T11:55:49.270Z",
                                      "dataDeExpiracaoContrato": "2025-05-19T11:55:49.270Z",
                                      "renovacaoAutomatica": true,
                                      "valorToralContrato": 0,
                                      "quantidadeParcelas": 1
                                    }
                                    """
                    )
            )
    )
    public ResponseEntity<Contrato> update(@PathVariable Long id, @Valid @RequestBody ContratoRequestDto contrato) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.update(id, contrato));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar contrato por ID")
    public void delete(@PathVariable Long id) {
        try {
            contratoService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
