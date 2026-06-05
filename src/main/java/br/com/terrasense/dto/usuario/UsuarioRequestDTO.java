package br.com.terrasense.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @NotBlank
        @Size(max = 100)
        String nomeCompleto,

        @Size(max = 20)
        String telefone,

        @NotBlank
        @Email(message = "E-mail inválido")
        @Size(max = 150)
        String email,

        @NotBlank
        @Size(max = 255)
        String senha,

        @NotBlank
        @Size(max = 20)
        String perfilCargo

) {
}