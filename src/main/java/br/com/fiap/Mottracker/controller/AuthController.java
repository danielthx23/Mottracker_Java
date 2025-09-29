package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.UsuarioLoginDto;
import br.com.fiap.Mottracker.dto.UsuarioRequestDto;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Email ou senha inválidos");
        }
        if (logout != null) {
            model.addAttribute("message", "Logout realizado com sucesso");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDto("", "", "", "", "", null, null));
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsuarioRequestDto usuarioDto, Model model) {

        try {
            System.out.println("Creating user...");
            Usuario usuario = usuarioService.create(usuarioDto);
            System.out.println("User created successfully: " + usuario.getIdUsuario());
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Erro ao criar usuário: " + e.getMessage());
            model.addAttribute("usuario", usuarioDto);
            return "auth/register";
        }
    }
}
