package main.java.com.vyorg.reclutamiento.repository;

import main.java.com.vyorg.reclutamiento.config.DatabaseConnection;
import main.java.com.vyorg.reclutamiento.model.Aspirante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AspiranteRepository extends BaseRepository {

    private static final String SQL_LISTAR =
            "select id_aspirante, nombre, apellido, correo, telefono, direccion, edad "
            + "from aspirantes order by id_aspirante";
    private static final String SQL_GUARDAR =
            "insert into aspirantes (nombre, apellido, correo, telefono, direccion, edad) "
            + "values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_ACTUALIZAR =
            "update aspirantes set nombre = ?, apellido = ?, correo = ?, telefono = ?, "
            + "direccion = ?, edad = ? where id_aspirante = ?";
    private static final String SQL_ELIMINAR = "delete from aspirantes where id_aspirante = ?";
    private static final String SQL_EXISTE_CORREO =
            "select 1 from aspirantes where correo = ? and id_aspirante <> ? limit 1";

    public AspiranteRepository(DatabaseConnection baseDatos) {
        super(baseDatos);
    }

    public List<Aspirante> listar() {
        return buscarTodos(SQL_LISTAR, this::mapear);
    }

    public void guardar(Aspirante aspirante) {
        ejecutar(SQL_GUARDAR, aspirante.getNombre(), aspirante.getApellido(), aspirante.getCorreo(),
                aspirante.getTelefono(), aspirante.getDireccion(), aspirante.getEdad());
    }

    public boolean actualizar(Aspirante aspirante) {
        return ejecutar(SQL_ACTUALIZAR, aspirante.getNombre(), aspirante.getApellido(),
                aspirante.getCorreo(), aspirante.getTelefono(), aspirante.getDireccion(),
                aspirante.getEdad(), aspirante.getId()) > 0;
    }

    public boolean eliminar(int id) {
        return ejecutar(SQL_ELIMINAR, id) > 0;
    }

    public boolean existeCorreo(String correo, int idExcluido) {
        return existe(SQL_EXISTE_CORREO, correo, idExcluido);
    }

    private Aspirante mapear(ResultSet fila) throws SQLException {
        return new Aspirante(fila.getInt("id_aspirante"), fila.getString("nombre"),
                fila.getString("apellido"), fila.getString("correo"), fila.getString("telefono"),
                fila.getString("direccion"), fila.getInt("edad"));
    }
}