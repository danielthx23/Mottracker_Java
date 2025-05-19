package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_LAYOUT_PATIO_JAVA")
public class LayoutPatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLayoutPatio;

    private String descricao;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NotNull
    private BigDecimal largura;

    @NotNull
    private BigDecimal comprimento;

    private BigDecimal altura;

    @OneToOne
    @JoinColumn(name = "patioLayoutPatioId")
    @JsonIgnoreProperties("layoutPatio")
    private Patio patioLayoutPatio;

    @OneToMany(mappedBy = "layoutPatio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("layoutPatio")
    private List<QrCodePonto> qrCodesLayoutPatio = new ArrayList<>();
}
