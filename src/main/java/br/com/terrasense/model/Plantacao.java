package br.com.terrasense.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_PLANTACAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPlantacao")
@Schema(
        name = "Plantacao",
        description = "Representa uma plantação monitorada pelo sistema"
)
public class Plantacao {

    @Id
    @SequenceGenerator(name = "plantacao_seq", sequenceName = "SEQ_PLANTACAO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plantacao_seq")
    @Column(name = "ID_PLANTACAO")
    @Schema(
            description = "ID único da plantação",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idPlantacao;

    @NotBlank
    @Size(max = 150)
    @Column(name = "NM_PLANTACAO", nullable = false, length = 150)
    @Schema(
            description = "Nome da plantação",
            example = "Talhão Norte"
    )
    private String nomePlantacao;

    @NotBlank
    @Size(max = 80)
    @Column(name = "TP_PLANTACAO", nullable = false, length = 80)
    @Schema(
            description = "Tipo de cultura",
            example = "SOJA"
    )
    private String tipoPlantacao;

    @NotNull
    @Positive
    @Column(name = "NUM_AREA_HA")
    @Schema(
            description = "Área da plantação em hectares",
            example = "50.75"
    )
    private double areaHectares;

    @Column(name = "DT_PLANTIO")
    @Schema(
            description = "Data do plantio",
            example = "2026-03-15"
    )
    private LocalDate dataPlantio;

    @NotBlank
    @Size(max = 20)
    @Column(name = "ST_PLANTACAO", nullable = false, length = 20)
    @Schema(
            description = "Status da plantação",
            example = "PLANTADA"
    )
    private String statusPlantacao;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROPRIEDADE", nullable = false)
    private Propriedade propriedade;

    @Schema(hidden = true)
    @OneToMany(mappedBy = "plantacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DadosNasa> dadosNasa = new ArrayList<>();

    @Schema(hidden = true)
    @OneToMany(mappedBy = "plantacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DadosIotClima> dadosIotClima = new ArrayList<>();

    @Schema(hidden = true)
    @OneToMany(mappedBy = "plantacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alerta> alertas = new ArrayList<>();
}