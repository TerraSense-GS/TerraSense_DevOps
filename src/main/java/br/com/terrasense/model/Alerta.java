package br.com.terrasense.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_ALERTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idAlerta")
@Schema(
        name = "Alerta",
        description = "Representa um alerta gerado pelo sistema TerraSense"
)
public class Alerta {

    @Id
    @SequenceGenerator(name = "alerta_seq", sequenceName = "SEQ_ALERTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    @Column(name = "ID_ALERTA")
    @Schema(
            description = "ID único do alerta",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idAlerta;

    @NotBlank
    @Size(max = 50)
    @Column(name = "TP_ALERTA", nullable = false, length = 50)
    @Schema(
            description = "Tipo do alerta",
            example = "SECA"
    )
    private String tipoAlerta;

    @NotBlank
    @Size(max = 500)
    @Column(name = "DS_MENSAGEM", nullable = false, length = 500)
    @Schema(
            description = "Mensagem detalhando o alerta",
            example = "Baixo índice de umidade detectado na plantação."
    )
    private String mensagem;

    @NotBlank
    @Size(max = 20)
    @Column(name = "NV_ALERTA", nullable = false, length = 20)
    @Schema(
            description = "Nível de severidade do alerta",
            example = "ALTO"
    )
    private String nivelAlerta;

    @NotBlank
    @Size(max = 20)
    @Column(name = "ST_ALERTA", nullable = false, length = 20)
    @Schema(
            description = "Status atual do alerta",
            example = "ATIVO"
    )
    private String statusAlerta;

    @NotNull
    @Column(name = "DT_ALERTA", nullable = false)
    @Schema(
            description = "Data de geração do alerta",
            example = "2026-05-20"
    )
    private LocalDate dataAlerta;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLANTACAO", nullable = false)
    private Plantacao plantacao;
}