package main.java.com.vyorg.reclutamiento.repository;

import main.java.com.vyorg.reclutamiento.config.DatabaseConnection;
import main.java.com.vyorg.reclutamiento.exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository {

    @FunctionalInterface
    protected interface MapeadorFila<T> {
        T mapear(ResultSet resultado) throws SQLException;
    }

    private final DatabaseConnection baseDatos;

    protected BaseRepository(DatabaseConnection baseDatos) {
        this.baseDatos = baseDatos;
    }

    protected <T> List<T> buscarTodos(String sql, MapeadorFila<T> mapeador, Object... parametros) {
        try (Connection conexion = baseDatos.obtenerConexion();
             PreparedStatement sentencia = prepararSentencia(conexion, sql, parametros);
             ResultSet resultado = sentencia.executeQuery()) {
            List<T> filas = new ArrayList<>();
            while (resultado.next()) {
                filas.add(mapeador.mapear(resultado));
            }
            return filas;
        } catch (SQLException e) {
            throw traducir(e);
        }
    }

    protected <T> Optional<T> buscarUno(String sql, MapeadorFila<T> mapeador, Object... parametros) {
        return buscarTodos(sql, mapeador, parametros).stream().findFirst();
    }

    protected boolean existe(String sql, Object... parametros) {
        return buscarUno(sql, fila -> Boolean.TRUE, parametros).isPresent();
    }

    protected int ejecutar(String sql, Object... parametros) {
        try (Connection conexion = baseDatos.obtenerConexion();
             PreparedStatement sentencia = prepararSentencia(conexion, sql, parametros)) {
            return sentencia.executeUpdate();
        } catch (SQLException e) {
            throw traducir(e);
        }
    }

    private PreparedStatement prepararSentencia(Connection conexion, String sql, Object[] parametros)
            throws SQLException {
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        for (int i = 0; i < parametros.length; i++) {
            sentencia.setObject(i + 1, parametros[i]);
        }
        return sentencia;
    }

    private PersistenciaException traducir(SQLException e) {
     
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return new PersistenciaException("Ya existe un registro con esos datos.", e);
        }
        return new PersistenciaException("No se pudo completar la operación en la base de datos.", e);
    }
}
