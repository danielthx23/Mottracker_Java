package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.service.ContratoService;
import br.com.fiap.Mottracker.service.MotoService;
import br.com.fiap.Mottracker.service.PatioService;
import br.com.fiap.Mottracker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioWebController {

    private final UsuarioService usuarioService;
    private final MotoService motoService;
    private final ContratoService contratoService;
    private final PatioService patioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String relatorios(Model model) {
        // Estatísticas gerais
        Pageable pageable = PageRequest.of(0, 1);
        
        long totalUsuarios = usuarioService.getAll(pageable).getTotalElements();
        long totalMotos = motoService.getAll(pageable).getTotalElements();
        long totalContratos = contratoService.getAll(pageable).getTotalElements();
        long totalPatios = patioService.getAll(pageable).getTotalElements();
        
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("totalContratos", totalContratos);
        model.addAttribute("totalPatios", totalPatios);
        
        return "relatorios/dashboard";
    }
}


