package br.com.terrasense.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idUsuario")
@Schema(
        name = "Usuario",
        description = "Representa um usuário do sistema TerraSense"
)
public class Usuario {

    @Id
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @Column(name = "ID_USUARIO")
    @Schema(
            description = "ID único do usuário",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idUsuario;

    @NotBlank
    @Size(max = 100)
    @Column(name = "NM_COMPLETO", nullable = false, length = 100)
    @Schema(
            description = "Nome completo do usuário",
            example = "Vitor Dalmagro"
    )
    private String nomeCompleto;

    @Size(max = 20)
    @Column(name = "NUM_TELEFONE", length = 20)
    @Schema(
            description = "Telefone do usuário",
            example = "11999999999"
    )
    private String telefone;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(name = "DS_EMAIL", nullable = false, unique = true, length = 150)
    @Schema(
            description = "E-mail único do usuário",
            example = "vitor@email.com"
    )
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "DS_SENHA", nullable = false, length = 255)
    @Schema(
            description = "Senha de acesso do usuário",
            example = "123456"
    )
    private String senha;

    @NotBlank
    @Size(max = 20)
    @Column(name = "TP_PERFIL_CARGO", nullable = false, length = 20)
    @Schema(
            description = "Perfil ou cargo do usuário",
            example = "PRODUTOR"
    )
    private String perfilCargo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(hidden = true)
    private List<Propriedade> propriedades = new ArrayList<>();
}