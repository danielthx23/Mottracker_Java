package br.com.fiap.Mottracker.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Types;

@Service
@RequiredArgsConstructor
public class JsonUtilService {

    @PersistenceContext
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    /**
     * Chama a função Oracle criaJson para converter uma query em JSON
     */
    public String criaJson(String query) {
        try {
            return jdbcTemplate.execute((Connection connection) -> {
                String sql = "{ ? = call criaJson(?) }";
                try (CallableStatement cs = connection.prepareCall(sql)) {
                    cs.registerOutParameter(1, Types.CLOB);
                    cs.setString(2, query);
                    cs.execute();
                    
                    Clob clob = cs.getClob(1);
                    if (clob != null) {
                        long length = clob.length();
                        return clob.getSubString(1, (int) length);
                    }
                    return "[]";
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar criaJson: " + e.getMessage(), e);
        }
    }

    /**
     * Chama a função criaJson do package pkg_json_util
     */
    public String criaJsonFromPackage(String query) {
        try {
            return jdbcTemplate.execute((Connection connection) -> {
                String sql = "{ ? = call pkg_json_util.criaJson(?) }";
                try (CallableStatement cs = connection.prepareCall(sql)) {
                    cs.registerOutParameter(1, Types.CLOB);
                    cs.setString(2, query);
                    cs.execute();
                    
                    Clob clob = cs.getClob(1);
                    if (clob != null) {
                        long length = clob.length();
                        return clob.getSubString(1, (int) length);
                    }
                    return "[]";
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar pkg_json_util.criaJson: " + e.getMessage(), e);
        }
    }
}

