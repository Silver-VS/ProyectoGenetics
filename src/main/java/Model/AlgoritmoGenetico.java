package Model;

import java.util.ArrayList;

/**
 * Clase principal que coordina la ejecución del algoritmo genético.
 */
public class AlgoritmoGenetico {
    private Poblacion poblacion;
    private int ciclos;
    private ArrayList<Double> historiaMejorAptitud;
    private boolean maximizar;

    /**
     * Constructor de la clase AlgoritmoGenetico.
     *
     * @param tamanoPoblacion   El tamaño de la población.
     * @param ciclos            El número de ciclos a ejecutar.
     * @param porcentajeMutacion El porcentaje de mutación.
     * @param maximizar         Indica si se debe maximizar o minimizar la función.
     * @param functionType      El tipo de función que se utilizará para evaluar a los individuos.
     */
    public AlgoritmoGenetico(int tamanoPoblacion, int ciclos, double porcentajeMutacion, boolean maximizar, FunctionType functionType) {
        this.poblacion = new Poblacion(tamanoPoblacion, porcentajeMutacion, functionType);
        this.ciclos = ciclos;
        this.historiaMejorAptitud = new ArrayList<>();
        this.maximizar = maximizar;
    }

    /**
     * Ejecuta los ciclos del algoritmo genético.
     */
    public void ejecutar() {
        for (int i = 0; i < ciclos; i++) {
            poblacion.ejecutarCiclo();
            historiaMejorAptitud.add(obtenerMejorAptitud());
        }
    }

    /**
     * Obtiene la mejor aptitud en la población actual.
     *
     * @return La mejor aptitud.
     */
    private double obtenerMejorAptitud() {
        return poblacion.getIndividuos().stream()
                .mapToDouble(Individuo::getAptitud)
                .reduce((a, b) -> maximizar ? Math.max(a, b) : Math.min(a, b))
                .getAsDouble();
    }

    /**
     * Obtiene la historia de la mejor aptitud en cada ciclo.
     *
     * @return Una lista con la mejor aptitud en cada ciclo.
     */
    public ArrayList<Double> getHistoriaMejorAptitud() {
        return historiaMejorAptitud;
    }

    /**
     * Obtiene el mejor individuo en la población actual.
     *
     * @return El mejor individuo.
     */
    public Individuo getMejorIndividuo() {
        return poblacion.getIndividuos().stream()
                .reduce((a, b) -> maximizar ? (a.getAptitud() > b.getAptitud() ? a : b) : (a.getAptitud() < b.getAptitud() ? a : b))
                .get();
    }
}
