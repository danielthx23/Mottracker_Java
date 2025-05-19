package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.CameraRequestDto;
import br.com.fiap.Mottracker.enums.CameraStatus;
import br.com.fiap.Mottracker.model.Camera;
import br.com.fiap.Mottracker.service.CameraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cameras")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Câmeras", description = "Endpoints para gerenciamento de câmeras")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @GetMapping
    @Cacheable(value = "cameras")
    @Operation(summary = "Listar todas as câmeras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Page<Camera>> getAll(
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
                  "sort": ["nomeCamera,asc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "nomeCamera") Pageable pageable) {
        return ResponseEntity.ok(cameraService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar câmera por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Câmera encontrada"),
            @ApiResponse(responseCode = "404", description = "Câmera não encontrada")
    })
    public ResponseEntity<Camera> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cameraService.getById(id));
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar câmeras pelo nome (parcial ou completo)")
    public ResponseEntity<Page<Camera>> getByNome(
            @Parameter(description = "Nome (ou parte dele) da câmera") @PathVariable String nome,
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
                  "sort": ["nomeCamera,asc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "nomeCamera") Pageable pageable) {
        return ResponseEntity.ok(cameraService.getByNome(nome, pageable));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar câmeras pelo status (ATIVA, INATIVA, MANUTENCAO)")
    public ResponseEntity<Page<Camera>> getByStatus(
            @Parameter(description = "Status da câmera (ATIVA, INATIVA, MANUTENCAO)") @PathVariable CameraStatus status,
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
                  "sort": ["nomeCamera,asc"]
                }
                """)
            )
            @PageableDefault(size = 10, sort = "nomeCamera") Pageable pageable) {
        return ResponseEntity.ok(cameraService.getByStatus(status, pageable));
    }

    @PostMapping
    @CacheEvict(value = "cameras", allEntries = true)
    @Operation(summary = "Criar uma nova câmera")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Câmera criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<Camera> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da nova câmera",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "nomeCamera": "Câmera 1",
                                              "ipCamera": "192.168.0.101",
                                              "status": "ATIVA",
                                              "posX": 120.5,
                                              "posY": 310.75,
                                              "patioId": 1
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody CameraRequestDto dto) {
        Camera created = cameraService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "cameras", allEntries = true)
    @Operation(summary = "Atualizar uma câmera existente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados da câmera",
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "nomeCamera": "Câmera Atualizada",
                                      "ipCamera": "192.168.0.150",
                                      "status": "MANUTENCAO",
                                      "posX": 85.2,
                                      "posY": 240.3,
                                      "patioId": 2
                                    }
                                    """
                    )
            )
    )
    public ResponseEntity<Camera> update(
            @PathVariable Long id,
            @Valid @RequestBody CameraRequestDto dto) {
        Camera updated = cameraService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @CacheEvict(value = "cameras", allEntries = true)
    @Operation(summary = "Deletar uma câmera pelo ID")
    public void delete(@PathVariable Long id) {
        cameraService.delete(id);
    }
}
