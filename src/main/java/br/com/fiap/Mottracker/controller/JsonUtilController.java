package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.service.JsonUtilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/json-util")
@RequiredArgsConstructor
@Tag(name = "JSON Util", description = "Endpoints para conversão de queries SQL em JSON usando funções Oracle")
public class JsonUtilController {

    private final JsonUtilService jsonUtilService;

    @PostMapping("/criar-json")
    @Operation(summary = "Converte query SQL em JSON usando função criaJson", 
               description = "Executa uma query SQL e retorna o resultado em formato JSON usando a função Oracle criaJson")
    public ResponseEntity<Map<String, Object>> criarJson(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        if (query == null || query.trim().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Query não fornecida");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String json = jsonUtilService.criaJson(query);
            Map<String, Object> response = new HashMap<>();
            response.put("json", json);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/criar-json/package")
    @Operation(summary = "Converte query SQL em JSON usando package pkg_json_util", 
               description = "Executa uma query SQL e retorna o resultado em formato JSON usando pkg_json_util.criaJson")
    public ResponseEntity<Map<String, Object>> criarJsonFromPackage(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        if (query == null || query.trim().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Query não fornecida");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String json = jsonUtilService.criaJsonFromPackage(query);
            Map<String, Object> response = new HashMap<>();
            response.put("json", json);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

