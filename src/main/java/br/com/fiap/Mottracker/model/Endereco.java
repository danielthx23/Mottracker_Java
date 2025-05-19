package br.com.fiap.Mottracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "MT_ENDERECO_JAVA")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEndereco;

    @NotBlank
    @Size(max = 150)
    private String logradouro; // Rua, avenida, etc.

    @NotBlank
    @Size(max = 20)
    private String numero;

    @Size(max = 100)
    private String complemento;

    @NotBlank
    @Size(max = 100)
    private String bairro;

    @NotBlank
    @Size(max = 100)
    private String cidade;

    @NotBlank
    @Size(max = 2)
    private String estado; // Ex: "SP", "RJ"

    @NotBlank
    @Size(max = 10)
    private String cep;

    @Size(max = 100)
    private String referencia; // Ponto de referência (opcional)

    @OneToOne
    @JoinColumn(name = "patioEnderecoId")
    @JsonIgnoreProperties("enderecoPatio")
    private Patio patioEndereco;
}
