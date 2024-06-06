package Model;

/**
 * Clase que representa un individuo en la población del algoritmo genético.
 */
public class Individuo {
    private double valor;
    private double aptitud;

    public Individuo(double valor) {
        this.valor = valor;
        this.aptitud = 0.0;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getAptitud() {
        return aptitud;
    }

    public void setAptitud(double aptitud) {
        this.aptitud = aptitud;
    }

    @Override
    public String toString() {
        return "Individuo{" +
                "valor=" + valor +
                ", aptitud=" + aptitud +
                '}';
    }
}
