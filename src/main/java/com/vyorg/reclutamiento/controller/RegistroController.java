package main.java.com.vyorg.reclutamiento.controller;

import main.java.com.vyorg.reclutamiento.dto.RegistroUsuarioDto;
import main.java.com.vyorg.reclutamiento.exception.AppException;
import main.java.com.vyorg.reclutamiento.service.AuthService;
import main.java.com.vyorg.reclutamiento.view.Alertas;
import main.java.com.vyorg.reclutamiento.view.SceneManager;
import main.java.com.vyorg.reclutamiento.view.Vista;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {

    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML private TextField campoNombre;
    @FXML private TextField campoApellido;
    @FXML private TextField campoCorreo;
    @FXML private PasswordField campoPassword;

    public RegistroController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void registrar() {
        try {
            authService.registrar(new RegistroUsuarioDto(campoNombre.getText(),
                    campoApellido.getText(), campoCorreo.getText(), campoPassword.getText()));
            Alertas.informacion("Usuario registrado correctamente. Ya puede iniciar sesión.");
            sceneManager.mostrar(Vista.LOGIN);
        } catch (AppException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void volver() {
        sceneManager.mostrar(Vista.LOGIN);
    }
}
