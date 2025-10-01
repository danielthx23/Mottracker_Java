package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.PermissaoRequestDto;
import br.com.fiap.Mottracker.model.Permissao;
import br.com.fiap.Mottracker.service.PermissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/permissoes")
@RequiredArgsConstructor
public class PermissaoWebController {

    private final PermissaoService permissaoService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasRole('ADMIN')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Permissao> permissoes = permissaoService.getAll(pageable);
        model.addAttribute("permissoes", permissoes);
        return "permissoes/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String novo(Model model) {
        model.addAttribute("permissao", new PermissaoRequestDto("", ""));
        return "permissoes/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model) {
        Permissao permissao = permissaoService.getById(id);
        model.addAttribute("permissao", new PermissaoRequestDto(
            permissao.getNomePermissao(),
            permissao.getDescricao()
        ));
        
        // Adicionar atributos para modo de edição
        model.addAttribute("isEdit", true);
        model.addAttribute("permissaoId", id);
        
        return "permissoes/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvar(@Valid @ModelAttribute("permissao") PermissaoRequestDto permissaoDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "permissoes/form";
        }

        try {
            permissaoService.create(permissaoDto);
            redirectAttributes.addFlashAttribute("message", "Permissão salva com sucesso!");
            return "redirect:/permissoes/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar permissão: " + e.getMessage());
            return "permissoes/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("permissao") PermissaoRequestDto permissaoDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "permissoes/form";
        }

        try {
            permissaoService.update(id, permissaoDto);
            redirectAttributes.addFlashAttribute("message", "Permissão atualizada com sucesso!");
            return "redirect:/permissoes/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar permissão: " + e.getMessage());
            return "permissoes/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            permissaoService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Permissão excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir permissão: " + e.getMessage());
        }
        return "redirect:/permissoes/gerenciar";
    }
}


