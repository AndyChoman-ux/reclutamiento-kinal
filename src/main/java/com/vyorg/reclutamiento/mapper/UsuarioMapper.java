package main.java.com.vyorg.reclutamiento.mapper;

import main.java.com.vyorg.reclutamiento.dto.RegistroUsuarioDto;
import main.java.com.vyorg.reclutamiento.dto.UsuarioDto;
import main.java.com.vyorg.reclutamiento.model.Usuario;

public final class UsuarioMapper {

    private UsuarioMapper() { }

    public static UsuarioDto aDto(Usuario usuario) {
        return new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getApellido(),
                usuario.getCorreo(), usuario.getRol());
    }

    public static Usuario aEntidad(RegistroUsuarioDto dto, String contrasenaHash, String rol) {
        return new Usuario(0, dto.nombre(), dto.apellido(), dto.correo(), contrasenaHash, rol);
    }
}
