package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.PatioRequestDto;
import br.com.fiap.Mottracker.model.Patio;
import br.com.fiap.Mottracker.service.PatioService;
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
@RequestMapping("/patios")
@RequiredArgsConstructor
public class PatioWebController {

    private final PatioService patioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Patio> patios = patioService.getAll(pageable);
        model.addAttribute("patios", patios);
        return "patios/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String novo(Model model) {
        model.addAttribute("patio", new PatioRequestDto("", 0, 0, null, null, null, null, null));
        return "patios/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String editar(@PathVariable Long id, Model model) {
        Patio patio = patioService.getById(id);
        model.addAttribute("patio", new PatioRequestDto(
            patio.getNomePatio(),
            patio.getMotosTotaisPatio(),
            patio.getMotosDisponiveisPatio(),
            patio.getDataPatio(),
            null, // layoutPatio
            null, // enderecoPatio
            null, // camerasPatio
            null  // motosPatioAtual
        ));
        return "patios/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String salvar(@Valid @ModelAttribute("patio") PatioRequestDto patioDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "patios/form";
        }

        try {
            patioService.create(patioDto);
            redirectAttributes.addFlashAttribute("message", "Pátio salvo com sucesso!");
            return "redirect:/patios/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar pátio: " + e.getMessage());
            return "patios/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("patio") PatioRequestDto patioDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "patios/form";
        }

        try {
            patioService.update(id, patioDto);
            redirectAttributes.addFlashAttribute("message", "Pátio atualizado com sucesso!");
            return "redirect:/patios/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar pátio: " + e.getMessage());
            return "patios/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            patioService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Pátio excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir pátio: " + e.getMessage());
        }
        return "redirect:/patios/gerenciar";
    }
}
