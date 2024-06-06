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
import java.util.ArrayList;

/**
 * Controlador para manejar la lógica del algoritmo genético y la interacción con la vista.
 */
public class AlgoritmoGeneticoController {
    private AlgoritmoGeneticoView view;
    private AlgoritmoGenetico algoritmoGenetico;

    public AlgoritmoGeneticoController(AlgoritmoGeneticoView view) {
        this.view = view;
        this.view.setController(this);
    }

    public void iniciarAlgoritmo(int tamanoPoblacion, int ciclos, double porcentajeMutacion, FunctionType functionType, boolean maximizar, TextArea outputArea) {
        try {
            algoritmoGenetico = new AlgoritmoGenetico(tamanoPoblacion, ciclos, porcentajeMutacion, maximizar, functionType);
            algoritmoGenetico.ejecutar();

            mostrarResultados(outputArea);
            graficarResultados();
        } catch (NumberFormatException | NullPointerException e) {
            outputArea.appendText("Por favor ingrese valores válidos y seleccione una función.\n");
        }
    }

    private void mostrarResultados(TextArea outputArea) {
        outputArea.clear();
        outputArea.appendText("Ejecución completada.\n");
        outputArea.appendText("Mejor individuo final: " + algoritmoGenetico.getMejorIndividuo() + "\n");
    }

    private void graficarResultados() {
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
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame chartFrame = new JFrame("Evolución de la Mejor Aptitud");
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chartFrame.getContentPane().add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }
}
