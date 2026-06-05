package br.com.terrasense.dto.dadosnasa;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosNasaRequestDTO(

        @NotNull
        LocalDate dataReferencia,

        double radiacaoSolar,

        @DecimalMin("-1.0")
        @DecimalMax("1.0")
        double ndvi,

        double temperaturaSat,

        @DecimalMin("0.0")
        @DecimalMax("100.0")
        double umidadeSat,

        double precipSat,

        LocalDate dataColeta,

        @NotNull
        Long idPlantacao

) {
}