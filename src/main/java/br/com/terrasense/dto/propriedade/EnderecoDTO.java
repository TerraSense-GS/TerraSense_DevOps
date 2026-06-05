package br.com.terrasense.dto.propriedade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(

        @NotBlank
        @Size(max = 100)
        String cidade,

        @NotBlank
        @Size(min = 2, max = 2)
        String estado,

        @Size(max = 10)
        String cep

) {
}