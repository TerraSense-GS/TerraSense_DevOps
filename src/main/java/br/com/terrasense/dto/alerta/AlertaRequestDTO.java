package br.com.terrasense.dto.alerta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlertaRequestDTO(

        @NotBlank
        @Size(max = 50)
        String tipoAlerta,

        @NotBlank
        @Size(max = 500)
        String mensagem,

        @NotBlank
        @Size(max = 20)
        String nivelAlerta,

        @NotBlank
        @Size(max = 20)
        String statusAlerta,

        @NotNull
        LocalDate dataAlerta,

        @NotNull
        Long idPlantacao

) {
}