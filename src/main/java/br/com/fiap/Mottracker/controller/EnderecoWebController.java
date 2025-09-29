package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.EnderecoRequestDto;
import br.com.fiap.Mottracker.model.Endereco;
import br.com.fiap.Mottracker.service.EnderecoService;
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
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoWebController {

    private final EnderecoService enderecoService;
    private final PatioService patioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Endereco> enderecos = enderecoService.getAll(pageable);
        model.addAttribute("enderecos", enderecos);
        return "enderecos/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String novo(Model model) {
        model.addAttribute("endereco", new EnderecoRequestDto("", "", "", "", "", "", "", "", null));
        
        // Carregar pátios para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        
        return "enderecos/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String editar(@PathVariable Long id, Model model) {
        Endereco endereco = enderecoService.getById(id);
        model.addAttribute("endereco", new EnderecoRequestDto(
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep(),
            endereco.getReferencia(),
            endereco.getPatioEndereco() != null ? endereco.getPatioEndereco().getIdPatio() : null
        ));
        
        // Carregar pátios para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        
        return "enderecos/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String salvar(@Valid @ModelAttribute("endereco") EnderecoRequestDto enderecoDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "enderecos/form";
        }

        try {
            enderecoService.create(enderecoDto);
            redirectAttributes.addFlashAttribute("message", "Endereço salvo com sucesso!");
            return "redirect:/enderecos/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar endereço: " + e.getMessage());
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "enderecos/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("endereco") EnderecoRequestDto enderecoDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "enderecos/form";
        }

        try {
            enderecoService.update(id, enderecoDto);
            redirectAttributes.addFlashAttribute("message", "Endereço atualizado com sucesso!");
            return "redirect:/enderecos/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar endereço: " + e.getMessage());
            // Recarregar pátios para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            return "enderecos/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            enderecoService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Endereço excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir endereço: " + e.getMessage());
        }
        return "redirect:/enderecos/gerenciar";
    }
}

