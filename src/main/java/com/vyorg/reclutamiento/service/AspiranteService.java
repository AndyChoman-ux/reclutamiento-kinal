package main.java.com.vyorg.reclutamiento.service;

import main.java.com.vyorg.reclutamiento.dto.AspiranteDto;
import main.java.com.vyorg.reclutamiento.exception.ValidationException;
import main.java.com.vyorg.reclutamiento.mapper.AspiranteMapper;
import main.java.com.vyorg.reclutamiento.model.Aspirante;
import main.java.com.vyorg.reclutamiento.repository.AspiranteRepository;
import main.java.com.vyorg.reclutamiento.validation.AspiranteValidator;
import java.util.List;

public class AspiranteService {

    private static final String MENSAJE_NO_EXISTE = "El aspirante ya no existe en la base de datos.";
    private static final String MENSAJE_CORREO_DUPLICADO = "Ya existe un aspirante con ese correo.";

    private final AspiranteRepository aspiranteRepository;

    public AspiranteService(AspiranteRepository aspiranteRepository) {
        this.aspiranteRepository = aspiranteRepository;
    }

    public List<AspiranteDto> listar() {
        return aspiranteRepository.listar().stream().map(AspiranteMapper::aDto).toList();
    }

    public void agregar(AspiranteDto dto) {
        aspiranteRepository.guardar(validarYConvertir(dto));
    }

    public void actualizar(AspiranteDto dto) {
        if (!aspiranteRepository.actualizar(validarYConvertir(dto))) {
            throw new ValidationException(MENSAJE_NO_EXISTE);
        }
    }

    public void eliminar(int id) {
        if (!aspiranteRepository.eliminar(id)) {
            throw new ValidationException(MENSAJE_NO_EXISTE);
        }
    }

    private Aspirante validarYConvertir(AspiranteDto dto) {
        Aspirante aspirante = AspiranteMapper.aEntidad(AspiranteValidator.validar(dto));
        if (aspiranteRepository.existeCorreo(aspirante.getCorreo(), aspirante.getId())) {
            throw new ValidationException(MENSAJE_CORREO_DUPLICADO);
        }
        return aspirante;
    }
}
