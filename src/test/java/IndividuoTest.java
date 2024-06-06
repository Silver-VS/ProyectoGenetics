import Model.FunctionType;
import Model.Individuo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la clase Model.Individuo.
 */
public class IndividuoTest {

    @Test
    public void testEvaluateAbs() {
        int x = 10;
        double expected = Math.abs((x - 5) / 2.0 + Math.sin(x));
        assertEquals(expected, Individuo.evaluate(FunctionType.ABS, x));
    }

    @Test
    public void testEvaluateSquare() {
        int x = 10;
        double expected = Math.pow(x, 2);
        assertEquals(expected, Individuo.evaluate(FunctionType.SQUARE, x));
    }

    @Test
    public void testEvaluateSinCos() {
        int x = 10;
        double expected = Math.sin(x) + Math.cos(x);
        assertEquals(expected, Individuo.evaluate(FunctionType.SIN_COS, x));
    }
}
