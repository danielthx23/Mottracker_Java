package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_USUARIO_JAVA")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotNull
    private String nomeUsuario;

    @NotNull
    private String cpfUsuario;

    @NotNull
    private String senhaUsuario;

    @NotNull
    @Column(unique = true)
    private String cnhUsuario;

    @NotNull
    @Email
    @Column(unique = true)
    private String emailUsuario;

    private String tokenUsuario;

    private LocalDate dataNascimentoUsuario;

    private LocalDateTime criadoEmUsuario;

    @OneToOne(mappedBy = "usuarioContrato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Contrato contratoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("usuario")
    private List<Telefone> telefones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("usuario")
    private List<UsuarioPermissao> usuarioPermissoes = new ArrayList<>();
}