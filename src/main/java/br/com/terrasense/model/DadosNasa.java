package br.com.terrasense.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TB_DADOS_NASA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idDadoNasa")
@Schema(
        name = "DadosNasa",
        description = "Representa dados climáticos obtidos por satélite via NASA"
)
public class DadosNasa {

    @Id
    @SequenceGenerator(name = "dados_nasa_seq", sequenceName = "SEQ_DADOS_NASA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dados_nasa_seq")
    @Column(name = "ID_DADO_NASA")
    @Schema(
            description = "ID único do dado NASA",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idDadoNasa;

    @NotNull
    @Column(name = "DT_REFERENCIA", nullable = false)
    @Schema(
            description = "Data de referência dos dados do satélite",
            example = "2026-05-20"
    )
    private LocalDate dataReferencia;

    @Column(name = "NUM_RADIACAO")
    @Schema(
            description = "Radiação solar incidente",
            example = "24.50"
    )
    private double radiacaoSolar;

    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    @Column(name = "NUM_NDVI")
    @Schema(
            description = "Índice NDVI de vegetação",
            example = "0.7425"
    )
    private double ndvi;

    @Column(name = "NUM_TEMP_SAT")
    @Schema(
            description = "Temperatura da superfície via satélite",
            example = "31.80"
    )
    private double temperaturaSat;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(name = "NUM_UMIDADE_SAT")
    @Schema(
            description = "Umidade relativa estimada via satélite",
            example = "68.50"
    )
    private double umidadeSat;

    @Column(name = "NUM_PRECIP_SAT")
    @Schema(
            description = "Precipitação estimada pelo satélite",
            example = "12.30"
    )
    private double precipSat;

    @Column(name = "DT_COLETA")
    @Schema(
            description = "Data da coleta dos dados",
            example = "2026-05-20"
    )
    private LocalDate dataColeta;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLANTACAO", nullable = false)
    private Plantacao plantacao;
}