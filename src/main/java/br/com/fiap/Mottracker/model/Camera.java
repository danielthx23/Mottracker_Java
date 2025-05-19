package br.com.fiap.Mottracker.model;

import br.com.fiap.Mottracker.enums.CameraStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MT_CAMERA_JAVA")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCamera;

    @NotBlank
    private String nomeCamera;

    @Size(max = 255)
    private String ipCamera;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private CameraStatus status;

    private Float posX;

    private Float posY;

    @ManyToOne
    @JoinColumn(name = "patioId")
    @JsonIgnoreProperties("camerasPatio")
    private Patio patio;

}
