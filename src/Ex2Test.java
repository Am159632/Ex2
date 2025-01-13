import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex2Test {

    private Ex2Sheet sheet;
    @BeforeEach
    void setUp() {
        sheet = new Ex2Sheet(2, 2);
    }

    @Test
    void computeFormTest(){
        double a= SCell.computeForm("=8.7");
        assertEquals(a,8.7);
        a= SCell.computeForm("= 1 + 2 ");
        assertEquals(a,3.0);
        a= SCell.computeForm("=-((1+3)*((3-1.5))+3)");
        assertEquals(a,-9);
        a= SCell.computeForm("=4+4+(4*4/(4*4/(4+4)-(4*4)/4-(4*4)+4*4)/4-4)");
        assertEquals(a,2);
        a= SCell.computeForm("=+(4-2)*3-5/(2*3.5-10+1.5*2)-8+2*(4-5)");
        assertTrue(Double.isInfinite(a));
        a= SCell.computeForm("=6-(-6)");
        assertEquals(a,12);
    }

    @Test
    void eval(){
        sheet.set(0,0,"=1+3*4-5");
        sheet.set(0,1,"=A0+1");
        Assertions.assertEquals(sheet.eval(0,1),"=(1+3*4-5)+1");
        Assertions.assertEquals(SCell.computeForm(sheet.eval(0,1)),9.0);
        sheet.set(0,1,"=1.2*(8-13)+1.5*(0.2*2-(a0-5))");
        Assertions.assertEquals(sheet.eval(0, 1),"=1.2*(8-13)+1.5*(0.2*2-((1+3*4-5)-5))");
        Assertions.assertEquals(SCell.computeForm(sheet.eval(0, 1)),-9.9);
        sheet.set(1,0,"=(A1-0.1)*0.2");
        Assertions.assertEquals(sheet.eval(1, 0),"=((1.2*(8-13)+1.5*(0.2*2-((1+3*4-5)-5)))-0.1)*0.2");
        Assertions.assertEquals(SCell.computeForm(sheet.eval(1, 0)),-2);
        sheet.set(1,1,"=b0*(A1-0.1)/4-A0");
        Assertions.assertEquals(sheet.eval(1, 1),"=(((1.2*(8-13)+1.5*(0.2*2-((1+3*4-5)-5)))-0.1)*0.2)*((1.2*(8-13)+1.5*(0.2*2-((1+3*4-5)-5)))-0.1)/4-(1+3*4-5)");
        Assertions.assertEquals(SCell.computeForm(sheet.eval(1, 1)),-3);
    }

    @Test
    void value(){
        sheet.set(0,0,"Asaf");
        assertEquals(sheet.value(0,0),"Asaf");
        sheet.set(0,1,"8");
        assertEquals(sheet.value(0,1),"8");
        sheet.set(1,0,"=A0");
        Assertions.assertEquals(sheet.value(1,0), Ex2Utils.ERR_FORM);
        sheet.set(1,1,"=A1-4");
        assertEquals(sheet.value(1,1),"4.0");
        sheet.set(0,1,"=B1");
        assertEquals(sheet.value(0,1), Ex2Utils.ERR_CYCLE);
        sheet.set(0,0,"=Aa1");
        Assertions.assertEquals(sheet.value(0,0), Ex2Utils.ERR_FORM);
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
        sheet.set(0,0,"=B1");
        assertEquals(sheet.depth()[0][0],-1);
        assertEquals(sheet.depth()[0][1],-1);
        assertEquals(sheet.depth()[1][0],-1);
        assertEquals(sheet.depth()[1][1],-1);
    }
}
