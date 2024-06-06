import Model.AlgoritmoGenetico;
import Model.FunctionType;
import Model.Individuo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la clase Model.AlgoritmoGenetico.
 */
public class AlgoritmoGeneticoTest {

    @Test
    public void testEjecutar() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(10, 5, 0.1, true, FunctionType.ABS);
        algoritmoGenetico.ejecutar();
        assertEquals(5, algoritmoGenetico.getHistoriaMejorAptitud().size());
    }

    @Test
    public void testObtenerMejorIndividuo() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(10, 5, 0.1, true, FunctionType.ABS);
        algoritmoGenetico.ejecutar();
        Individuo mejorIndividuo = algoritmoGenetico.getMejorIndividuo();
        assertNotNull(mejorIndividuo);
        assertTrue(mejorIndividuo.getAptitud() >= 0);
    }

    @Test
    public void testMaximizarFuncion() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(10, 5, 0.1, true, FunctionType.SQUARE);
        algoritmoGenetico.ejecutar();
        double mejorAptitud = algoritmoGenetico.getMejorIndividuo().getAptitud();
        assertTrue(mejorAptitud > 0);
    }

    @Test
    public void testMinimizarFuncion() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(10, 5, 0.1, false, FunctionType.SQUARE);
        algoritmoGenetico.ejecutar();
        double mejorAptitud = algoritmoGenetico.getMejorIndividuo().getAptitud();
        assertTrue(mejorAptitud >= 0);
    }
}
