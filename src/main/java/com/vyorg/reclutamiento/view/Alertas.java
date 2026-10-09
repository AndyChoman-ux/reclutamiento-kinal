package main.java.com.vyorg.reclutamiento.view;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public final class Alertas {

    private Alertas() { }

    public static void informacion(String mensaje) {
        mostrar(AlertType.INFORMATION, "Información", mensaje);
    }

    public static void error(String mensaje) {
        mostrar(AlertType.ERROR, "Error", mensaje);
    }

    public static boolean confirmar(String mensaje) {
        Alert alerta = crear(AlertType.CONFIRMATION, "Confirmación", mensaje);
        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
    }

    private static void mostrar(AlertType tipo, String titulo, String mensaje) {
        crear(tipo, titulo, mensaje).showAndWait();
    }

    private static Alert crear(AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta;
    }
}
