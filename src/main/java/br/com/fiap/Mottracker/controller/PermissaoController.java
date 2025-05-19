package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.PermissaoRequestDto;
import br.com.fiap.Mottracker.model.Permissao;
import br.com.fiap.Mottracker.service.PermissaoService;
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
@RequestMapping("/permissoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Permissões", description = "Operações relacionadas às permissões")
public class PermissaoController {

    private final PermissaoService permissaoService;

    @GetMapping
    @Operation(summary = "Listar todas as permissões com paginação")
    @Cacheable(value = "permissoes")
    public ResponseEntity<Page<Permissao>> getAll(
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nomePermissao,asc"` ou `"idPermissao,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["nomePermissao,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "idPermissao") Pageable pageable) {
        return ResponseEntity.ok(permissaoService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar permissão por ID")
    public ResponseEntity<Permissao> getById(@PathVariable Long id) {
        return ResponseEntity.ok(permissaoService.getById(id));
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar permissões por nome com paginação")
    public ResponseEntity<Page<Permissao>> findByNomePermissao(
            @RequestParam String nome,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"nomePermissao,asc"` ou `"idPermissao,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["nomePermissao,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(permissaoService.findByNomePermissaoContaining(nome, pageable));
    }

    @GetMapping("/descricao")
    @Operation(summary = "Buscar permissões por descrição com paginação")
    public ResponseEntity<Page<Permissao>> findByDescricao(
            @RequestParam String descricao,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"descricao,asc"` ou `"idPermissao,desc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["descricao,desc"]
                        }
                        """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(permissaoService.findByDescricaoContaining(descricao, pageable));
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova permissão", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para criar permissão",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "nomePermissao": "ADMIN",
                              "descricao": "Permissão de administrador do sistema"
                            }
                            """)
            )
    ))
    @CacheEvict(value = "permissoes", allEntries = true)
    public ResponseEntity<Permissao> create(@Valid @RequestBody PermissaoRequestDto permissao) {
        Permissao criada = permissaoService.create(permissao);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar permissão existente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de corpo da requisição para atualizar permissão",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @ExampleObject(value = """
                            {
                              "nomePermissao": "SUPERVISOR",
                              "descricao": "Permissão para supervisores"
                            }
                            """)
            )
    ))
    @CacheEvict(value = "permissoes", allEntries = true)
    public ResponseEntity<Permissao> update(@PathVariable Long id, @Valid @RequestBody PermissaoRequestDto permissao) {
        Permissao atualizada = permissaoService.update(id, permissao);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizada);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir permissão por ID")
    @CacheEvict(value = "permissoes", allEntries = true)
    public void delete(@PathVariable Long id) {
        permissaoService.delete(id);
    }
}
