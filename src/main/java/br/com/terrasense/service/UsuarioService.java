package br.com.terrasense.service;

import br.com.terrasense.dto.usuario.LoginRequestDTO;
import br.com.terrasense.dto.usuario.LoginResponseDTO;
import br.com.terrasense.dto.usuario.UsuarioRequestDTO;
import br.com.terrasense.dto.usuario.UsuarioResponseDTO;
import br.com.terrasense.exception.DuplicateResourceException;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.Usuario;
import br.com.terrasense.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com ID: " + id)
                );

        return toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Já existe um usuário cadastrado com este e-mail.");
        }

        Usuario usuario = new Usuario();

        usuario.setNomeCompleto(dto.nomeCompleto());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setPerfilCargo(dto.perfilCargo());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return toResponseDTO(usuarioSalvo);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com ID: " + id)
                );

        if (usuarioRepository.existsByEmailAndIdUsuarioNot(dto.email(), id)) {
            throw new DuplicateResourceException("Já existe um usuário cadastrado com este e-mail.");
        }

        usuario.setNomeCompleto(dto.nomeCompleto());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setPerfilCargo(dto.perfilCargo());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return toResponseDTO(usuarioAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com ID: " + id)
                );

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNomeCompleto(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getPerfilCargo()
        );
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository
                .findByEmailAndSenha(dto.email(), dto.senha())
                .orElseThrow(() -> new ResourceNotFoundException("E-mail ou senha inválidos."));

        return new LoginResponseDTO(
                usuario.getIdUsuario()
        );
    }
}