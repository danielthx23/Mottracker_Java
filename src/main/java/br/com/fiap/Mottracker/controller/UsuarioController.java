package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.UsuarioLoginDto;
import br.com.fiap.Mottracker.dto.UsuarioRequestDto;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.service.UsuarioService;
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
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação")
    @Cacheable(value = "usuarios")
    public ResponseEntity<Page<Usuario>> getAll(
            @Parameter(
                    description = """
                        Parâmetros de paginação padrão do Spring Data:
                        
                        - `page`: número da página (inicia em 0)
                        - `size`: quantidade de registros por página
                        - `sort`: ordenação no formato `"campo,direcao"` (ex: `"idUsuario,asc"`)
                        """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                        {
                          "page": 0,
                          "size": 10,
                          "sort": ["idUsuario,asc"]
                        }
                        """)
            )
            @PageableDefault(size = 10, sort = "idUsuario") Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo usuário",
            requestBody = @RequestBody(
                    description = "Exemplo de requisição para criar usuário",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                        {
                          "nomeUsuario": "João da Silva",
                          "cpfUsuario": "123.456.789-00",
                          "senhaUsuario": "senha123",
                          "cnhUsuario": "12345678900",
                          "emailUsuario": "joao@email.com",
                          "dataNascimentoUsuario": "1990-01-01T00:00:00Z",
                          "telefones": [
                            {
                              "numero": "(11) 91234-5678",
                              "tipo": "celular"
                            }
                          ]
                        }
                        """)
                    )
            )
    )
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioRequestDto usuarioDto) {
        Usuario criado = usuarioService.create(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário",
            requestBody = @RequestBody(
                    description = "Exemplo de requisição para atualizar usuário",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                        {
                          "nomeUsuario": "Maria Souza",
                          "cpfUsuario": "987.654.321-00",
                          "senhaUsuario": "novaSenha456",
                          "cnhUsuario": "98765432100",
                          "emailUsuario": "maria@email.com",
                          "dataNascimentoUsuario": "1985-05-10T00:00:00Z",
                          "telefones": [
                            {
                              "numero": "(21) 99876-5432",
                              "tipo": "residencial"
                            }
                          ]
                        }
                        """)
                    )
            )
    )
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<Usuario> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto usuarioDto) {
        Usuario atualizado = usuarioService.update(id, usuarioDto);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir usuário por ID")
    @CacheEvict(value = "usuarios", allEntries = true)
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário no sistema",
            requestBody = @RequestBody(
                    description = "Exemplo de requisição de login",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                        {
                          "emailUsuario": "joao@email.com",
                          "senhaUsuario": "senha123"
                        }
                        """)
                    )
            )
    )
    public ResponseEntity<Usuario> login(@Valid @RequestBody UsuarioLoginDto loginDto) {
        Usuario usuario = usuarioService.login(loginDto);
        return ResponseEntity.ok(usuario);
    }
}
