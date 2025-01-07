import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ex2Test {


    @Test
    void computeFormTest(){
        double a=SCell.computeForm("=((1+3)*((3-1.5))+3)");
        assertEquals(a,9);
        a=SCell.computeForm("=4+4+(4*4/(4*4/(4+4)-(4*4)/4-(4*4)+4*4)/4-4)");
        assertEquals(a,2);
    }
}
