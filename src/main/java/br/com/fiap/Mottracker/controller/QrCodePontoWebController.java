package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.QrCodePontoRequestDto;
import br.com.fiap.Mottracker.model.QrCodePonto;
import br.com.fiap.Mottracker.service.QrCodePontoService;
import br.com.fiap.Mottracker.service.LayoutPatioService;
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
@RequestMapping("/qrcodes")
@RequiredArgsConstructor
public class QrCodePontoWebController {

    private final QrCodePontoService qrCodePontoService;
    private final LayoutPatioService layoutPatioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<QrCodePonto> qrCodes = qrCodePontoService.getAll(pageable);
        model.addAttribute("qrCodes", qrCodes);
        return "qrcodes/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String novo(Model model) {
        model.addAttribute("isEdit", false);
        model.addAttribute("qrCode", new QrCodePontoRequestDto("", 0.0f, 0.0f, null));
        
        // Carregar layouts de pátio para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("layouts", layoutPatioService.getAll(pageable).getContent());
        
        return "qrcodes/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("isEdit", true);
        model.addAttribute("qrCodeId", id);
        
        QrCodePonto qrCode = qrCodePontoService.getById(id);
        model.addAttribute("qrCode", new QrCodePontoRequestDto(
            qrCode.getIdentificadorQrCode(),
            qrCode.getPosX(),
            qrCode.getPosY(),
            qrCode.getLayoutPatio() != null ? qrCode.getLayoutPatio().getIdLayoutPatio() : null
        ));
        
        // Carregar layouts de pátio para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("layouts", layoutPatioService.getAll(pageable).getContent());
        
        return "qrcodes/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String salvar(@Valid @ModelAttribute("qrCode") QrCodePontoRequestDto qrCodeDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar layouts para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("layouts", layoutPatioService.getAll(pageable).getContent());
            return "qrcodes/form";
        }

        try {
            qrCodePontoService.create(qrCodeDto);
            redirectAttributes.addFlashAttribute("message", "QR Code salvo com sucesso!");
            return "redirect:/qrcodes/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar QR Code: " + e.getMessage());
            // Recarregar layouts para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("layouts", layoutPatioService.getAll(pageable).getContent());
            return "qrcodes/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("qrCode") QrCodePontoRequestDto qrCodeDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar layouts para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("layouts", layoutPatioService.getAll(pageable).getContent());
            return "qrcodes/form";
        }

        try {
            qrCodePontoService.update(id, qrCodeDto);
            redirectAttributes.addFlashAttribute("message", "QR Code atualizado com sucesso!");
            return "redirect:/qrcodes/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar QR Code: " + e.getMessage());
            // Recarregar layouts para o dropdown
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("layouts", layoutPatioService.getAll(pageable).getContent());
            return "qrcodes/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            qrCodePontoService.delete(id);
            redirectAttributes.addFlashAttribute("message", "QR Code excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir QR Code: " + e.getMessage());
        }
        return "redirect:/qrcodes/gerenciar";
    }
}
