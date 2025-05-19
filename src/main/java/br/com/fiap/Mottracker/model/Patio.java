package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MT_PATIO_JAVA")
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPatio;

    @NotNull
    private String nomePatio;

    private int motosTotaisPatio;

    private int motosDisponiveisPatio;

    private LocalDateTime dataPatio;

    @OneToMany(mappedBy = "motoPatioAtual", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("motoPatioAtual")
    private List<Moto> motosPatioAtual = new ArrayList<>();

    @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("patio")
    private List<Camera> camerasPatio = new ArrayList<>();

    @OneToOne(mappedBy = "patioLayoutPatio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patioLayoutPatio")
    private LayoutPatio layoutPatio;

    @OneToOne(mappedBy = "patioEndereco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patioEndereco")
    private Endereco enderecoPatio;
}
