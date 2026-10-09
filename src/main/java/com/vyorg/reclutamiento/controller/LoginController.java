package main.java.com.vyorg.reclutamiento.controller;

import main.java.com.vyorg.reclutamiento.dto.CredencialesDto;
import main.java.com.vyorg.reclutamiento.dto.UsuarioDto;
import main.java.com.vyorg.reclutamiento.exception.AppException;
import main.java.com.vyorg.reclutamiento.service.AuthService;
import main.java.com.vyorg.reclutamiento.view.Alertas;
import main.java.com.vyorg.reclutamiento.view.SceneManager;
import main.java.com.vyorg.reclutamiento.view.Vista;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML private TextField campoCorreo;
    @FXML private PasswordField campoPassword;

    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void iniciarSesion() {
        try {
            UsuarioDto usuario = authService.iniciarSesion(
                    new CredencialesDto(campoCorreo.getText(), campoPassword.getText()));
            Alertas.informacion("Bienvenido, " + usuario.nombre() + ". Rol: " + usuario.rol());
            sceneManager.mostrar(Vista.INVENTARIO);
        } catch (AppException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void irARegistro() {
        sceneManager.mostrar(Vista.REGISTRO);
    }
}