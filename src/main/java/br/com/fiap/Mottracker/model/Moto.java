package br.com.fiap.Mottracker.model;

import br.com.fiap.Mottracker.enums.Estados;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_MOTO_JAVA")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMoto;

    @NotNull
    private String placaMoto;

    @NotNull
    private String modeloMoto; // Mottu Sport, Pop, E

    @NotNull
    private int anoMoto;

    private String identificadorMoto;

    private int quilometragemMoto;

    @Enumerated(EnumType.ORDINAL)
    private Estados estadoMoto;

    private String condicoesMoto;

    @OneToOne
    @JoinColumn(name = "contratoMotoId")
    @JsonIgnoreProperties("motoContrato")
    private Contrato contratoMoto;

    @ManyToOne
    @JoinColumn(name = "motoPatioAtualId")
    @JsonIgnoreProperties("motosPatioAtual")
    private Patio motoPatioAtual;

    private Long motoPatioOrigemId;
}
