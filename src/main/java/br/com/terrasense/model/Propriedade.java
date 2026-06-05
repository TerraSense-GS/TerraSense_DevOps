package br.com.terrasense.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_PROPRIEDADE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPropriedade")
@Schema(
        name = "Propriedade",
        description = "Representa uma propriedade rural cadastrada no sistema"
)
public class Propriedade {

    @Id
    @SequenceGenerator(name = "propriedade_seq", sequenceName = "SEQ_PROPRIEDADE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "propriedade_seq")
    @Column(name = "ID_PROPRIEDADE")
    @Schema(
            description = "ID único da propriedade",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idPropriedade;

    @NotBlank
    @Size(max = 150)
    @Column(name = "NM_PROPRIEDADE", nullable = false, length = 150)
    @Schema(
            description = "Nome da propriedade rural",
            example = "Fazenda Boa Esperança"
    )
    private String nomePropriedade;

    @NotBlank
    @Size(max = 50)
    @Column(name = "TP_PROPRIEDADE", nullable = false, length = 50)
    @Schema(
            description = "Tipo da propriedade",
            example = "FAZENDA"
    )
    private String tipoPropriedade;

    @Embedded
    private Endereco endereco;

    @Positive
    @Column(name = "NUM_AREA_TOTAL")
    @Schema(
            description = "Área total da propriedade em hectares",
            example = "150.50"
    )
    private double areaTotal;

    @Schema(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(hidden = true)
    private List<Plantacao> plantacoes = new ArrayList<>();
}