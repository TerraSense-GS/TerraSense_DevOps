package br.com.terrasense.dto.dadosiotclima;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DadosIotClimaRequestDTO(

        double temperatura,

        @DecimalMin("0.0")
        @DecimalMax("100.0")
        double umidade,

        @PositiveOrZero
        double chuva,

        @PositiveOrZero
        double ventoKmh,

        @Size(max = 50)
        String clima,

        @NotNull
        LocalDate dataLeitura,

        @NotNull
        Long idPlantacao

) {
}