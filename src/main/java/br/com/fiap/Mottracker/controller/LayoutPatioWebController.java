package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.LayoutPatioRequestDto;
import br.com.fiap.Mottracker.model.LayoutPatio;
import br.com.fiap.Mottracker.service.LayoutPatioService;
import br.com.fiap.Mottracker.service.PatioService;
import java.math.BigDecimal;
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
@RequestMapping("/layouts")
@RequiredArgsConstructor
public class LayoutPatioWebController {

    private final LayoutPatioService layoutPatioService;
    private final PatioService patioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<LayoutPatio> layouts = layoutPatioService.getAll(pageable);
        model.addAttribute("layouts", layouts);
        return "layouts/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String novo(Model model) {
        model.addAttribute("layout", new LayoutPatioRequestDto("", BigDecimal.ZERO, BigDecimal.ZERO, null, null, null));
        
        // Carregar pátios para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        
        return "layouts/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String editar(@PathVariable Long id, Model model) {
        LayoutPatio layout = layoutPatioService.getById(id);
        model.addAttribute("layout", new LayoutPatioRequestDto(
            layout.getDescricao(),
            layout.getLargura(),
            layout.getComprimento(),
            layout.getAltura(),
            layout.getPatioLayoutPatio() != null ? layout.getPatioLayoutPatio().getIdPatio() : null,
            null // qrCodesLayoutPatio - não carregamos os QR codes no formulário por simplicidade
        ));
        
        // Carregar pátios para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        
        return "layouts/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String salvar(@Valid @ModelAttribute("layout") LayoutPatioRequestDto layoutDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "layouts/form";
        }

        try {
            layoutPatioService.create(layoutDto);
            redirectAttributes.addFlashAttribute("message", "Layout salvo com sucesso!");
            return "redirect:/layouts/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar layout: " + e.getMessage());
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "layouts/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("layout") LayoutPatioRequestDto layoutDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "layouts/form";
        }

        try {
            layoutPatioService.update(id, layoutDto);
            redirectAttributes.addFlashAttribute("message", "Layout atualizado com sucesso!");
            return "redirect:/layouts/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar layout: " + e.getMessage());
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "layouts/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            layoutPatioService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Layout excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir layout: " + e.getMessage());
        }
        return "redirect:/layouts/gerenciar";
    }
}
