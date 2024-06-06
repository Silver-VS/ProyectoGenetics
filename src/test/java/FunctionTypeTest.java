import Model.FunctionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionTypeTest {

    @Test
    public void testValues() {
        FunctionType[] values = FunctionType.values();
        assertEquals(3, values.length);
    }

    @Test
    public void testValueOf() {
        FunctionType abs = FunctionType.valueOf("ABS");
        assertEquals(FunctionType.ABS, abs);
    }
}
