//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Ex2Main {
    public static void main(String[] args) {
        SCell c=new SCell("=((1+2)+(1+2))*2 + (3 .5)");
        System.out.println(c.computeForm(c.getData()));

    }
}