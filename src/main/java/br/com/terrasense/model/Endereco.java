package br.com.terrasense.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Endereco",
        description = "Endereço incorporado à propriedade rural"
)
public class Endereco {

    @NotBlank
    @Size(max = 100)
    @Column(name = "nm_cidade", nullable = false, length = 100)
    @Schema(
            description = "Cidade da propriedade",
            example = "Rondonópolis"
    )
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(name = "sg_estado", nullable = false, length = 2)
    @Schema(
            description = "Sigla do estado",
            example = "MT"
    )
    private String estado;

    @Size(max = 10)
    @Column(name = "num_cep", length = 10)
    @Schema(
            description = "CEP da propriedade",
            example = "78700-000"
    )
    private String cep;
}