package br.com.fiap.Mottracker.controller;

import br.com.fiap.Mottracker.model.Permissao;
import br.com.fiap.Mottracker.model.Usuario;
import br.com.fiap.Mottracker.model.UsuarioPermissao;
import br.com.fiap.Mottracker.model.UsuarioPermissaoId;
import br.com.fiap.Mottracker.repository.PermissaoRepository;
import br.com.fiap.Mottracker.repository.UsuarioPermissaoRepository;
import br.com.fiap.Mottracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setup")
@RequiredArgsConstructor
public class SetupController {

    private final PermissaoRepository permissaoRepository;
    private final UsuarioPermissaoRepository usuarioPermissaoRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/init")
    public String init(Model model) {
        return "setup/init";
    }

    @PostMapping("/init")
    public String initData(Model model) {
        try {
            // 1. Criar permissões se não existirem
            criarPermissoes();
            
            // 2. Associar permissões a todos os usuários existentes
            associarPermissoesAosUsuarios();
            
            model.addAttribute("success", "Sistema inicializado com sucesso! Todas as permissões foram criadas e associadas aos usuários.");
            return "setup/init";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao inicializar sistema: " + e.getMessage());
            return "setup/init";
        }
    }

    private void criarPermissoes() {
        String[] permissoes = {"ADMIN", "GERENTE", "OPERADOR", "USER"};
        String[] descricoes = {
            "Administrador do sistema",
            "Gerente de operações", 
            "Operador de pátio",
            "Usuário comum"
        };

        for (int i = 0; i < permissoes.length; i++) {
            if (!permissaoRepository.existsByNomePermissao(permissoes[i])) {
                Permissao permissao = new Permissao();
                permissao.setNomePermissao(permissoes[i]);
                permissao.setDescricao(descricoes[i]);
                permissaoRepository.save(permissao);
                System.out.println("Permissão criada: " + permissoes[i]);
            }
        }
    }

    private void associarPermissoesAosUsuarios() {
        // Buscar todas as permissões
        var permissoes = permissaoRepository.findAll();
        
        // Buscar todos os usuários
        var usuarios = usuarioRepository.findAll();
        
        for (Usuario usuario : usuarios) {
            for (Permissao permissao : permissoes) {
                UsuarioPermissaoId id = new UsuarioPermissaoId(usuario.getIdUsuario(), permissao.getIdPermissao());
                
                if (!usuarioPermissaoRepository.existsById(id)) {
                    UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
                    usuarioPermissao.setUsuarioId(usuario.getIdUsuario());
                    usuarioPermissao.setPermissaoId(permissao.getIdPermissao());
                    usuarioPermissao.setPapel(permissao.getNomePermissao());
                    
                    usuarioPermissaoRepository.save(usuarioPermissao);
                    System.out.println("Permissão " + permissao.getNomePermissao() + " associada ao usuário " + usuario.getEmailUsuario());
                }
            }
        }
    }
}


