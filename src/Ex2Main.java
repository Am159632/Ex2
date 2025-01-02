//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Ex2Main {
    public static void main(String[] args) {
        SCell c = new SCell("=4+4+(4*4/(4*4/(4+4)-(4*4)/4-(4*4)+4*4)/4-4)");
       // boolean ok= Double.isInfinite(SCell.computeForm(c.getData()));
        System.out.println(SCell.computeForm(c.getData()));
    }
}