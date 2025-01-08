import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ex2Test {

    Ex2Sheet sheet =new Ex2Sheet(2,2);
    SCell c=new SCell(Ex2Utils.EMPTY_CELL);
    CellEntry index=new CellEntry(0,0);
    int x,y;
   final double eps=0.01;

    @Test
    void computeFormTest(){
        double a=SCell.computeForm("=((1+3)*((3-1.5))+3)");
        assertEquals(a,9);
        a=SCell.computeForm("=4+4+(4*4/(4*4/(4+4)-(4*4)/4-(4*4)+4*4)/4-4)");
        assertEquals(a,2);
    }

    @Test
    void eval(){
        sheet.set(0,0,"=1+3*4-5");
        sheet.set(0,1,"=A0+1");
        assertEquals(SCell.computeForm("="+sheet.eval(0,1)),9.0);
    }

    @Test
    void depth(){
        sheet.set(0,0,"5");
        sheet.set(0,1,"=A0");
        assertEquals(sheet.depth()[0][1],1);
    }
}
