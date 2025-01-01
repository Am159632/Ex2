public class SCell implements Cell {

    private String line;
    private int type;

    public SCell(String s) {
        setData(s);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String toString() {
        return getData();
    }

    @Override
    public void setData(String s) {
        line = s;
    }

    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {

    }

    public static boolean isNumber(String text) {
        text = text.replaceAll("\\s+", "");

        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isText(String text) {
        if (!isNumber(text) && !text.startsWith("="))
            return true;
        return false;
    }

    public static int valueOP(char c) {
        if (c == '+' | c == '-')
            return 1;
        if (c == '*' | c == '/')
            return 2;
        return 3;
    }

    public static int indOfMainOp(String text) {
        int count = 0, index = 0, lowOP = 3;
        for (int i = 0; i < text.length(); i++) {
            char x = text.charAt(i);
            if (x == '(')
                count++;
            if (x == ')')
                count--;
            if (count == 0) {
                if (valueOP(x) < lowOP) {
                    lowOP = valueOP(x);
                    index = i;
                }
            }
        }
        return index;
    }

    public static double cal(char op, double l, double r) {
        if (op == '*')
            return l * r;
        if (op == '+')
            return l + r;
        if (op == '-')
            return l - r;
        if (r == 0)
            throw new RuntimeException("infinity");
        return l / r;
    }

    public static boolean isValidForm(String text) {
        if (!text.startsWith("="))
            return false;
        text = text.substring(1, text.length());
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(')
                count++;
            if (text.charAt(i) == ')') {
                if (count > 0)
                    count--;
                else
                    return false;
            }
        }
        if (count > 0)
            return false;
        for (int i = 0; i < text.length(); i++) {
            char x = text.charAt(i);
            if (x != '*' && x != '/' && x != '+' && x != '-' && x != '(' && x != ')' && x != '.' && !Character.isDigit(x))
                return false;
        }
        return true;
    }

    public static boolean isForm(String text) {
        try {
            double d = computeForm(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Double computeForm(String form) {

        form = form.replaceAll("\\s+", "");


        if (form.startsWith("=-(")) {
            String x = form.substring(2, form.length());
            return computeForm("=0-1*" + x);
        }
        if (form.startsWith("=+(")) {
            String x = form.substring(2, form.length());
            return computeForm("=" + x);
        }

        if (!isValidForm(form))
            throw new RuntimeException("not valid");

        if (form.startsWith("="))
            form = form.substring(1, form.length());

        if (isNumber(form))
            return Double.parseDouble(form);

        if (form.startsWith("(") & form.endsWith(")")) {
            String x = "=" + form.substring(1, form.length() - 1);
            if (isForm(x))
                return computeForm(x);
        }

        String left = form.substring(0, indOfMainOp(form));
        String right = form.substring(indOfMainOp(form) + 1, form.length());

        double Vl = computeForm("=" + left);
        double Vr = computeForm("=" + right);

        char x = form.charAt(indOfMainOp(form));
        return cal(x, Vl, Vr);
    }
}



