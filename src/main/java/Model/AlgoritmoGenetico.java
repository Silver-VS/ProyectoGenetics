package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa el algoritmo genético.
 */
public class AlgoritmoGenetico {
    private final int tamanoPoblacion;
    private final int ciclos;
    private final double porcentajeMutacion;
    private final boolean maximizar;
    private final FunctionType functionType;
    private final double minValue;
    private final double maxValue;
    private final ArrayList<Double> historiaMejorAptitud;
    private Individuo mejorIndividuo;
    private final ArrayList<Individuo> poblacion;

    public AlgoritmoGenetico(int tamanoPoblacion, int ciclos, double porcentajeMutacion, boolean maximizar, FunctionType functionType, double minValue, double maxValue) {
        this.tamanoPoblacion = tamanoPoblacion;
        this.ciclos = ciclos;
        this.porcentajeMutacion = porcentajeMutacion;
        this.maximizar = maximizar;
        this.functionType = functionType;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.historiaMejorAptitud = new ArrayList<>();
        this.poblacion = new ArrayList<>();
        this.mejorIndividuo = null;
    }

    public void ejecutar() {
        inicializarPoblacion();
        for (int ciclo = 0; ciclo < ciclos; ciclo++) {
            evaluarPoblacion();
            seleccionarPadres();
            generarNuevaPoblacion();
            aplicarMutacion();
            registrarMejorIndividuo();
        }
    }

    private void inicializarPoblacion() {
        Random random = new Random();
        for (int i = 0; i < tamanoPoblacion; i++) {
            double valor = minValue + (maxValue - minValue) * random.nextDouble();
            Individuo individuo = new Individuo(valor);
            poblacion.add(individuo);
        }
    }

    private void evaluarPoblacion() {
        for (Individuo individuo : poblacion) {
            double aptitud = evaluarFuncion(individuo.getValor());
            individuo.setAptitud(aptitud);
        }
    }

    private double evaluarFuncion(double valor) {
        return switch (functionType) {
            case ABS -> Math.abs((valor - 5) / 2.0 + Math.sin(valor));
            case SQUARE -> Math.pow(valor, 2);
            case SIN_COS -> Math.sin(valor) + Math.cos(valor);
        };
    }

    private void seleccionarPadres() {
        // Implementar selección de padres aquí
    }

    private void generarNuevaPoblacion() {
        // Implementar generación de nueva población aquí
    }

    private void aplicarMutacion() {
        Random random = new Random();
        for (Individuo individuo : poblacion) {
            if (random.nextDouble() < porcentajeMutacion) {
                double nuevoValor = minValue + (maxValue - minValue) * random.nextDouble();
                individuo.setValor(nuevoValor);
                double aptitud = evaluarFuncion(nuevoValor);
                individuo.setAptitud(aptitud);
            }
        }
    }

    private void registrarMejorIndividuo() {
        Individuo mejorIndividuoCiclo = null;
        for (Individuo individuo : poblacion) {
            if (mejorIndividuoCiclo == null || (maximizar ? individuo.getAptitud() > mejorIndividuoCiclo.getAptitud() : individuo.getAptitud() < mejorIndividuoCiclo.getAptitud())) {
                mejorIndividuoCiclo = individuo;
            }
        }
        if (mejorIndividuoCiclo != null) {
            historiaMejorAptitud.add(mejorIndividuoCiclo.getAptitud());
            if (mejorIndividuo == null || (maximizar ? mejorIndividuoCiclo.getAptitud() > mejorIndividuo.getAptitud() : mejorIndividuoCiclo.getAptitud() < mejorIndividuo.getAptitud())) {
                mejorIndividuo = mejorIndividuoCiclo;
            }
        }
    }

    public Individuo getMejorIndividuo() {
        return mejorIndividuo;
    }

    public ArrayList<Double> getHistoriaMejorAptitud() {
        return historiaMejorAptitud;
    }

    public List<Individuo> getPoblacion() {
        return poblacion;
    }
}
