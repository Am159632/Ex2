//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Ex2Main {
    public static void main(String[] args) {
        SCell c = new SCell("=4+4+(4*4/(4*4/(4+4)-(4*4)/4-(4*4)+4*4)/4-4)");
       // boolean ok= Double.isInfinite(SCell.computeForm(c.getData()));

        System.out.println(SCell.computeForm(c.getData()));
        CellEntry ce=CellEntry.ConvertString("A1");
        System.out.println(ce.getX()+""+ce.getY());
        Ex2Sheet table=new Ex2Sheet(1,3);
        table.set(0,0,"=5");
        table.set(0,1,"=A0+1");
        table.set(0,2,"=A2+1");
        System.out.println(table.eval(0,1));

    }
}