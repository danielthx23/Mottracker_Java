package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.UsuarioPermissaoRequestDto;
import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.service.UsuarioPermissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("/usuario-permissoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Usuário-Permissões", description = "Gerenciamento das permissões atribuídas aos usuários")
public class UsuarioPermissaoController {

    private final UsuarioPermissaoService usuarioPermissaoService;

    @GetMapping
    @Operation(summary = "Listar todas as permissões de usuários com paginação")
    @Cacheable(value = "usuariospermissoes")
    public ResponseEntity<Page<UsuarioPermissao>> getAll(
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"` (ex: `"usuarioId,asc"`)
                        """,
                    examples = @ExampleObject(value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["usuarioId,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "usuarioId") Pageable pageable) {
        return ResponseEntity.ok(usuarioPermissaoService.getAll(pageable));
    }

    @GetMapping("/{usuarioId}/{permissaoId}")
    @Operation(summary = "Buscar permissão por ID de usuário e permissão")
    public ResponseEntity<UsuarioPermissao> getById(
            @PathVariable Long usuarioId,
            @PathVariable Long permissaoId) {
        return ResponseEntity.ok(usuarioPermissaoService.getById(usuarioId, permissaoId));
    }

    @GetMapping("/usuario")
    @Operation(summary = "Listar permissões por ID de usuário")
    public ResponseEntity<Page<UsuarioPermissao>> getByUsuarioId(
            @RequestParam Long usuarioId,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"`
                        """,
                    examples = @ExampleObject(value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["usuarioId,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "usuarioId") Pageable pageable) {
        return ResponseEntity.ok(usuarioPermissaoService.findByUsuarioId(usuarioId, pageable));
    }

    @GetMapping("/permissao")
    @Operation(summary = "Listar permissões por ID da permissão")
    public ResponseEntity<Page<UsuarioPermissao>> getByPermissaoId(
            @RequestParam Long permissaoId,
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"`
                        """,
                    examples = @ExampleObject(value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["usuarioId,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "usuarioId") Pageable pageable) {
        return ResponseEntity.ok(usuarioPermissaoService.findByPermissaoId(permissaoId, pageable));
    }

    @GetMapping("/usuario/{usuarioId}/permissao/{permissaoId}")
    @Operation(summary = "Buscar permissão específica por ID de usuário e de permissão")
    public ResponseEntity<UsuarioPermissao> getByUsuarioIdAndPermissaoId(
            @PathVariable Long usuarioId,
            @PathVariable Long permissaoId) {
        UsuarioPermissao usuarioPermissao = usuarioPermissaoService.getByUsuarioIdAndPermissaoId(usuarioId, permissaoId);
        return ResponseEntity.ok(usuarioPermissao);
    }

    @PostMapping
    @Operation(summary = "Criar nova permissão para um usuário",
            requestBody = @RequestBody(
                    description = "Exemplo de requisição para criar uma permissão",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                        {
                          "usuarioId": 1,
                          "permissaoId": 2,
                          "papel": "ADMIN"
                        }
                        """)
                    )
            )
    )
    @CacheEvict(value = "usuariospermissoes", allEntries = true)
    public ResponseEntity<UsuarioPermissao> create(@Valid @RequestBody UsuarioPermissaoRequestDto dto) {
        UsuarioPermissao created = usuarioPermissaoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{usuarioId}/{permissaoId}")
    @Operation(summary = "Atualizar permissão de usuário",
            requestBody = @RequestBody(
                    description = "Exemplo de requisição para atualizar uma permissão",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                        {
                          "usuarioId": 1,
                          "permissaoId": 2,
                          "papel": "GERENTE"
                        }
                        """)
                    )
            )
    )
    @CacheEvict(value = "usuariospermissoes", allEntries = true)
    public ResponseEntity<UsuarioPermissao> update(
            @PathVariable Long usuarioId,
            @PathVariable Long permissaoId,
            @Valid @RequestBody UsuarioPermissaoRequestDto dto) {
        UsuarioPermissao updated = usuarioPermissaoService.update(usuarioId, permissaoId, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{usuarioId}/{permissaoId}")
    @Operation(summary = "Excluir permissão de um usuário")
    @CacheEvict(value = "usuariospermissoes", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long usuarioId, @PathVariable Long permissaoId) {
        usuarioPermissaoService.delete(usuarioId, permissaoId);
    }
}
