package main.java.com.vyorg.reclutamiento.controller;

import main.java.com.vyorg.reclutamiento.dto.AspiranteDto;
import main.java.com.vyorg.reclutamiento.exception.AppException;
import main.java.com.vyorg.reclutamiento.exception.ValidationException;
import main.java.com.vyorg.reclutamiento.service.AspiranteService;
import main.java.com.vyorg.reclutamiento.view.Alertas;
import main.java.com.vyorg.reclutamiento.view.SceneManager;
import main.java.com.vyorg.reclutamiento.view.Vista;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InventarioController implements Initializable {

    private final AspiranteService aspiranteService;
    private final SceneManager sceneManager;
    private final ObservableList<AspiranteDto> aspirantes = FXCollections.observableArrayList();

    @FXML private Label etiquetaRol;

    @FXML private TableView<AspiranteDto> tablaAspirantes;
    @FXML private TableColumn<AspiranteDto, Number> columnaId;
    @FXML private TableColumn<AspiranteDto, String> columnaNombre;
    @FXML private TableColumn<AspiranteDto, String> columnaApellido;
    @FXML private TableColumn<AspiranteDto, String> columnaCorreo;
    @FXML private TableColumn<AspiranteDto, String> columnaTelefono;
    @FXML private TableColumn<AspiranteDto, String> columnaDireccion;
    @FXML private TableColumn<AspiranteDto, Number> columnaEdad;

    @FXML private TextField campoNombre;
    @FXML private TextField campoApellido;
    @FXML private TextField campoCorreo;
    @FXML private TextField campoTelefono;
    @FXML private TextField campoDireccion;
    @FXML private TextField campoEdad;

    public InventarioController(AspiranteService aspiranteService, SceneManager sceneManager) {
        this.aspiranteService = aspiranteService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL ubicacion, ResourceBundle recursos) {
        configurarColumnas();
        tablaAspirantes.setItems(aspirantes);
        tablaAspirantes.getSelectionModel().selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        cargarFormulario(seleccionado);
                    }
                });
        ejecutar(this::recargarTabla, null);
    }

    public void establecerRol(String nombreRol) {
        if (etiquetaRol != null && nombreRol != null) {
            etiquetaRol.setText("Rol: " + nombreRol);
        }
    }

    @FXML
    private void agregar() {
        ejecutar(() -> aspiranteService.agregar(leerFormulario(0)), "Aspirante agregado correctamente.");
    }

    @FXML
    private void actualizar() {
        ejecutar(() -> aspiranteService.actualizar(leerFormulario(obtenerIdSeleccionado())),
                "Aspirante actualizado correctamente.");
    }

    @FXML
    private void eliminar() {
        ejecutar(() -> {
            int id = obtenerIdSeleccionado();
            if (Alertas.confirmar("¿Desea eliminar al aspirante seleccionado?")) {
                aspiranteService.eliminar(id);
            }
        }, null);
    }

    @FXML
    private void limpiar() {
        tablaAspirantes.getSelectionModel().clearSelection();
        campoNombre.clear();
        campoApellido.clear();
        campoCorreo.clear();
        campoTelefono.clear();
        campoDireccion.clear();
        campoEdad.clear();
    }

    @FXML
    private void cerrarSesion() {
        sceneManager.mostrar(Vista.LOGIN);
    }

    private void ejecutar(Runnable accion, String mensajeExito) {
        try {
            accion.run();
            recargarTabla();
            if (mensajeExito != null) {
                limpiar();
                Alertas.informacion(mensajeExito);
            }
        } catch (AppException e) {
            Alertas.error(e.getMessage());
        }
    }

    private void recargarTabla() {
        aspirantes.setAll(aspiranteService.listar());
    }

    private int obtenerIdSeleccionado() {
        AspiranteDto seleccionado = tablaAspirantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            throw new ValidationException("Seleccione un aspirante de la tabla.");
        }
        return seleccionado.id();
    }

    private AspiranteDto leerFormulario(int id) {
        return new AspiranteDto(id, campoNombre.getText(), campoApellido.getText(),
                campoCorreo.getText(), campoTelefono.getText(), campoDireccion.getText(), leerEdad());
    }

    private int leerEdad() {
        try {
            return Integer.parseInt(campoEdad.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("La edad debe ser un número entero.");
        }
    }

    private void cargarFormulario(AspiranteDto aspirante) {
        campoNombre.setText(aspirante.nombre());
        campoApellido.setText(aspirante.apellido());
        campoCorreo.setText(aspirante.correo());
        campoTelefono.setText(aspirante.telefono());
        campoDireccion.setText(aspirante.direccion());
        campoEdad.setText(String.valueOf(aspirante.edad()));
    }

    private void configurarColumnas() {
        columnaId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().id()));
        columnaNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().nombre()));
        columnaApellido.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().apellido()));
        columnaCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().correo()));
        columnaTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().telefono()));
        columnaDireccion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().direccion()));
        columnaEdad.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().edad()));
    }
}