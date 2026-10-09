package main.java.com.vyorg.reclutamiento.validation;

import main.java.com.vyorg.reclutamiento.dto.AspiranteDto;
import main.java.com.vyorg.reclutamiento.exception.ValidationException;

public final class AspiranteValidator {

    private static final int EDAD_MINIMA = 18;
    private static final int EDAD_MAXIMA = 100;

    private AspiranteValidator() { }

    public static AspiranteDto validar(AspiranteDto dto) {
        AspiranteDto datos = new AspiranteDto(
                dto.id(),
                Validador.requerido(dto.nombre(), "nombre"),
                Validador.requerido(dto.apellido(), "apellido"),
                Validador.correo(dto.correo()),
                Validador.telefono(dto.telefono()),
                Validador.requerido(dto.direccion(), "dirección"),
                dto.edad());
        validarEdad(datos.edad());
        return datos;
    }

    private static void validarEdad(int edad) {
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            throw new ValidationException("La edad debe estar entre "
                    + EDAD_MINIMA + " y " + EDAD_MAXIMA + " años.");
        }
    }
}
