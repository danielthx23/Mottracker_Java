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
@Table(name = "MT_TELEFONE_JAVA")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelefone;

    @NotNull
    private String numero;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    @JsonIgnoreProperties("telefones")
    private Usuario usuario;
}
