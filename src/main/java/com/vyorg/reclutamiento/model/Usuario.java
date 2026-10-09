package main.java.com.vyorg.reclutamiento.model;

import java.util.Objects;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenaHash;
    private String rol;

    public Usuario() { }

    public Usuario(int id, String nombre, String apellido, String correo,
                   String contrasenaHash, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        Usuario otro = (Usuario) objeto;
        return Objects.equals(correo, otro.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correo);
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre=" + getNombreCompleto()
                + ", correo=" + correo + ", rol=" + rol + "}";
    }
}
