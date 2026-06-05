package br.com.terrasense.dto.propriedade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PropriedadeRequestDTO(

        @NotBlank
        @Size(max = 150)
        String nomePropriedade,

        @NotBlank
        @Size(max = 50)
        String tipoPropriedade,

        @Positive
        double areaTotal,

        @NotNull
        Long idUsuario,

        @Valid
        EnderecoDTO endereco

) {
}