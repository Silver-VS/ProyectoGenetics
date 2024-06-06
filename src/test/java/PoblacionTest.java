import Model.FunctionType;
import Model.Individuo;
import Model.Poblacion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase de pruebas para la clase Model.Poblacion.
 */
public class PoblacionTest {

    @Test
    public void testInicializarPoblacion() {
        int tamanoPoblacion = 10;
        Poblacion poblacion = new Poblacion(tamanoPoblacion, 0.1, FunctionType.ABS);
        assertEquals(tamanoPoblacion, poblacion.getIndividuos().size());
    }

    @Test
    public void testSeleccionarPadre() {
        int tamanoPoblacion = 10;
        Poblacion poblacion = new Poblacion(tamanoPoblacion, 0.1, FunctionType.ABS);
        Set<Individuo> padres = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            padres.add(poblacion.seleccionarPadre());
        }
        assertFalse(padres.isEmpty());
    }

    @Test
    public void testCruza() {
        Individuo padre1 = new Individuo("101010", FunctionType.ABS);
        Individuo padre2 = new Individuo("010101", FunctionType.ABS);
        Poblacion poblacion = new Poblacion(10, 0.1, FunctionType.ABS);
        Individuo[] hijos = poblacion.cruza(padre1, padre2);
        assertEquals(2, hijos.length);
        assertNotNull(hijos[0].getCromosoma());
        assertNotNull(hijos[1].getCromosoma());
    }

    @Test
    public void testMutar() {
        Individuo individuo = new Individuo("101010", FunctionType.ABS);
        Poblacion poblacion = new Poblacion(10, 0.1, FunctionType.ABS);
        String originalCromosoma = individuo.getCromosoma();
        poblacion.mutar(individuo);
        assertNotEquals(originalCromosoma, individuo.getCromosoma());
    }
}
