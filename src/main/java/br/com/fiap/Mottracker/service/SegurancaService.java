package br.com.fiap.Mottracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Service
@RequiredArgsConstructor
public class SegurancaService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Valida senha usando a função Oracle fnc_valsenha
     */
    public String validarSenha(String senha) {
        try {
            return jdbcTemplate.execute((Connection connection) -> {
                String sql = "{ ? = call fnc_valsenha(?) }";
                try (CallableStatement cs = connection.prepareCall(sql)) {
                    cs.registerOutParameter(1, Types.VARCHAR);
                    cs.setString(2, senha);
                    cs.execute();
                    return cs.getString(1);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar senha: " + e.getMessage(), e);
        }
    }

    /**
     * Valida senha usando a função do package pkg_seguranca
     */
    public String validarSenhaFromPackage(String senha) {
        try {
            return jdbcTemplate.execute((Connection connection) -> {
                String sql = "{ ? = call pkg_seguranca.fnc_valsenha(?) }";
                try (CallableStatement cs = connection.prepareCall(sql)) {
                    cs.registerOutParameter(1, Types.VARCHAR);
                    cs.setString(2, senha);
                    cs.execute();
                    return cs.getString(1);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar senha: " + e.getMessage(), e);
        }
    }

    /**
     * Validação de senha em Java (equivalente à função Oracle)
     */
    public String validarSenhaJava(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            return "ERRO: Senha nula";
        }

        int len = senha.length();
        if (len < 6) {
            return "ERRO: Senha muito curta (min 6)";
        }

        boolean hasDigit = false;
        boolean hasUpper = false;

        for (int i = 0; i < len; i++) {
            char ch = senha.charAt(i);
            if (ch >= '0' && ch <= '9') {
                hasDigit = true;
            }
            if (ch >= 'A' && ch <= 'Z') {
                hasUpper = true;
            }
        }

        if (!hasDigit) {
            return "ERRO: Deve conter ao menos um dígito";
        }

        if (!hasUpper) {
            return "ERRO: Deve conter ao menos uma letra maiúscula";
        }

        return "OK";
    }
}

