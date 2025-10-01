package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.MotoRequestDto;
import br.com.fiap.Mottracker.model.Moto;
import br.com.fiap.Mottracker.service.MotoService;
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
@RequestMapping("/motos")
@RequiredArgsConstructor
public class MotoWebController {

    private final MotoService motoService;
    private final PatioService patioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN', 'GERENTE')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Moto> motos = motoService.getAll(pageable);
        model.addAttribute("motos", motos);
        return "motos/list";
    }

    @GetMapping("/disponiveis")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GERENTE', 'OPERADOR')")
    public String disponiveis(Model model) {
        model.addAttribute("motos", motoService.getDisponiveis());
        return "motos/available";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN', 'GERENTE')")
    public String novo(Model model) {
        model.addAttribute("isEdit", false);
        model.addAttribute("moto", new MotoRequestDto("", "", 0, "", 0, null, "", null, null, null));
        // Use a Pageable to get all patios for the dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        return "motos/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN', 'GERENTE')")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("motoId", id);
        
        Moto moto = motoService.getById(id);
        model.addAttribute("moto", new MotoRequestDto(
            moto.getPlacaMoto(),
            moto.getModeloMoto(),
            moto.getAnoMoto(),
            moto.getIdentificadorMoto(),
            moto.getQuilometragemMoto(),
            moto.getEstadoMoto(),
            moto.getCondicoesMoto(),
            moto.getContratoMoto() != null ? moto.getContratoMoto().getIdContrato() : null,
            moto.getMotoPatioAtual() != null ? moto.getMotoPatioAtual().getIdPatio() : null,
            moto.getMotoPatioOrigemId()
        ));
        // Use a Pageable to get all patios for the dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        return "motos/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN', 'GERENTE')")
    public String salvar(@Valid @ModelAttribute("moto") MotoRequestDto motoDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Use a Pageable to get all patios for the dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "motos/form";
        }

        try {
            motoService.create(motoDto);
            redirectAttributes.addFlashAttribute("message", "Moto salva com sucesso!");
            return "redirect:/motos/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar moto: " + e.getMessage());
            // Use a Pageable to get all patios for the dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "motos/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN', 'GERENTE')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("moto") MotoRequestDto motoDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Use a Pageable to get all patios for the dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            model.addAttribute("isEdit", true);
            model.addAttribute("motoId", id);
            return "motos/form";
        }

        try {
            motoService.update(id, motoDto);
            redirectAttributes.addFlashAttribute("message", "Moto atualizada com sucesso!");
            return "redirect:/motos/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar moto: " + e.getMessage());
            // Use a Pageable to get all patios for the dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            model.addAttribute("isEdit", true);
            model.addAttribute("motoId", id);
            return "motos/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN', 'GERENTE')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            motoService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Moto excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir moto: " + e.getMessage());
        }
        return "redirect:/motos/gerenciar";
    }
}

