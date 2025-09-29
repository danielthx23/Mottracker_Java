package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.UsuarioPermissaoRequestDto;
import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.service.UsuarioPermissaoService;
import br.com.fiap.Mottracker.service.UsuarioService;
import br.com.fiap.Mottracker.service.PermissaoService;
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
@RequestMapping("/usuario-permissoes")
@RequiredArgsConstructor
public class UsuarioPermissaoWebController {

    private final UsuarioPermissaoService usuarioPermissaoService;
    private final UsuarioService usuarioService;
    private final PermissaoService permissaoService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasRole('ADMIN')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<UsuarioPermissao> usuarioPermissoes = usuarioPermissaoService.getAll(pageable);
        model.addAttribute("usuarioPermissoes", usuarioPermissoes);
        return "usuario-permissoes/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String novo(Model model) {
        model.addAttribute("usuarioPermissao", new UsuarioPermissaoRequestDto(null, null, ""));
        
        // Carregar usuários e permissões para os dropdowns
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
        model.addAttribute("permissoes", permissaoService.getAll(pageable).getContent());
        
        return "usuario-permissoes/form";
    }

    @GetMapping("/editar/{usuarioId}/{permissaoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long usuarioId, 
                        @PathVariable Long permissaoId, 
                        Model model) {
        UsuarioPermissao usuarioPermissao = usuarioPermissaoService.getById(usuarioId, permissaoId);
        model.addAttribute("usuarioPermissao", new UsuarioPermissaoRequestDto(
            usuarioPermissao.getUsuarioId(),
            usuarioPermissao.getPermissaoId(),
            usuarioPermissao.getPapel()
        ));
        
        // Carregar usuários e permissões para os dropdowns
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
        model.addAttribute("permissoes", permissaoService.getAll(pageable).getContent());
        
        return "usuario-permissoes/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvar(@Valid @ModelAttribute("usuarioPermissao") UsuarioPermissaoRequestDto usuarioPermissaoDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            model.addAttribute("permissoes", permissaoService.getAll(pageable).getContent());
            return "usuario-permissoes/form";
        }

        try {
            usuarioPermissaoService.create(usuarioPermissaoDto);
            redirectAttributes.addFlashAttribute("message", "Permissão de usuário salva com sucesso!");
            return "redirect:/usuario-permissoes/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar permissão de usuário: " + e.getMessage());
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            model.addAttribute("permissoes", permissaoService.getAll(pageable).getContent());
            return "usuario-permissoes/form";
        }
    }

    @PostMapping("/atualizar/{usuarioId}/{permissaoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String atualizar(@PathVariable Long usuarioId,
                          @PathVariable Long permissaoId,
                          @Valid @ModelAttribute("usuarioPermissao") UsuarioPermissaoRequestDto usuarioPermissaoDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            model.addAttribute("permissoes", permissaoService.getAll(pageable).getContent());
            return "usuario-permissoes/form";
        }

        try {
            usuarioPermissaoService.update(usuarioId, permissaoId, usuarioPermissaoDto);
            redirectAttributes.addFlashAttribute("message", "Permissão de usuário atualizada com sucesso!");
            return "redirect:/usuario-permissoes/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar permissão de usuário: " + e.getMessage());
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            model.addAttribute("permissoes", permissaoService.getAll(pageable).getContent());
            return "usuario-permissoes/form";
        }
    }

    @PostMapping("/excluir/{usuarioId}/{permissaoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long usuarioId, 
                        @PathVariable Long permissaoId, 
                        RedirectAttributes redirectAttributes) {
        try {
            usuarioPermissaoService.delete(usuarioId, permissaoId);
            redirectAttributes.addFlashAttribute("message", "Permissão de usuário excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir permissão de usuário: " + e.getMessage());
        }
        return "redirect:/usuario-permissoes/gerenciar";
    }
}


