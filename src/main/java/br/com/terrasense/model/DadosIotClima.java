package br.com.terrasense.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_DADOS_IOT_CLIMA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idDadoIot")
@Schema(
        name = "DadosIotClima",
        description = "Representa dados climáticos coletados por sensores IoT"
)
public class DadosIotClima {

    @Id
    @SequenceGenerator(name = "dados_iot_seq", sequenceName = "SEQ_DADOS_IOT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dados_iot_seq")
    @Column(name = "ID_DADO_IOT")
    @Schema(
            description = "ID único do dado IoT",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idDadoIot;

    @Column(name = "NUM_TEMPERATURA")
    @Schema(
            description = "Temperatura do ar medida pelo sensor",
            example = "29.50"
    )
    private double temperatura;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(name = "NUM_UMIDADE")
    @Schema(
            description = "Umidade relativa do ar",
            example = "72.30"
    )
    private double umidade;

    @PositiveOrZero
    @Column(name = "NUM_CHUVA")
    @Schema(
            description = "Volume de chuva registrado",
            example = "8.20"
    )
    private double chuva;

    @PositiveOrZero
    @Column(name = "NUM_VENTO_KMH")
    @Schema(
            description = "Velocidade do vento em km/h",
            example = "14.60"
    )
    private double ventoKmh;

    @Size(max = 50)
    @Column(name = "DS_CLIMA", length = 50)
    @Schema(
            description = "Condição climática registrada",
            example = "ENSOLARADO"
    )
    private String clima;

    @NotNull
    @Column(name = "DT_LEITURA", nullable = false)
    @Schema(
            description = "Data da leitura do sensor",
            example = "2026-05-20"
    )
    private LocalDate dataLeitura;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLANTACAO", nullable = false)
    private Plantacao plantacao;
}