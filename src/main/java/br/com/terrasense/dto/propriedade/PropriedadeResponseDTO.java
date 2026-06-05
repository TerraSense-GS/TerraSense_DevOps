package br.com.terrasense.dto.propriedade;

public record PropriedadeResponseDTO(
        Long idPropriedade,
        String nomePropriedade,
        String tipoPropriedade,
        double areaTotal,
        EnderecoDTO endereco,
        Long idUsuario
) {
}