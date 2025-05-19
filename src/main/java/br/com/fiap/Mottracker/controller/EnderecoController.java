package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.EnderecoRequestDto;
import br.com.fiap.Mottracker.model.Endereco;
import br.com.fiap.Mottracker.service.EnderecoService;
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
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Enderecos", description = "Operações relacionadas ao endereço de patios")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    @Cacheable(value = "enderecos")
    @Operation(summary = "Listar todos os endereços")
    public ResponseEntity<Page<Endereco>> getAll(
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"idEndereco,desc` ou `"idEndereco,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idEndereco,desc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "idEndereco", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(enderecoService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    public ResponseEntity<Endereco> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enderecoService.getById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
    }

    @GetMapping("/cidade")
    @Operation(summary = "Listar endereços por cidade")
    public ResponseEntity<Page<Endereco>> getByCidade(
            @RequestParam String nome,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"idEndereco,desc` ou `"idEndereco,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idEndereco,desc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(enderecoService.findByCidade(nome, pageable));
    }

    @GetMapping("/estado")
    @Operation(summary = "Listar endereços por estado")
    public ResponseEntity<Page<Endereco>> getByEstado(
            @RequestParam String nome,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"idEndereco,desc` ou `"idEndereco,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idEndereco,desc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(enderecoService.findByEstado(nome, pageable));
    }

    @GetMapping("/cep/{cep}")
    @Operation(summary = "Buscar endereço por CEP")
    public ResponseEntity<Endereco> getByCep(@PathVariable String cep) {
        return ResponseEntity.ok(enderecoService.findByCep(cep));
    }

    @GetMapping("/bairro")
    @Operation(summary = "Listar endereços por bairro")
    public ResponseEntity<Page<Endereco>> getByBairro(
            @RequestParam String nome,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"idEndereco,desc` ou `"idEndereco,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idEndereco,desc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(enderecoService.findByBairro(nome, pageable));
    }

    @GetMapping("/logradouro")
    @Operation(summary = "Listar endereços por logradouro")
    public ResponseEntity<Page<Endereco>> getByLogradouro(
            @RequestParam String nome,
            @Parameter(
                    description = """
                Parâmetros de paginação padrão do Spring Data:
                
                - `page`: número da página (inicia em 0)
                - `size`: quantidade de registros por página
                - `sort`: ordenação. Pode ser passada como `"campo,direcao"` (ex: `"idEndereco,desc` ou `"idEndereco,asc"`)
                """,
                    examples = @ExampleObject(name = "Exemplo de paginação", value = """
                {
                  "page": 0,
                  "size": 10,
                  "sort": ["idEndereco,desc"]
                }
                """)
            )
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(enderecoService.findByLogradouroContaining(nome, pageable));
    }

    @GetMapping("/patio/{id}")
    @Operation(summary = "Buscar endereço pelo ID do pátio")
    public ResponseEntity<Endereco> getByPatioId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.findByPatioId(id));
    }

    @PostMapping
    @CacheEvict(value = "enderecos", allEntries = true)
    @Operation(summary = "Criar novo endereço")
    @RequestBody(
            description = "Dados do endereço a ser criado",
            required = true,
            content = @Content(
                    examples = @ExampleObject(value = """
                            {
                              "logradouro": "Avenida Paulista",
                              "numero": "1578",
                              "complemento": "Apartamento 101",
                              "bairro": "Bela Vista",
                              "cidade": "São Paulo",
                              "estado": "SP",
                              "cep": "01310-200",
                              "referencia": "Próximo ao MASP",
                              "patioId": 1
                            }
                            """)
            )
    )
    public ResponseEntity<Endereco> create(@Valid @RequestBody EnderecoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.create(dto));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "enderecos", allEntries = true)
    @Operation(summary = "Atualizar endereço existente")
    @RequestBody(
            description = "Dados atualizados do endereço",
            required = true,
            content = @Content(
                    examples = @ExampleObject(value = """
                             {
                              "logradouro": "Avenida Paulista",
                              "numero": "1578",
                              "complemento": "Apartamento 101",
                              "bairro": "Bela Vista",
                              "cidade": "São Paulo",
                              "estado": "SP",
                              "cep": "01310-200",
                              "referencia": "Próximo ao MASP",
                              "patioId": 1
                            }
                            """)
            )
    )
    public ResponseEntity<Endereco> update(@PathVariable Long id, @Valid @RequestBody EnderecoRequestDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(enderecoService.update(id, dto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @CacheEvict(value = "enderecos", allEntries = true)
    @Operation(summary = "Deletar endereço por ID")
    public void delete(@PathVariable Long id) {
        try {
            enderecoService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
    }
}
