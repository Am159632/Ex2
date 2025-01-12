import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex2Test {

    Ex2Sheet sheet =new Ex2Sheet(2,2);
    SCell c=new SCell(Ex2Utils.EMPTY_CELL);
    @Test
    void computeFormTest(){
        double a=SCell.computeForm("=8.7");
        assertEquals(a,8.7);
        a=SCell.computeForm("=((1+3)*((3-1.5))+3)");
        assertEquals(a,9);
        a=SCell.computeForm("=4+4+(4*4/(4*4/(4+4)-(4*4)/4-(4*4)+4*4)/4-4)");
        assertEquals(a,2);
        a=SCell.computeForm("=(4-2)*3-5/(2*3.5-10+1.5*2)");
        assertTrue(Double.isInfinite(a));
    }

    @Test
    void eval(){
        sheet.set(0,0,"=1+3*4-5");
        sheet.set(0,1,"=A0+1");
        assertEquals(SCell.computeForm(sheet.eval(0,1)),9.0);
        sheet.set(0,1,"=1.2*(8-13)+1.5*(0.2*2-(A0-5))");
        assertEquals(SCell.computeForm(sheet.eval(0, 1)),-9.9);
        sheet.set(1,0,"=(A1-0.1)*0.2");
        assertEquals(SCell.computeForm(sheet.eval(1, 0)),-2);
    }

    @Test
    void depth(){
        sheet.set(0,0,"5");
        sheet.set(0,1,"=A0");
        assertEquals(sheet.depth()[0][0],0);
        assertEquals(sheet.depth()[0][1],1);
        sheet.set(0,1,"=B0");
        sheet.set(1,0,"=A0+A1");
        assertEquals(sheet.depth()[1][0],-1);
        sheet.set(0,0,"=1");
        sheet.set(0,1,"=A0+A0");
        sheet.set(1,0,"=A0+A1");
        sheet.set(1,1,"=B0+A0");
        assertEquals(sheet.depth()[0][0],0);
        assertEquals(sheet.depth()[0][1],1);
        assertEquals(sheet.depth()[1][0],2);
        assertEquals(sheet.depth()[1][1],3);
    }
}
