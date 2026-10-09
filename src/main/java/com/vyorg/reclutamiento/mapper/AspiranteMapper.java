package main.java.com.vyorg.reclutamiento.mapper;

import main.java.com.vyorg.reclutamiento.dto.AspiranteDto;
import main.java.com.vyorg.reclutamiento.model.Aspirante;

public final class AspiranteMapper {

    private AspiranteMapper() { }

    public static AspiranteDto aDto(Aspirante aspirante) {
        return new AspiranteDto(aspirante.getId(), aspirante.getNombre(), aspirante.getApellido(),
                aspirante.getCorreo(), aspirante.getTelefono(), aspirante.getDireccion(),
                aspirante.getEdad());
    }

    public static Aspirante aEntidad(AspiranteDto dto) {
        return new Aspirante(dto.id(), dto.nombre(), dto.apellido(), dto.correo(),
                dto.telefono(), dto.direccion(), dto.edad());
    }
}
