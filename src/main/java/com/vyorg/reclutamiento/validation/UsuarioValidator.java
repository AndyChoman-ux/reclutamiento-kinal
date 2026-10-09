package main.java.com.vyorg.reclutamiento.validation;

import main.java.com.vyorg.reclutamiento.dto.CredencialesDto;
import main.java.com.vyorg.reclutamiento.dto.RegistroUsuarioDto;
import main.java.com.vyorg.reclutamiento.exception.ValidationException;

public final class UsuarioValidator {

    private static final int LONGITUD_MINIMA_CONTRASENA = 6;

    private UsuarioValidator() { }

    public static RegistroUsuarioDto validarRegistro(RegistroUsuarioDto dto) {
        RegistroUsuarioDto datos = new RegistroUsuarioDto(
                Validador.requerido(dto.nombre(), "nombre"),
                Validador.requerido(dto.apellido(), "apellido"),
                Validador.correo(dto.correo()),
                dto.contrasena());
        validarLongitudContrasena(datos.contrasena());
        return datos;
    }

    public static CredencialesDto validarCredenciales(CredencialesDto dto) {
        String correo = Validador.correo(dto.correo());
        if (dto.contrasena() == null || dto.contrasena().isEmpty()) {
            throw new ValidationException("El campo contraseña es obligatorio.");
        }
        return new CredencialesDto(correo, dto.contrasena());
    }

    private static void validarLongitudContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < LONGITUD_MINIMA_CONTRASENA) {
            throw new ValidationException("La contraseña debe tener al menos "
                    + LONGITUD_MINIMA_CONTRASENA + " caracteres.");
        }
    }
}
