package main.java.com.vyorg.reclutamiento.repository;

import main.java.com.vyorg.reclutamiento.config.DatabaseConnection;
import main.java.com.vyorg.reclutamiento.model.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends BaseRepository {

    public UsuarioRepository(DatabaseConnection baseDatos) {
        super(baseDatos);
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultado.getInt("id_usuario"));
        usuario.setNombre(resultado.getString("nombre"));
        usuario.setApellido(resultado.getString("apellido"));
        usuario.setCorreo(resultado.getString("correo"));
        usuario.setContrasenaHash(resultado.getString("contrasena"));
        
        
        int idRol = resultado.getInt("id_rol");
        String nombreRol = (idRol == 1) ? "ADMIN" : "USER";
        usuario.setRol(nombreRol);
        
        return usuario;
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        return buscarUno(sql, this::mapearUsuario, correo);
    }

    public Optional<Usuario> buscarPorId(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        return buscarUno(sql, this::mapearUsuario, idUsuario);
    }

    public List<Usuario> obtenerTodos() {
        String sql = "SELECT * FROM usuarios";
        return buscarTodos(sql, this::mapearUsuario);
    }

    public boolean guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena, id_rol) VALUES (?, ?, ?, ?, ?)";
        
        int idRol = "ADMIN".equalsIgnoreCase(usuario.getRol()) ? 1 : 2;

        return ejecutar(sql, 
                usuario.getNombre(), 
                usuario.getApellido(), 
                usuario.getCorreo(), 
                usuario.getContrasenaHash(), 
                idRol) > 0;
    }

    public boolean existeCorreo(String correo) {
        String sql = "SELECT 1 FROM usuarios WHERE correo = ?";
        return existe(sql, correo);
    }
}