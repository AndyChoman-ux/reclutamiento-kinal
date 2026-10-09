package main.java.com.vyorg.reclutamiento.view;

import main.java.com.vyorg.reclutamiento.exception.AppException;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SceneManager {

    private final Stage stage;
    private Callback<Class<?>, Object> fabricaControladores;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void setFabricaControladores(Callback<Class<?>, Object> fabricaControladores) {
        this.fabricaControladores = fabricaControladores;
    }

    public void mostrar(Vista vista) {
        URL recurso = SceneManager.class.getResource(vista.getArchivo());
        if (recurso == null) {
            throw new AppException("No se encontró la vista " + vista.getArchivo() + ".");
        }
        try {
            FXMLLoader cargador = new FXMLLoader(recurso);
            cargador.setControllerFactory(fabricaControladores);
            Parent raiz = cargador.load();
            stage.setTitle(vista.getTitulo());
            stage.setScene(new Scene(raiz));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new AppException("No se pudo cargar la vista " + vista.getTitulo() + ".", e);
        }
    }
}
