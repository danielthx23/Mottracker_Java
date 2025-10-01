package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.UsuarioRequestDto;
import br.com.fiap.Mottracker.dto.UsuarioEditDto;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioWebController {

    private final UsuarioService usuarioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasRole('ADMIN')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Usuario> usuarios = usuarioService.getAll(pageable);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String novo(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDto("", "", "", "", "", null, null));
        model.addAttribute("isEdit", false);
        return "usuarios/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.getById(id);
        model.addAttribute("usuario", new UsuarioEditDto(
            usuario.getNomeUsuario(),
            usuario.getCpfUsuario(),
            "", // Senha vazia para edição
            usuario.getCnhUsuario(),
            usuario.getEmailUsuario(),
            usuario.getDataNascimentoUsuario(),
            null // Telefones serão carregados separadamente
        ));
        model.addAttribute("isEdit", true);
        model.addAttribute("usuarioId", id);
        return "usuarios/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvar(@Valid @ModelAttribute("usuario") UsuarioRequestDto usuarioDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "usuarios/form";
        }

        try {
            usuarioService.create(usuarioDto);
            redirectAttributes.addFlashAttribute("message", "Usuário salvo com sucesso!");
            return "redirect:/usuarios/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar usuário: " + e.getMessage());
            return "usuarios/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("usuario") UsuarioEditDto usuarioDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        System.out.println("DEBUG: Atualizando usuário ID: " + id);
        System.out.println("DEBUG: Dados recebidos: " + usuarioDto);
        System.out.println("DEBUG: BindingResult errors: " + result.hasErrors());
        
        if (result.hasErrors()) {
            System.out.println("DEBUG: Erros de validação: " + result.getAllErrors());
            model.addAttribute("isEdit", true);
            model.addAttribute("usuarioId", id);
            return "usuarios/form";
        }

        try {
            // Converter UsuarioEditDto para UsuarioRequestDto
            UsuarioRequestDto requestDto = new UsuarioRequestDto(
                usuarioDto.nomeUsuario(),
                usuarioDto.cpfUsuario(),
                usuarioDto.senhaUsuario(), // Pode estar vazia
                usuarioDto.cnhUsuario(),
                usuarioDto.emailUsuario(),
                usuarioDto.dataNascimentoUsuario(),
                usuarioDto.telefones()
            );
            
            usuarioService.update(id, requestDto);
            System.out.println("DEBUG: Usuário atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("message", "Usuário atualizado com sucesso!");
            return "redirect:/usuarios/gerenciar";
        } catch (Exception e) {
            System.out.println("DEBUG: Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Erro ao atualizar usuário: " + e.getMessage());
            model.addAttribute("isEdit", true);
            model.addAttribute("usuarioId", id);
            return "usuarios/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Usuário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir usuário: " + e.getMessage());
        }
        return "redirect:/usuarios/gerenciar";
    }
}
