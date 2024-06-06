package Model;

/**
 * Clase que representa un individuo en la población del algoritmo genético.
 */
public class Individuo {
    private String cromosoma;
    private double aptitud;
    private FunctionType functionType;

    /**
     * Constructor de la clase Individuo.
     *
     * @param cromosoma   Cadena de bits que representa el cromosoma del individuo.
     * @param functionType Tipo de función que se utilizará para calcular la aptitud del individuo.
     */
    public Individuo(String cromosoma, FunctionType functionType) {
        this.cromosoma = cromosoma;
        this.functionType = functionType;
        this.aptitud = calcularAptitud();
    }

    /**
     * Calcula la aptitud del individuo utilizando la función de evaluación especificada.
     *
     * @return La aptitud del individuo.
     */
    private double calcularAptitud() {
        int x = Integer.parseInt(cromosoma, 2);
        return evaluate(functionType, x);
    }

    /**
     * Evalúa una función para un valor dado de x.
     *
     * @param type Tipo de función a evaluar.
     * @param x    Valor de entrada para la función.
     * @return El resultado de la función evaluada.
     */
    public static double evaluate(FunctionType type, int x) {
        return switch (type) {
            case ABS -> Math.abs((x - 5) / 2.0 + Math.sin(x));
            case SQUARE -> Math.pow(x, 2);
            case SIN_COS -> Math.sin(x) + Math.cos(x);
            default -> throw new IllegalArgumentException("Unknown function type");
        };
    }

    /**
     * Obtiene el cromosoma del individuo.
     *
     * @return El cromosoma del individuo.
     */
    public String getCromosoma() {
        return cromosoma;
    }

    /**
     * Obtiene la aptitud del individuo.
     *
     * @return La aptitud del individuo.
     */
    public double getAptitud() {
        return aptitud;
    }

    /**
     * Establece el cromosoma del individuo y recalcula su aptitud.
     *
     * @param cromosoma El nuevo cromosoma del individuo.
     */
    public void setCromosoma(String cromosoma) {
        this.cromosoma = cromosoma;
        this.aptitud = calcularAptitud();
    }

    @Override
    public String toString() {
        return "Cromosoma: " + cromosoma + " Aptitud: " + aptitud;
    }
}
