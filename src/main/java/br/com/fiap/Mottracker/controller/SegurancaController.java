package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.service.SegurancaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/seguranca")
@RequiredArgsConstructor
@Tag(name = "Segurança", description = "Endpoints para validação de senhas usando funções Oracle")
public class SegurancaController {

    private final SegurancaService segurancaService;

    @PostMapping("/validar-senha")
    @Operation(summary = "Valida senha usando função Oracle fnc_valsenha", 
               description = "Valida se a senha atende aos critérios: mínimo 6 caracteres, ao menos um dígito e uma letra maiúscula")
    public ResponseEntity<Map<String, String>> validarSenha(@RequestBody Map<String, String> request) {
        String senha = request.get("senha");
        if (senha == null) {
            Map<String, String> response = new HashMap<>();
            response.put("erro", "Senha não fornecida");
            return ResponseEntity.badRequest().body(response);
        }

        String resultado = segurancaService.validarSenha(senha);
        Map<String, String> response = new HashMap<>();
        
        if ("OK".equals(resultado)) {
            response.put("status", "OK");
            response.put("mensagem", "Senha válida");
        } else {
            response.put("status", "ERRO");
            response.put("mensagem", resultado);
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validar-senha/package")
    @Operation(summary = "Valida senha usando função do package pkg_seguranca", 
               description = "Valida senha usando pkg_seguranca.fnc_valsenha")
    public ResponseEntity<Map<String, String>> validarSenhaFromPackage(@RequestBody Map<String, String> request) {
        String senha = request.get("senha");
        if (senha == null) {
            Map<String, String> response = new HashMap<>();
            response.put("erro", "Senha não fornecida");
            return ResponseEntity.badRequest().body(response);
        }

        String resultado = segurancaService.validarSenhaFromPackage(senha);
        Map<String, String> response = new HashMap<>();
        
        if ("SENHA OK".equals(resultado)) {
            response.put("status", "OK");
            response.put("mensagem", "Senha válida");
        } else {
            response.put("status", "ERRO");
            response.put("mensagem", resultado);
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validar-senha/java")
    @Operation(summary = "Valida senha usando validação Java", 
               description = "Valida senha usando lógica Java equivalente à função Oracle")
    public ResponseEntity<Map<String, String>> validarSenhaJava(@RequestBody Map<String, String> request) {
        String senha = request.get("senha");
        if (senha == null) {
            Map<String, String> response = new HashMap<>();
            response.put("erro", "Senha não fornecida");
            return ResponseEntity.badRequest().body(response);
        }

        String resultado = segurancaService.validarSenhaJava(senha);
        Map<String, String> response = new HashMap<>();
        
        if ("OK".equals(resultado)) {
            response.put("status", "OK");
            response.put("mensagem", "Senha válida");
        } else {
            response.put("status", "ERRO");
            response.put("mensagem", resultado);
        }
        
        return ResponseEntity.ok(response);
    }
}

