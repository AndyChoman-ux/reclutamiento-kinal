package main.java.com.vyorg.reclutamiento.validation;

import main.java.com.vyorg.reclutamiento.exception.ValidationException;
import java.util.regex.Pattern;

public final class Validador {

    private static final Pattern CORREO = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONO = Pattern.compile("^[0-9+\\- ]{8,15}$");

    private Validador() { }

    public static String requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException("El campo " + campo + " es obligatorio.");
        }
        return valor.trim();
    }

    public static String correo(String valor) {
        String correo = requerido(valor, "correo");
        if (!CORREO.matcher(correo).matches()) {
            throw new ValidationException("El correo no tiene un formato válido.");
        }
        return correo.toLowerCase();
    }

    public static String telefono(String valor) {
        String telefono = requerido(valor, "teléfono");
        if (!TELEFONO.matcher(telefono).matches()) {
            throw new ValidationException("El teléfono debe tener entre 8 y 15 dígitos.");
        }
        return telefono;
    }
}
