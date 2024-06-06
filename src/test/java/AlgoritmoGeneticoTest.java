import Model.AlgoritmoGenetico;
import Model.FunctionType;
import Model.Individuo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoritmoGeneticoTest {
    private AlgoritmoGenetico algoritmoGenetico;

    @BeforeEach
    public void setUp() {
        algoritmoGenetico = new AlgoritmoGenetico(10, 10, 0.2, true, FunctionType.SQUARE, 0, 10);
    }

    @Test
    public void testInicializarPoblacion() {
        algoritmoGenetico.ejecutar();
        List<Individuo> poblacion = algoritmoGenetico.getPoblacion();
        assertNotNull(poblacion);
        assertEquals(10, poblacion.size());
        for (Individuo individuo : poblacion) {
            assertTrue(individuo.getValor() >= 0 && individuo.getValor() <= 10);
        }
    }

    @Test
    public void testEvaluarFuncionAbs() {
        algoritmoGenetico = new AlgoritmoGenetico(10, 10, 0.2, true, FunctionType.ABS, 0, 10);
        algoritmoGenetico.ejecutar();
        List<Individuo> poblacion = algoritmoGenetico.getPoblacion();
        for (Individuo individuo : poblacion) {
            double valor = individuo.getValor();
            double aptitudEsperada = Math.abs((valor - 5) / 2.0 + Math.sin(valor));
            assertEquals(aptitudEsperada, individuo.getAptitud(), 0.0001);
        }
    }

    @Test
    public void testEvaluarFuncionSquare() {
        algoritmoGenetico = new AlgoritmoGenetico(10, 10, 0.2, true, FunctionType.SQUARE, 0, 10);
        algoritmoGenetico.ejecutar();
        List<Individuo> poblacion = algoritmoGenetico.getPoblacion();
        for (Individuo individuo : poblacion) {
            double valor = individuo.getValor();
            double aptitudEsperada = Math.pow(valor, 2);
            assertEquals(aptitudEsperada, individuo.getAptitud(), 0.0001);
        }
    }

    @Test
    public void testEvaluarFuncionSinCos() {
        algoritmoGenetico = new AlgoritmoGenetico(10, 10, 0.2, true, FunctionType.SIN_COS, 0, 10);
        algoritmoGenetico.ejecutar();
        List<Individuo> poblacion = algoritmoGenetico.getPoblacion();
        for (Individuo individuo : poblacion) {
            double valor = individuo.getValor();
            double aptitudEsperada = Math.sin(valor) + Math.cos(valor);
            assertEquals(aptitudEsperada, individuo.getAptitud(), 0.0001);
        }
    }

    @Test
    public void testAplicarMutacion() {
        algoritmoGenetico.ejecutar();
        boolean mutacionAplicada = false;
        for (Individuo individuo : algoritmoGenetico.getPoblacion()) {
            if (individuo.getValor() != 0) {  // Assuming the original value is not 0
                mutacionAplicada = true;
                break;
            }
        }
        assertTrue(mutacionAplicada, "La mutación debería haberse aplicado al menos a un individuo.");
    }

    @Test
    public void testRegistrarMejorIndividuo() {
        algoritmoGenetico.ejecutar();
        Individuo mejorIndividuo = algoritmoGenetico.getMejorIndividuo();
        assertNotNull(mejorIndividuo);
        List<Double> historiaMejorAptitud = algoritmoGenetico.getHistoriaMejorAptitud();
        assertFalse(historiaMejorAptitud.isEmpty());
        assertEquals(mejorIndividuo.getAptitud(), historiaMejorAptitud.getLast(), 0.0001);
    }
}
