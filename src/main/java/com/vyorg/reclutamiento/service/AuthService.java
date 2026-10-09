package main.java.com.vyorg.reclutamiento.service;

import main.java.com.vyorg.reclutamiento.dto.CredencialesDto;
import main.java.com.vyorg.reclutamiento.dto.RegistroUsuarioDto;
import main.java.com.vyorg.reclutamiento.dto.UsuarioDto;
import main.java.com.vyorg.reclutamiento.exception.AuthException;
import main.java.com.vyorg.reclutamiento.mapper.UsuarioMapper;
import main.java.com.vyorg.reclutamiento.model.Usuario;
import main.java.com.vyorg.reclutamiento.repository.UsuarioRepository;
import main.java.com.vyorg.reclutamiento.security.PasswordHasher;
import main.java.com.vyorg.reclutamiento.validation.UsuarioValidator;

public class AuthService {

    private static final String ROL_POR_DEFECTO = "USER";
    private static final String MENSAJE_CREDENCIALES = "Correo o contraseña incorrectos.";

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(UsuarioRepository usuarioRepository, PasswordHasher passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    public void registrar(RegistroUsuarioDto dto) {
        RegistroUsuarioDto datos = UsuarioValidator.validarRegistro(dto);
        if (usuarioRepository.buscarPorCorreo(datos.correo()).isPresent()) {
            throw new AuthException("Ya existe un usuario registrado con ese correo.");
        }
        String hash = passwordHasher.hash(datos.contrasena());
        usuarioRepository.guardar(UsuarioMapper.aEntidad(datos, hash, ROL_POR_DEFECTO));
    }

    public UsuarioDto iniciarSesion(CredencialesDto dto) {
        CredencialesDto datos = UsuarioValidator.validarCredenciales(dto);
        Usuario usuario = usuarioRepository.buscarPorCorreo(datos.correo())
                .orElseThrow(() -> new AuthException(MENSAJE_CREDENCIALES));
        if (!passwordHasher.coincide(datos.contrasena(), usuario.getContrasenaHash())) {
            throw new AuthException(MENSAJE_CREDENCIALES);
        }
        return UsuarioMapper.aDto(usuario);
    }
}
