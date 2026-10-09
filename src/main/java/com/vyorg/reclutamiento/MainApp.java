package main.java.com.vyorg.reclutamiento;

import main.java.com.vyorg.reclutamiento.config.DatabaseConnection;
import main.java.com.vyorg.reclutamiento.controller.InventarioController;
import main.java.com.vyorg.reclutamiento.controller.LoginController;
import main.java.com.vyorg.reclutamiento.controller.RegistroController;
import main.java.com.vyorg.reclutamiento.exception.AppException;
import main.java.com.vyorg.reclutamiento.repository.AspiranteRepository;
import main.java.com.vyorg.reclutamiento.repository.UsuarioRepository;
import main.java.com.vyorg.reclutamiento.security.PasswordHasher;
import main.java.com.vyorg.reclutamiento.service.AspiranteService;
import main.java.com.vyorg.reclutamiento.service.AuthService;
import main.java.com.vyorg.reclutamiento.view.Alertas;
import main.java.com.vyorg.reclutamiento.view.SceneManager;
import main.java.com.vyorg.reclutamiento.view.Vista;
import java.util.Map;
import java.util.function.Supplier;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DatabaseConnection baseDatos = new DatabaseConnection();
        AuthService authService = new AuthService(new UsuarioRepository(baseDatos), new PasswordHasher());
        AspiranteService aspiranteService = new AspiranteService(new AspiranteRepository(baseDatos));
        SceneManager sceneManager = new SceneManager(stage);

        Map<Class<?>, Supplier<Object>> controladores = Map.of(
                LoginController.class, () -> new LoginController(authService, sceneManager),
                RegistroController.class, () -> new RegistroController(authService, sceneManager),
                InventarioController.class, () -> new InventarioController(aspiranteService, sceneManager));
        sceneManager.setFabricaControladores(tipo -> controladores.get(tipo).get());

        try {
            sceneManager.mostrar(Vista.LOGIN);
        } catch (AppException e) {
            Alertas.error(e.getMessage());
        }
    }
}
