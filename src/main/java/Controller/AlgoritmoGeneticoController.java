package Controller;

import Model.AlgoritmoGenetico;
import Model.FunctionType;
import View.AlgoritmoGeneticoView;
import javafx.scene.control.TextArea;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Controlador para manejar la lógica del algoritmo genético y la interacción con la vista.
 */
public class AlgoritmoGeneticoController {
    private AlgoritmoGenetico algoritmoGenetico;

    public AlgoritmoGeneticoController(AlgoritmoGeneticoView view) {
        view.setController(this);
    }

    public void iniciarAlgoritmo(int tamanoPoblacion, int ciclos, double porcentajeMutacion, FunctionType functionType, boolean maximizar, double minValue, double maxValue, TextArea outputArea) {
        try {
            algoritmoGenetico = new AlgoritmoGenetico(tamanoPoblacion, ciclos, porcentajeMutacion, maximizar, functionType, minValue, maxValue);
            algoritmoGenetico.ejecutar();

            mostrarResultados(outputArea);
            graficarResultados(tamanoPoblacion, ciclos, porcentajeMutacion, functionType, maximizar, minValue, maxValue);
        } catch (NumberFormatException | NullPointerException e) {
            outputArea.appendText("Por favor ingrese valores válidos y seleccione una función.\n");
        }
    }

    private void mostrarResultados(TextArea outputArea) {
        outputArea.clear();
        outputArea.appendText("Ejecución completada.\n");
        outputArea.appendText("Mejor individuo final: " + algoritmoGenetico.getMejorIndividuo() + "\n");
    }

    private void graficarResultados(int tamanoPoblacion, int ciclos, double porcentajeMutacion, FunctionType functionType, boolean maximizar, double minValue, double maxValue) {
        ArrayList<Double> historiaMejorAptitud = algoritmoGenetico.getHistoriaMejorAptitud();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < historiaMejorAptitud.size(); i++) {
            dataset.addValue(historiaMejorAptitud.get(i), "Mejor Aptitud", "Generación " + (i + 1));
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Evolución de la Mejor Aptitud",
                "Generación",
                "Mejor Aptitud",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Crear ventana para la gráfica y detalles
        JFrame chartFrame = new JFrame("Resultados del Algoritmo Genético");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.getContentPane().setLayout(new BorderLayout());
        chartFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        // Añadir detalles a la ventana
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setText("Detalles del Algoritmo:\n");
        detailsArea.append("Tamaño de la población: " + tamanoPoblacion + "\n");
        detailsArea.append("Número de generaciones: " + ciclos + "\n");
        detailsArea.append("Porcentaje de mutación: " + porcentajeMutacion + "\n");
        detailsArea.append("Función: " + functionType + "\n");
        detailsArea.append("Maximizar: " + maximizar + "\n");
        detailsArea.append("Rango: [" + minValue + ", " + maxValue + "]\n");
        detailsArea.append("Mejor individuo final: " + algoritmoGenetico.getMejorIndividuo() + "\n");

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        chartFrame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

        chartFrame.pack();
        chartFrame.setVisible(true);
    }
}
