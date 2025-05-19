package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_CONTRATO_JAVA")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;

    private String clausulasContrato;

    @NotNull
    private LocalDateTime dataDeEntradaContrato;

    @NotNull
    private LocalDateTime horarioDeDevolucaoContrato;

    @NotNull
    private LocalDateTime dataDeExpiracaoContrato;

    @Column(name = "renovacaoAutomaticaContrato", columnDefinition = "NUMBER(1)")
    private Integer renovacaoAutomaticaContrato;

    private LocalDateTime dataUltimaRenovacaoContrato;

    private int numeroRenovacoesContrato;

    @Column(name = "ativoContrato", columnDefinition = "NUMBER(1)")
    private Integer ativoContrato;

    @NotNull
    private BigDecimal valorToralContrato;

    @NotNull
    private int quantidadeParcelas;

    @ManyToOne
    @JoinColumn(name = "usuarioContratoId")
    @JsonIgnoreProperties("contratos")
    private Usuario usuarioContrato;

    @ManyToOne
    @JoinColumn(name = "motoContratoId")
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
