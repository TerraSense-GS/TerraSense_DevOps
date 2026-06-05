package br.com.terrasense.controller;

import br.com.terrasense.dto.propriedade.PropriedadeRequestDTO;
import br.com.terrasense.dto.propriedade.PropriedadeResponseDTO;
import br.com.terrasense.service.PropriedadeService;
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
@RequestMapping("/propriedades")
@Tag(name = "Propriedades", description = "Endpoints para gerenciamento de propriedades rurais")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private PagedResourcesAssembler<PropriedadeResponseDTO> pagedAssembler;

    @PostMapping
    @Operation(summary = "Cadastrar propriedade", description = "Cadastra uma nova propriedade rural")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propriedade cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<EntityModel<PropriedadeResponseDTO>> cadastrar(
            @RequestBody @Valid PropriedadeRequestDTO dto
    ) {
        PropriedadeResponseDTO propriedade = propriedadeService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adicionarLinks(propriedade));
    }

    @GetMapping
    @Operation(summary = "Listar propriedades", description = "Retorna uma lista paginada de propriedades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de propriedades retornada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PropriedadeResponseDTO>>> listar(
            Pageable pageable
    ) {
        Page<PropriedadeResponseDTO> propriedades = propriedadeService.listar(pageable);

        PagedModel<EntityModel<PropriedadeResponseDTO>> model =
                pagedAssembler.toModel(propriedades, this::adicionarLinks);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar propriedade por ID", description = "Retorna uma propriedade pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propriedade encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    public ResponseEntity<EntityModel<PropriedadeResponseDTO>> buscarPorId(
            @PathVariable Long id
    ) {
        PropriedadeResponseDTO propriedade = propriedadeService.buscarPorId(id);

        return ResponseEntity.ok(adicionarLinks(propriedade));
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Buscar propriedades por usuário", description = "Retorna propriedades vinculadas a um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por usuário realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PropriedadeResponseDTO>>> buscarPorUsuario(
            @PathVariable Long idUsuario,
            Pageable pageable
    ) {
        Page<PropriedadeResponseDTO> propriedades =
                propriedadeService.buscarPorUsuario(idUsuario, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(propriedades, this::adicionarLinks)
        );
    }

    @GetMapping("/tipo")
    @Operation(summary = "Buscar propriedades por tipo", description = "Retorna propriedades de acordo com o tipo informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca por tipo realizada com sucesso")
    })
    public ResponseEntity<PagedModel<EntityModel<PropriedadeResponseDTO>>> buscarPorTipo(
            @RequestParam String tipoPropriedade,
            Pageable pageable
    ) {
        Page<PropriedadeResponseDTO> propriedades =
                propriedadeService.buscarPorTipo(tipoPropriedade, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(propriedades, this::adicionarLinks)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar propriedade", description = "Atualiza os dados de uma propriedade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propriedade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Propriedade ou usuário não encontrado")
    })
    public ResponseEntity<EntityModel<PropriedadeResponseDTO>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PropriedadeRequestDTO dto
    ) {
        PropriedadeResponseDTO propriedade = propriedadeService.atualizar(id, dto);

        return ResponseEntity.ok(adicionarLinks(propriedade));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar propriedade", description = "Remove uma propriedade pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Propriedade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        propriedadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<PropriedadeResponseDTO> adicionarLinks(
            PropriedadeResponseDTO propriedade
    ) {
        return EntityModel.of(
                propriedade,
                linkTo(methodOn(PropriedadeController.class)
                        .buscarPorId(propriedade.idPropriedade()))
                        .withSelfRel(),

                linkTo(methodOn(PropriedadeController.class)
                        .listar(Pageable.unpaged()))
                        .withRel("propriedades"),

                linkTo(methodOn(UsuarioController.class)
                        .buscarPorId(propriedade.idUsuario()))
                        .withRel("usuario")
        );
    }
}