package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.MotoRequestDto;
import br.com.fiap.Mottracker.model.Moto;
import br.com.fiap.Mottracker.service.MotoService;
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
@RequestMapping("/motos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Motos", description = "Operações relacionadas as motos")
public class MotoController {

    private final MotoService motoService;

    @GetMapping
    @Operation(summary = "Listar todas as motos com paginação")
    @Cacheable(value = "motos")
    public ResponseEntity<Page<Moto>> getAll(
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"placaMoto,asc"` ou `"idMoto,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["placaMoto,desc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "idMoto") Pageable pageable) {
        return ResponseEntity.ok(motoService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moto por ID")
    public ResponseEntity<Moto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.getById(id));
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar moto por placa")
    public ResponseEntity<Moto> getByPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(motoService.getByPlaca(placa));
    }

    @GetMapping("/estado")
    @Operation(summary = "Listar motos por estado com paginação")
    public ResponseEntity<Page<Moto>> getByEstado(
            @RequestParam String estado,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"placaMoto,asc"` ou `"idMoto,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["placaMoto,desc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(motoService.getByEstado(estado, pageable));
    }

    @GetMapping("/contrato/{contratoId}")
    @Operation(summary = "Listar motos por ID de contrato com paginação")
    public ResponseEntity<Page<Moto>> getByContratoId(
            @PathVariable Long contratoId,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"placaMoto,asc"` ou `"idMoto,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["placaMoto,desc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(motoService.getByContratoId(contratoId, pageable));
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova moto", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "placaMoto": "ABC1234",
                              "modeloMoto": "Honda CG 160",
                              "anoMoto": 2022,
                              "identificadorMoto": "ID123456",
                              "quilometragemMoto": 12000,
                              "estadoMoto": "DISPONIVEL",
                              "condicoesMoto": "Em perfeito estado",
                              "contratoMotoId": 1001,
                              "motoPatioAtualId": 2001,
                              "motoPatioOrigemId": 1999
                            }
                            """)
            )
    ))
    @CacheEvict(value = "motos", allEntries = true)
    public ResponseEntity<Moto> create(@Valid @RequestBody MotoRequestDto dto) {
        Moto criado = motoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados da moto", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para atualização",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "placaMoto": "XYZ5678",
                              "modeloMoto": "Yamaha Fazer 250",
                              "anoMoto": 2023,
                              "identificadorMoto": "ID654321",
                              "quilometragemMoto": 8000,
                              "estadoMoto": "MANUTENCAO",
                              "condicoesMoto": "Revisão agendada",
                              "contratoMotoId": 1002,
                              "motoPatioAtualId": 2002,
                              "motoPatioOrigemId": 2000
                            }
                            """)
            )
    ))
    @CacheEvict(value = "motos", allEntries = true)
    public ResponseEntity<Moto> update(@PathVariable Long id, @Valid @RequestBody MotoRequestDto dto) {
        Moto atualizado = motoService.update(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizado);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir moto por ID")
    @CacheEvict(value = "motos", allEntries = true)
    public void delete(@PathVariable Long id) {
        motoService.delete(id);
    }
}
