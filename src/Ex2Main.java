//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Ex2Main {
    public static void main(String[] args) {
        SCell c=new SCell("wqffe");
        c.setContent("=123*4");
        System.out.println(c.getType());
        System.out.println(c.getValue());
    }
}