package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.model.Permissao;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.service.PermissaoService;
import br.com.fiap.Mottracker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final UsuarioService usuarioService;
    private final PermissaoService permissaoService;

    @GetMapping("/test")
    public String test(Authentication authentication, Model model) {
        try {
            // Get current user
            String userEmail = authentication.getName();
            Usuario usuario = usuarioService.findByEmail(userEmail);
            
            // Get all permissions
            List<Permissao> permissoes = permissaoService.getAll();
            
            // Get user permissions
            List<UsuarioPermissao> usuarioPermissoes = usuario.getUsuarioPermissoes();
            
            model.addAttribute("usuario", usuario);
            model.addAttribute("permissoes", permissoes);
            model.addAttribute("usuarioPermissoes", usuarioPermissoes);
            model.addAttribute("userEmail", userEmail);
            
            return "test";
        } catch (Exception e) {
            model.addAttribute("error", "Erro: " + e.getMessage());
            return "test";
        }
    }
}