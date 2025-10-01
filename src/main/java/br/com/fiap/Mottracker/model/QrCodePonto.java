package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_QRCODE_PONTO_JAVA")
public class QrCodePonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQrCodePonto;

    @NotNull
    private String identificadorQrCode;

    private float posX;

    private float posY;

    @ManyToOne
    @JoinColumn(name = "layout_patio_id")
    @JsonIgnoreProperties("qrCodesLayoutPatio")
    private LayoutPatio layoutPatio;
}
