package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.ContratoRequestDto;
import br.com.fiap.Mottracker.model.Contrato;
import br.com.fiap.Mottracker.service.ContratoService;
import br.com.fiap.Mottracker.service.MotoService;
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
@RequestMapping("/contratos")
@RequiredArgsConstructor
public class ContratoWebController {

    private final ContratoService contratoService;
    private final MotoService motoService;
    private final UsuarioService usuarioService;

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String novo(Model model) {
        model.addAttribute("isEdit", false);
        model.addAttribute("contrato", new ContratoRequestDto(
            "", // clausulasContrato
            null, // dataDeEntradaContrato
            null, // horarioDeDevolucaoContrato
            null, // dataDeExpiracaoContrato
            null, // renovacaoAutomatica
            null, // valorToralContrato
            null, // quantidadeParcelas
            null, // usuarioId
            null  // motoId
        ));
        
        // Carregar dados para os dropdowns
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("motos", motoService.getAll(pageable).getContent());
        model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
        
        return "contratos/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("contratoId", id);
        
        Contrato contrato = contratoService.getById(id);
        model.addAttribute("contrato", new ContratoRequestDto(
            contrato.getClausulasContrato(),
            contrato.getDataDeEntradaContrato(),
            contrato.getHorarioDeDevolucaoContrato(),
            contrato.getDataDeExpiracaoContrato(),
            contrato.getRenovacaoAutomaticaContrato() != null && contrato.getRenovacaoAutomaticaContrato() == 1 ? true : false,
            contrato.getValorToralContrato(),
            contrato.getQuantidadeParcelas(),
            contrato.getUsuarioContrato() != null ? contrato.getUsuarioContrato().getIdUsuario() : null,
            contrato.getMotoContrato() != null ? contrato.getMotoContrato().getIdMoto() : null
        ));
        
        // Carregar dados para os dropdowns
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("motos", motoService.getAll(pageable).getContent());
        model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
        
        return "contratos/form";
    }

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Contrato> contratos = contratoService.getAll(pageable);
        model.addAttribute("contratos", contratos);
        return "contratos/list";
    }

    @GetMapping("/meus")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GERENTE', 'OPERADOR')")
    public String meus(@PageableDefault(size = 10) Pageable pageable, Model model) {
        // Aqui você implementaria a lógica para buscar contratos do usuário logado
        Page<Contrato> contratos = contratoService.getAll(pageable);
        model.addAttribute("contratos", contratos);
        return "contratos/my-contracts";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String salvar(@Valid @ModelAttribute("contrato") ContratoRequestDto contratoDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        System.out.println("=== DEBUG CONTRATO SALVAR ===");
        System.out.println("ContratoDto: " + contratoDto);
        System.out.println("Has errors: " + result.hasErrors());
        if (result.hasErrors()) {
            System.out.println("Errors: " + result.getAllErrors());
        }

        if (result.hasErrors()) {
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("motos", motoService.getAll(pageable).getContent());
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "contratos/form";
        }

        try {
            System.out.println("Tentando criar contrato...");
            contratoService.create(contratoDto);
            System.out.println("Contrato criado com sucesso!");
            redirectAttributes.addFlashAttribute("message", "Contrato salvo com sucesso!");
            return "redirect:/contratos/gerenciar";
        } catch (Exception e) {
            System.out.println("Erro ao criar contrato: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Erro ao salvar contrato: " + e.getMessage());
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("motos", motoService.getAll(pageable).getContent());
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "contratos/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("contrato") ContratoRequestDto contratoDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("motos", motoService.getAll(pageable).getContent());
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "contratos/form";
        }

        try {
            contratoService.update(id, contratoDto);
            redirectAttributes.addFlashAttribute("message", "Contrato atualizado com sucesso!");
            return "redirect:/contratos/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar contrato: " + e.getMessage());
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("motos", motoService.getAll(pageable).getContent());
            model.addAttribute("usuarios", usuarioService.getAll(pageable).getContent());
            return "contratos/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            contratoService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Contrato excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir contrato: " + e.getMessage());
        }
        return "redirect:/contratos/gerenciar";
    }
}