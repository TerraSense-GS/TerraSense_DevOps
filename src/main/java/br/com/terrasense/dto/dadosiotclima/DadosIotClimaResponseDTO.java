package br.com.terrasense.dto.dadosiotclima;

import java.time.LocalDate;

public record DadosIotClimaResponseDTO(
        Long idDadoIot,
        double temperatura,
        double umidade,
        double chuva,
        double ventoKmh,
        String clima,
        LocalDate dataLeitura,
        Long idPlantacao

) {
}