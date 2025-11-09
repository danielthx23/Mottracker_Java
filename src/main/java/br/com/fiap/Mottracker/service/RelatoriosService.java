package br.com.fiap.Mottracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatoriosService {

    private final JdbcTemplate jdbcTemplate;
    private final JsonUtilService jsonUtilService;

    /**
     * Executa a procedure proc_json e retorna o JSON
     */
    public String gerarJsonRelatorio() {
        try {
            String query = "SELECT m.id_moto, m.placa_moto, m.modelo_moto, m.quilometragem_moto, " +
                          "c.id_contrato, c.valor_toral_contrato, p.id_patio, p.nome_patio " +
                          "FROM mt_moto_java m " +
                          "JOIN mt_contrato_java c ON m.contrato_moto_id = c.id_contrato " +
                          "JOIN mt_patio_java p ON m.moto_patio_atual_id = p.id_patio " +
                          "ORDER BY p.id_patio, c.id_contrato, m.id_moto";
            
            return jsonUtilService.criaJson(query);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Executa a procedure proc_motos_p_patio e retorna os resultados
     */
    public List<Map<String, Object>> gerarRelatorioMotosPorPatio() {
        try {
            return jdbcTemplate.execute((Connection connection) -> {
                String sql = "{ call proc_motos_p_patio }";
                try (CallableStatement cs = connection.prepareCall(sql)) {
                    cs.execute();
                    
                    // Como a procedure usa DBMS_OUTPUT, precisamos capturar de outra forma
                    // Vamos fazer uma query equivalente
                    String query = "SELECT DISTINCT m.moto_patio_atual_id AS idpatio, " +
                                  "m.contrato_moto_id AS idcon, " +
                                  "SUM(m.quilometragem_moto) AS soma_quilometragem " +
                                  "FROM mt_moto_java m " +
                                  "WHERE m.moto_patio_atual_id IS NOT NULL " +
                                  "AND m.contrato_moto_id IS NOT NULL " +
                                  "GROUP BY m.moto_patio_atual_id, m.contrato_moto_id " +
                                  "ORDER BY m.moto_patio_atual_id, m.contrato_moto_id";
                    
                    List<Map<String, Object>> resultados = new ArrayList<>();
                    try (ResultSet rs = cs.getConnection().createStatement().executeQuery(query)) {
                        while (rs.next()) {
                            Map<String, Object> row = new HashMap<>();
                            row.put("idpatio", rs.getObject("idpatio"));
                            row.put("idcon", rs.getObject("idcon"));
                            row.put("soma_quilometragem", rs.getObject("soma_quilometragem"));
                            resultados.add(row);
                        }
                    }
                    return resultados;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório de motos por pátio: " + e.getMessage(), e);
        }
    }

    /**
     * Executa a procedure do package pkg_relatorios.proc_json
     */
    public String gerarJsonRelatorioFromPackage() {
        try {
            String query = "SELECT id_moto, placa_moto, modelo_moto, quilometragem_moto FROM mt_moto_java";
            return jsonUtilService.criaJsonFromPackage(query);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório JSON do package: " + e.getMessage(), e);
        }
    }
}

