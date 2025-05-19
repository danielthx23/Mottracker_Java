package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.TelefoneRequestDto;
import br.com.fiap.Mottracker.model.Telefone;
import br.com.fiap.Mottracker.service.TelefoneService;
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
@RequestMapping("/telefones")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Telefones", description = "Gerenciamento de telefones dos usuários")
public class TelefoneController {

    private final TelefoneService telefoneService;

    @GetMapping
    @Operation(summary = "Listar todos os telefones com paginação")
    @Cacheable(value = "telefones")
    public ResponseEntity<Page<Telefone>> getAll(
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"` (ex: `"idTelefone,asc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["idTelefone,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "idTelefone") Pageable pageable) {
        return ResponseEntity.ok(telefoneService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar telefone por ID")
    public ResponseEntity<Telefone> getById(@PathVariable Long id) {
        return ResponseEntity.ok(telefoneService.getById(id));
    }

    @GetMapping("/numero")
    @Operation(summary = "Buscar telefones por número")
    public ResponseEntity<Page<Telefone>> getByNumero(
            @RequestParam String numero,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"` (ex: `"idTelefone,asc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["idTelefone,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(telefoneService.getByNumero(numero, pageable));
    }

    @GetMapping("/usuario")
    @Operation(summary = "Buscar telefones por ID de usuário")
    public ResponseEntity<Page<Telefone>> getByUsuarioId(
            @RequestParam Long usuarioId,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"` (ex: `"idTelefone,asc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["idTelefone,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(telefoneService.findByUsuarioId(usuarioId, pageable));
    }

    @GetMapping("/tipo")
    @Operation(summary = "Buscar telefones por tipo (ex: celular, fixo, etc.)")
    public ResponseEntity<Page<Telefone>> getByTipo(
            @RequestParam String tipo,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"` (ex: `"idTelefone,asc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["idTelefone,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(telefoneService.findByTipo(tipo, pageable));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo telefone", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de requisição para criar telefone",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "numero": "(11) 91234-5678",
                              "tipo": "celular",
                              "usuarioId": 1
                            }
                            """)
            )
    ))
    @CacheEvict(value = "telefones", allEntries = true)
    public ResponseEntity<Telefone> create(@Valid @RequestBody TelefoneRequestDto telefone) {
        Telefone created = telefoneService.create(telefone);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar telefone existente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de requisição para atualizar telefone",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "numero": "(11) 99876-5432",
                              "tipo": "residencial",
                              "usuarioId": 123456789
                            }
                            """)
            )
    ))
    @CacheEvict(value = "telefones", allEntries = true)
    public ResponseEntity<Telefone> update(@PathVariable Long id, @Valid @RequestBody TelefoneRequestDto telefone) {
        Telefone updated = telefoneService.update(id, telefone);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir telefone por ID")
    @CacheEvict(value = "telefones", allEntries = true)
    public void delete(@PathVariable Long id) {
        telefoneService.delete(id);
    }
}
