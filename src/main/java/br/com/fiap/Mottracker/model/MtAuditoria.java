package br.com.fiap.Mottracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mt_auditoria")
public class MtAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audit")
    private Long idAudit;

    @Column(name = "tabela_nome", length = 100)
    private String tabelaNome;

    @Column(name = "usuario_nome", length = 100)
    private String usuarioNome;

    @Column(name = "tipo_operacao", length = 10)
    private String tipoOperacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora")
    private Date dataHora;

    @Lob
    @Column(name = "valores_old", columnDefinition = "CLOB")
    private String valoresOld;

    @Lob
    @Column(name = "valores_new", columnDefinition = "CLOB")
    private String valoresNew;
}

