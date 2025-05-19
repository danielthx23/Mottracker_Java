package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_USUARIO_PERMISSAO_JAVA")
@IdClass(UsuarioPermissaoId.class)
public class UsuarioPermissao {

    @Id
    @Column(name = "usuarioId")
    private Long usuarioId;

    @Id
    @Column(name = "permissaoId")
    private Long permissaoId;

    private String papel;

    @ManyToOne
    @JoinColumn(name = "usuarioId", insertable = false, updatable = false)
    @JsonIgnoreProperties("usuarioPermissoes")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "permissaoId", insertable = false, updatable = false)
    @JsonIgnoreProperties("usuarioPermissoes")
    private Permissao permissao;
}
