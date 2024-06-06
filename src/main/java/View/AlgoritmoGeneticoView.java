package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controller.AlgoritmoGeneticoController;
import Model.FunctionType;

import java.util.Objects;

/**
 * Clase que representa la vista de la aplicación del algoritmo genético.
 */
public class AlgoritmoGeneticoView extends Application {
    private AlgoritmoGeneticoController controller;
    private TextField tamanoPoblacionField;
    private TextField ciclosField;
    private TextField porcentajeMutacionField;
    private TextField minValueField;
    private TextField maxValueField;
    private ComboBox<FunctionType> functionTypeComboBox;
    private ComboBox<String> maxMinComboBox;
    private TextArea outputArea;

    public void setController(AlgoritmoGeneticoController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        // Inicializar el controlador
        controller = new AlgoritmoGeneticoController(this);

        VBox root = new VBox();
        root.setSpacing(15);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        Label titulo = new Label("Algoritmo Genético - Configuración");
        titulo.getStyleClass().add("titulo");

        Label tamanoPoblacionLabel = new Label("Tamaño de la población:");
        tamanoPoblacionField = new TextField();
        tamanoPoblacionField.setPromptText("Tamaño de la población");
        tamanoPoblacionField.getStyleClass().add("input");

        Label ciclosLabel = new Label("Número de generaciones:");
        ciclosField = new TextField();
        ciclosField.setPromptText("Número de generaciones");
        ciclosField.getStyleClass().add("input");

        Label porcentajeMutacionLabel = new Label("Porcentaje de mutación (0-1):");
        porcentajeMutacionField = new TextField();
        porcentajeMutacionField.setPromptText("Porcentaje de mutación (0-1)");
        porcentajeMutacionField.getStyleClass().add("input");

        Label minValueLabel = new Label("Valor mínimo del rango:");
        minValueField = new TextField();
        minValueField.setPromptText("Valor mínimo del rango");
        minValueField.getStyleClass().add("input");

        Label maxValueLabel = new Label("Valor máximo del rango:");
        maxValueField = new TextField();
        maxValueField.setPromptText("Valor máximo del rango");
        maxValueField.getStyleClass().add("input");

        Label functionTypeLabel = new Label("Selecciona la función:");
        functionTypeComboBox = new ComboBox<>();
        functionTypeComboBox.getItems().addAll(FunctionType.values());
        functionTypeComboBox.setPromptText("Selecciona la función");
        functionTypeComboBox.getStyleClass().add("combo-box");

        // Añadir etiquetas con las funciones matemáticas
        Label absFunctionLabel = new Label("ABS: f(x) = |(x - 5) / 2 + sin(x)|");
        absFunctionLabel.getStyleClass().add("function-label");
        Label squareFunctionLabel = new Label("SQUARE: f(x) = x²");
        squareFunctionLabel.getStyleClass().add("function-label");
        Label sinCosFunctionLabel = new Label("SIN_COS: f(x) = sin(x) + cos(x)");
        sinCosFunctionLabel.getStyleClass().add("function-label");

        Label maxMinLabel = new Label("Selecciona Maximizar o Minimizar:");
        maxMinComboBox = new ComboBox<>();
        maxMinComboBox.getItems().addAll("Maximizar", "Minimizar");
        maxMinComboBox.setPromptText("Selecciona Maximizar o Minimizar");
        maxMinComboBox.getStyleClass().add("combo-box");

        Button iniciarButton = new Button("Iniciar");
        iniciarButton.getStyleClass().add("button");
        iniciarButton.setOnAction(_ -> validarCampos());

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.getStyleClass().add("output-area");

        root.getChildren().addAll(
                titulo,
                tamanoPoblacionLabel, tamanoPoblacionField,
                ciclosLabel, ciclosField,
                porcentajeMutacionLabel, porcentajeMutacionField,
                minValueLabel, minValueField,
                maxValueLabel, maxValueField,
                functionTypeLabel, functionTypeComboBox,
                absFunctionLabel, squareFunctionLabel, sinCosFunctionLabel,
                maxMinLabel, maxMinComboBox,
                iniciarButton, outputArea
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 650);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/View/styles.css")).toExternalForm());

        primaryStage.setTitle("Algoritmo Genético");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void validarCampos() {
        outputArea.clear();
        boolean isValid = true;

        // Validar tamaño de la población
        int tamanoPoblacion = 0;
        try {
            tamanoPoblacion = Integer.parseInt(tamanoPoblacionField.getText());
            if (tamanoPoblacion <= 0) {
                outputArea.appendText("El tamaño de la población debe ser mayor a 0.\n");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            outputArea.appendText("Por favor ingrese un valor numérico válido para el tamaño de la población.\n");
            isValid = false;
        }

        // Validar ciclos
        int ciclos = 0;
        try {
            ciclos = Integer.parseInt(ciclosField.getText());
            if (ciclos <= 0) {
                outputArea.appendText("El número de generaciones debe ser mayor a 0.\n");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            outputArea.appendText("Por favor ingrese un valor numérico válido para el número de generaciones.\n");
            isValid = false;
        }

        // Validar porcentaje de mutación
        double porcentajeMutacion = 0;
        try {
            porcentajeMutacion = Double.parseDouble(porcentajeMutacionField.getText());
            if (porcentajeMutacion < 0 || porcentajeMutacion > 1) {
                outputArea.appendText("El porcentaje de mutación debe estar entre 0 y 1.\n");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            outputArea.appendText("Por favor ingrese un valor numérico válido para el porcentaje de mutación.\n");
            isValid = false;
        }

        // Validar valor mínimo del rango
        double minValue = 0;
        try {
            minValue = Double.parseDouble(minValueField.getText());
        } catch (NumberFormatException e) {
            outputArea.appendText("Por favor ingrese un valor numérico válido para el valor mínimo del rango.\n");
            isValid = false;
        }

        // Validar valor máximo del rango
        double maxValue = 0;
        try {
            maxValue = Double.parseDouble(maxValueField.getText());
            if (maxValue <= minValue) {
                outputArea.appendText("El valor máximo del rango debe ser mayor que el valor mínimo.\n");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            outputArea.appendText("Por favor ingrese un valor numérico válido para el valor máximo del rango.\n");
            isValid = false;
        }

        // Validar selección de función
        FunctionType functionType = functionTypeComboBox.getValue();
        if (functionType == null) {
            outputArea.appendText("Por favor seleccione una función.\n");
            isValid = false;
        }

        // Validar selección de maximizar/minimizar
        String maxMin = maxMinComboBox.getValue();
        if (maxMin == null) {
            outputArea.appendText("Por favor seleccione si desea maximizar o minimizar.\n");
            isValid = false;
        }

        if (isValid) {
            boolean maximizar = maxMin.equals("Maximizar");
            controller.iniciarAlgoritmo(tamanoPoblacion, ciclos, porcentajeMutacion, functionType, maximizar, minValue, maxValue, outputArea);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
