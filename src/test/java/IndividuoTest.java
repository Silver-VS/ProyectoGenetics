import Model.Individuo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IndividuoTest {

    @Test
    public void testCrearIndividuo() {
        Individuo individuo = new Individuo(5.0);
        assertEquals(5.0, individuo.getValor());
        assertEquals(0.0, individuo.getAptitud());
    }

    @Test
    public void testSetGetAptitud() {
        Individuo individuo = new Individuo(5.0);
        individuo.setAptitud(10.0);
        assertEquals(10.0, individuo.getAptitud());
    }
}
