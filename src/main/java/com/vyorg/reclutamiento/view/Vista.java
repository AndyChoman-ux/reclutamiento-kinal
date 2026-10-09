package main.java.com.vyorg.reclutamiento.view;

public enum Vista {
    LOGIN("/main/resources/view/login.fxml", "Reclutamiento - Iniciar sesión"),
    REGISTRO("/main/resources/view/registro.fxml", "Reclutamiento - Registro de usuario"),
    INVENTARIO("/main/resources/view/inventario.fxml", "Reclutamiento - Inventario de aspirantes");

    private final String archivo;
    private final String titulo;

    Vista(String archivo, String titulo) {
        this.archivo = archivo;
        this.titulo = titulo;
    }

    public String getArchivo() {
        return archivo;
    }

    public String getTitulo() {
        return titulo;
    }
}
