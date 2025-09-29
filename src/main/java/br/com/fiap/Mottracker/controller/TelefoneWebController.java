package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.TelefoneRequestDto;
import br.com.fiap.Mottracker.model.Telefone;
import br.com.fiap.Mottracker.service.TelefoneService;
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
@RequestMapping("/telefones")
@RequiredArgsConstructor
public class TelefoneWebController {

    private final TelefoneService telefoneService;
    private final UsuarioService usuarioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Telefone> telefones = telefoneService.getAll(pageable);
        model.addAttribute("telefones", telefones);
        return "telefones/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String novo(Model model) {
        model.addAttribute("telefone", new TelefoneRequestDto("", "", null));
        
        // Carregar usuários para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
        
        return "telefones/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String editar(@PathVariable Long id, Model model) {
        Telefone telefone = telefoneService.getById(id);
        model.addAttribute("telefone", new TelefoneRequestDto(
            telefone.getNumero(),
            telefone.getTipo(),
            telefone.getUsuario() != null ? telefone.getUsuario().getIdUsuario() : null
        ));
        
        // Carregar usuários para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
        
        return "telefones/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String salvar(@Valid @ModelAttribute("telefone") TelefoneRequestDto telefoneDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar usuários para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "telefones/form";
        }

        try {
            telefoneService.create(telefoneDto);
            redirectAttributes.addFlashAttribute("message", "Telefone salvo com sucesso!");
            return "redirect:/telefones/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar telefone: " + e.getMessage());
            // Recarregar usuários para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "telefones/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("telefone") TelefoneRequestDto telefoneDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar usuários para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "telefones/form";
        }

        try {
            telefoneService.update(id, telefoneDto);
            redirectAttributes.addFlashAttribute("message", "Telefone atualizado com sucesso!");
            return "redirect:/telefones/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar telefone: " + e.getMessage());
            // Recarregar usuários para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "telefones/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            telefoneService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Telefone excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir telefone: " + e.getMessage());
        }
        return "redirect:/telefones/gerenciar";
    }
}


