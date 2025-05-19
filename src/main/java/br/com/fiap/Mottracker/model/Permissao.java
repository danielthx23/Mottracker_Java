package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_PERMISSAO_JAVA")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermissao;

    @NotNull
    @Size(max = 100)
    private String nomePermissao;

    private String descricao;

    @OneToMany(mappedBy = "permissao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("permissao")
    private List<UsuarioPermissao> usuarioPermissoes = new ArrayList<>();
}
