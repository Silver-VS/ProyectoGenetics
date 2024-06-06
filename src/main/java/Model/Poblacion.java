package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Clase que representa una población de individuos en el algoritmo genético.
 */
public class Poblacion {
    private ArrayList<Individuo> individuos;
    private int tamanoPoblacion;
    private Random random;
    private double porcentajeMutacion;
    private FunctionType functionType;

    /**
     * Constructor de la clase Poblacion.
     *
     * @param tamanoPoblacion   El tamaño de la población.
     * @param porcentajeMutacion El porcentaje de mutación.
     * @param functionType      El tipo de función que se utilizará para evaluar a los individuos.
     */
    public Poblacion(int tamanoPoblacion, double porcentajeMutacion, FunctionType functionType) {
        this.tamanoPoblacion = tamanoPoblacion;
        this.porcentajeMutacion = porcentajeMutacion;
        this.functionType = functionType;
        this.individuos = new ArrayList<>();
        this.random = new Random();
        inicializarPoblacion();
    }

    /**
     * Inicializa la población con individuos aleatorios.
     */
    private void inicializarPoblacion() {
        for (int i = 0; i < tamanoPoblacion; i++) {
            String cromosoma = generarCromosomaAleatorio();
            individuos.add(new Individuo(cromosoma, functionType));
        }
    }

    /**
     * Genera una cadena de bits aleatoria.
     *
     * @return Una cadena de bits.
     */
    private String generarCromosomaAleatorio() {
        StringBuilder cromosoma = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            cromosoma.append(random.nextInt(2));
        }
        return cromosoma.toString();
    }

    /**
     * Selecciona un padre utilizando selección estocástica con reemplazo.
     *
     * @return Un individuo seleccionado como padre.
     */
    public Individuo seleccionarPadre() {
        double sumaAptitudes = individuos.stream().mapToDouble(Individuo::getAptitud).sum();
        double valorSeleccion = random.nextDouble() * sumaAptitudes;
        double acumulado = 0;
        for (Individuo ind : individuos) {
            acumulado += ind.getAptitud();
            if (acumulado >= valorSeleccion) {
                return ind;
            }
        }
        return individuos.get(random.nextInt(tamanoPoblacion));
    }

    /**
     * Realiza la cruza acentuada entre dos padres para generar dos descendientes.
     *
     * @param padre1 El primer padre.
     * @param padre2 El segundo padre.
     * @return Un arreglo con dos individuos descendientes.
     */
    public Individuo[] cruza(Individuo padre1, Individuo padre2) {
        String cromosoma1 = padre1.getCromosoma();
        String cromosoma2 = padre2.getCromosoma();
        int numPuntosCruza = random.nextInt(3) + 1;
        int[] puntosCruza = new int[numPuntosCruza];

        for (int i = 0; i < numPuntosCruza; i++) {
            puntosCruza[i] = random.nextInt(cromosoma1.length());
        }
        Arrays.sort(puntosCruza);

        StringBuilder hijo1Cromosoma = new StringBuilder();
        StringBuilder hijo2Cromosoma = new StringBuilder();

        boolean alternar = true;
        int inicio = 0;
        for (int puntoCruza : puntosCruza) {
            if (alternar) {
                hijo1Cromosoma.append(cromosoma1, inicio, puntoCruza);
                hijo2Cromosoma.append(cromosoma2, inicio, puntoCruza);
            } else {
                hijo1Cromosoma.append(cromosoma2, inicio, puntoCruza);
                hijo2Cromosoma.append(cromosoma1, inicio, puntoCruza);
            }
            alternar = !alternar;
            inicio = puntoCruza;
        }

        if (alternar) {
            hijo1Cromosoma.append(cromosoma1.substring(inicio));
            hijo2Cromosoma.append(cromosoma2.substring(inicio));
        } else {
            hijo1Cromosoma.append(cromosoma2.substring(inicio));
            hijo2Cromosoma.append(cromosoma1.substring(inicio));
        }

        return new Individuo[]{new Individuo(hijo1Cromosoma.toString(), functionType), new Individuo(hijo2Cromosoma.toString(), functionType)};
    }

    /**
     * Aplica mutación a un individuo cambiando un bit aleatorio.
     *
     * @param ind El individuo a mutar.
     */
    public void mutar(Individuo ind) {
        String cromosoma = ind.getCromosoma();
        int pos = random.nextInt(cromosoma.length());
        char nuevoBit = cromosoma.charAt(pos) == '0' ? '1' : '0';
        String nuevoCromosoma = cromosoma.substring(0, pos) + nuevoBit + cromosoma.substring(pos + 1);
        ind.setCromosoma(nuevoCromosoma);
    }

    /**
     * Ejecuta un ciclo del algoritmo genético.
     */
    public void ejecutarCiclo() {
        ArrayList<Individuo> nuevaGeneracion = new ArrayList<>();
        while (nuevaGeneracion.size() < tamanoPoblacion) {
            Individuo padre1 = seleccionarPadre();
            Individuo padre2 = seleccionarPadre();
            Individuo[] descendientes = cruza(padre1, padre2);
            nuevaGeneracion.add(descendientes[0]);
            nuevaGeneracion.add(descendientes[1]);
        }

        int numMutaciones = (int) (tamanoPoblacion * porcentajeMutacion);
        for (int i = 0; i < numMutaciones; i++) {
            mutar(nuevaGeneracion.get(random.nextInt(tamanoPoblacion)));
        }

        individuos = nuevaGeneracion;
    }

    /**
     * Devuelve la población actual.
     *
     * @return La lista de individuos en la población.
     */
    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }
}
