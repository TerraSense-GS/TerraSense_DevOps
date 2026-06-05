package br.com.terrasense.dto.usuario;

public record UsuarioResponseDTO(

        Long idUsuario,
        String nomeCompleto,
        String telefone,
        String email,
        String perfilCargo

) {
}