package View;

import Controller.AlgoritmoGeneticoController;
import Model.FunctionType;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Clase que representa la vista de la aplicación del algoritmo genético.
 */
public class AlgoritmoGeneticoView extends Application {
    private AlgoritmoGeneticoController controller;
    private TextField tamanoPoblacionField;
    private TextField ciclosField;
    private TextField porcentajeMutacionField;
    private ComboBox<FunctionType> functionTypeComboBox;
    private CheckBox maximizarCheckBox;
    private TextArea outputArea;

    public void setController(AlgoritmoGeneticoController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setSpacing(15);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        Label titulo = new Label("Algoritmo Genético - Configuración");
        titulo.getStyleClass().add("titulo");

        tamanoPoblacionField = new TextField();
        tamanoPoblacionField.setPromptText("Tamaño de la población");
        tamanoPoblacionField.getStyleClass().add("input");

        ciclosField = new TextField();
        ciclosField.setPromptText("Número de generaciones");
        ciclosField.getStyleClass().add("input");

        porcentajeMutacionField = new TextField();
        porcentajeMutacionField.setPromptText("Porcentaje de mutación (0-1)");
        porcentajeMutacionField.getStyleClass().add("input");

        functionTypeComboBox = new ComboBox<>();
        functionTypeComboBox.getItems().addAll(FunctionType.values());
        functionTypeComboBox.setPromptText("Selecciona la función");
        functionTypeComboBox.getStyleClass().add("combo-box");

        maximizarCheckBox = new CheckBox("Maximizar función");
        maximizarCheckBox.getStyleClass().add("check-box");

        Button iniciarButton = new Button("Iniciar");
        iniciarButton.getStyleClass().add("button");
        iniciarButton.setOnAction(e -> {
            try {
                int tamanoPoblacion = Integer.parseInt(tamanoPoblacionField.getText());
                int ciclos = Integer.parseInt(ciclosField.getText());
                double porcentajeMutacion = Double.parseDouble(porcentajeMutacionField.getText());
                FunctionType functionType = functionTypeComboBox.getValue();
                boolean maximizar = maximizarCheckBox.isSelected();

                controller.iniciarAlgoritmo(tamanoPoblacion, ciclos, porcentajeMutacion, functionType, maximizar, outputArea);
            } catch (NumberFormatException | NullPointerException ex) {
                outputArea.appendText("Por favor ingrese valores válidos y seleccione una función.\n");
            }
        });

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.getStyleClass().add("output-area");

        root.getChildren().addAll(titulo, tamanoPoblacionField, ciclosField, porcentajeMutacionField, functionTypeComboBox, maximizarCheckBox, iniciarButton, outputArea);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

        primaryStage.setTitle("Algoritmo Genético");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
