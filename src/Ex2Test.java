import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ex2Test {
    @Test
    void isNumberTest() {
        String[] good = {"1", "12345", "124.231", "0.12"};
        for (int i = 0; i < good.length; i++) {
            boolean ok = SCell.isNumber(good[i]);
            assertTrue(ok);
        }
    }

    @Test
    void computeFormTest(){
        double a=SCell.computeForm("=((1+3)*((3-1.5))+3)");
        assertEquals(a,9);
    }
}
