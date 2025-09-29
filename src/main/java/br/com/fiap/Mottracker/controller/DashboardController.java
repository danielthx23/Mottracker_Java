package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.service.ContratoService;
import br.com.fiap.Mottracker.service.MotoService;
import br.com.fiap.Mottracker.service.PatioService;
import br.com.fiap.Mottracker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UsuarioService usuarioService;
    private final MotoService motoService;
    private final ContratoService contratoService;
    private final PatioService patioService;

    @GetMapping("/home")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String userEmail = authentication.getName();
        var usuario = usuarioService.findByEmail(userEmail);
        
        // Create a Pageable for getting total counts (we only need the first page)
        Pageable pageable = PageRequest.of(0, 1);
        
        // Get dashboard statistics
        long totalUsuarios = usuarioService.getAll(pageable).getTotalElements();
        long totalMotos = motoService.getAll(pageable).getTotalElements();
        long totalContratos = contratoService.getAll(pageable).getTotalElements();
        long totalPatios = patioService.getAll(pageable).getTotalElements();
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("totalContratos", totalContratos);
        model.addAttribute("totalPatios", totalPatios);
        
        return "dashboard";
    }
}
