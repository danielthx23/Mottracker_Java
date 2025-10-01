package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_CONTRATO_JAVA", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"usuario_contrato_id", "moto_contrato_id", "data_de_entrada_contrato"})
       })
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "clausulas_contrato")
    private String clausulasContrato;

    @NotNull
    @Column(name = "data_de_entrada_contrato")
    private LocalDate dataDeEntradaContrato;

    @Column(name = "horario_de_devolucao_contrato")
    private LocalTime horarioDeDevolucaoContrato;

    @NotNull
    @Column(name = "data_de_expiracao_contrato")
    private LocalDate dataDeExpiracaoContrato;

    @Column(name = "renovacao_automatica_contrato", columnDefinition = "NUMBER(1)")
    private Integer renovacaoAutomaticaContrato;

    @Column(name = "data_ultima_renovacao_contrato")
    private LocalDateTime dataUltimaRenovacaoContrato;

    @Column(name = "numero_renovacoes_contrato")
    private int numeroRenovacoesContrato;

    @Column(name = "ativo_contrato", columnDefinition = "NUMBER(1)")
    private Integer ativoContrato;

    @NotNull
    @Column(name = "valor_toral_contrato")
    private BigDecimal valorToralContrato;

    @NotNull
    @Column(name = "quantidade_parcelas")
    private int quantidadeParcelas;

    @ManyToOne
    @JoinColumn(name = "usuario_contrato_id")
    @JsonIgnoreProperties("contratos")
    private Usuario usuarioContrato;

    @ManyToOne
    @JoinColumn(name = "moto_contrato_id")
    @JsonIgnoreProperties("contratos")
    private Moto motoContrato;

    @Transient
    public boolean isRenovacaoAutomatica() {
        return renovacaoAutomaticaContrato != null && renovacaoAutomaticaContrato == 1;
    }

    public void setRenovacaoAutomatica(boolean valor) {
        this.renovacaoAutomaticaContrato = valor ? 1 : 0;
    }
}
