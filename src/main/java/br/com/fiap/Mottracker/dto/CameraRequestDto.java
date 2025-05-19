package br.com.fiap.Mottracker.dto;

import br.com.fiap.Mottracker.enums.CameraStatus;
import jakarta.validation.constraints.*;

public record CameraRequestDto(

        @NotBlank
        String nomeCamera,

        @Size(max = 255)
        String ipCamera,

        @NotNull
        CameraStatus status,

        Float posX,

        Float posY,

        @NotNull
        Long patioId

) {}
