package br.com.fiap.Mottracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    
    @GetMapping("/test-permissions")
    public String testPermissions() {
        return "test-permissions";
    }
}