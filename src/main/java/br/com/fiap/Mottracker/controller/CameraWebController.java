package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.dto.CameraRequestDto;
import br.com.fiap.Mottracker.enums.CameraStatus;
import br.com.fiap.Mottracker.model.Camera;
import br.com.fiap.Mottracker.service.CameraService;
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
@RequestMapping("/cameras")
@RequiredArgsConstructor
public class CameraWebController {

    private final CameraService cameraService;
    private final PatioService patioService;

    @GetMapping("/gerenciar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String gerenciar(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Camera> cameras = cameraService.getAll(pageable);
        model.addAttribute("cameras", cameras);
        return "cameras/list";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String novo(Model model) {
        model.addAttribute("camera", new CameraRequestDto("", "", CameraStatus.ATIVA, null, null, null));
        
        // Carregar pátios para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        model.addAttribute("statusOptions", CameraStatus.values());
        
        return "cameras/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String editar(@PathVariable Long id, Model model) {
        Camera camera = cameraService.getById(id);
        model.addAttribute("camera", new CameraRequestDto(
            camera.getNomeCamera(),
            camera.getIpCamera(),
            camera.getStatus(),
            camera.getPosX(),
            camera.getPosY(),
            camera.getPatio() != null ? camera.getPatio().getIdPatio() : null
        ));
        
        // Carregar pátios para o dropdown
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        model.addAttribute("patios", patioService.getAll(pageable).getContent());
        model.addAttribute("statusOptions", CameraStatus.values());
        
        return "cameras/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String salvar(@Valid @ModelAttribute("camera") CameraRequestDto cameraDto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            model.addAttribute("statusOptions", CameraStatus.values());
            return "cameras/form";
        }

        try {
            cameraService.create(cameraDto);
            redirectAttributes.addFlashAttribute("message", "Câmera salva com sucesso!");
            return "redirect:/cameras/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar câmera: " + e.getMessage());
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            model.addAttribute("statusOptions", CameraStatus.values());
            return "cameras/form";
        }
    }

    @PostMapping("/atualizar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String atualizar(@PathVariable Long id,
                          @Valid @ModelAttribute("camera") CameraRequestDto cameraDto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            model.addAttribute("statusOptions", CameraStatus.values());
            return "cameras/form";
        }

        try {
            cameraService.update(id, cameraDto);
            redirectAttributes.addFlashAttribute("message", "Câmera atualizada com sucesso!");
            return "redirect:/cameras/gerenciar";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar câmera: " + e.getMessage());
            // Recarregar dados para os dropdowns
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            model.addAttribute("patios", patioService.getAll(pageable).getContent());
            model.addAttribute("statusOptions", CameraStatus.values());
            return "cameras/form";
        }
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'OPERADOR')")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cameraService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Câmera excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir câmera: " + e.getMessage());
        }
        return "redirect:/cameras/gerenciar";
    }
}


