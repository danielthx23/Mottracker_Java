package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.UsuarioRequestDto;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;

    @GetMapping("/create-admin")
    public String createAdminForm(Model model) {
        return "admin/create-admin";
    }

    @PostMapping("/create-admin")
    public String createAdmin(UsuarioRequestDto dto, Model model) {
        try {
            Usuario usuario = usuarioService.create(dto);
            model.addAttribute("success", "Usuário admin criado com sucesso! Email: " + usuario.getEmailUsuario());
            return "admin/create-admin";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar usuário admin: " + e.getMessage());
            return "admin/create-admin";
        }
    }
}



