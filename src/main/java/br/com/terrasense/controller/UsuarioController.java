package br.com.terrasense.controller;

import br.com.terrasense.dto.usuario.LoginRequestDTO;
import br.com.terrasense.dto.usuario.LoginResponseDTO;
import br.com.terrasense.dto.usuario.UsuarioRequestDTO;
import br.com.terrasense.dto.usuario.UsuarioResponseDTO;
import br.com.terrasense.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagedResourcesAssembler<UsuarioResponseDTO> pagedAssembler;

    @PostMapping
    @Operation(
            summary = "Cadastrar usuário",
            description = "Cadastra um novo usuário no sistema TerraSense"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> cadastrar(
            @RequestBody @Valid UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO usuario = usuarioService.cadastrar(dto);

        EntityModel<UsuarioResponseDTO> model = adicionarLinks(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(model);
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista paginada de usuários cadastrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<UsuarioResponseDTO>>> listar(
            Pageable pageable
    ) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listar(pageable);

        PagedModel<EntityModel<UsuarioResponseDTO>> model =
                pagedAssembler.toModel(
                        usuarios,
                        this::adicionarLinks
                );

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico baseado no ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> buscarPorId(
            @PathVariable Long id
    ) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(adicionarLinks(usuario));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO usuario = usuarioService.atualizar(id, dto);

        return ResponseEntity.ok(adicionarLinks(usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema pelo ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<UsuarioResponseDTO> adicionarLinks(
            UsuarioResponseDTO usuario
    ) {
        return EntityModel.of(
                usuario,
                linkTo(methodOn(UsuarioController.class)
                        .buscarPorId(usuario.idUsuario()))
                        .withSelfRel(),

                linkTo(methodOn(UsuarioController.class)
                        .listar(Pageable.unpaged()))
                        .withRel("usuarios")
        );
    }

    @PostMapping("/login")
    @Operation(
            summary = "Realizar login",
            description = "Autentica um usuário através de e-mail e senha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO dto
    ) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }
}