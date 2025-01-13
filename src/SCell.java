public class SCell implements Cell {

    private String line;
    private int type;
    private  int order;

    public SCell(String s) {
        setData(s);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String toString() {
        return getData();
    }

    @Override
    public void setData(String s) {
        this.line = s;
        this.order=0;
        if (SCell.isText(s))
            this.type= Ex2Utils.TEXT;
        if (SCell.isNumber(s))
            this.type= Ex2Utils.NUMBER;
        if(SCell.isForm(s))
            this.type= Ex2Utils.FORM;
        if (!SCell.isForm(s) && s.startsWith("="))
            this.type= Ex2Utils.ERR_FORM_FORMAT;
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
        type=t;
    }

    @Override
    public void setOrder(int t) {
        this.order=t;
    }

    private static boolean isText(String text) {
        if (!isNumber(text) && !text.startsWith("="))
            return true;
        return false;
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

    /**
     * +|- = 1, *|/ =2
     * @param c
     * @return value of the operator
     */


    private static int valueOP(char c) {
        if (c == '+' | c == '-')
            return 1;
        if (c == '*' | c == '/')
            return 2;
        return 3;
    }

    /**
     * Calculates the formula according to the operator
     * @param op
     * @param l
     * @param r
     * @return the value of the calculate
     */

    private static double cal(char op, double l, double r) {
        if (op == '*')
            return l * r;
        if (op == '+')
            return l + r;
        if (op == '-')
            return l - r;
        return l / r;
    }

    /**
     * Checks where the index of the operator is according to the one outside the parentheses
     * and the last one due to the order of operations
     * @param text
     * @return index of the operator that we want to calculate this round
     */

    public static int indOfMainOp(String text) {
        int count = 0, index = -1, lowOP = 3;
        for (int i = 0; i < text.length(); i++) {
            char x = text.charAt(i);
            if (x == '(')
                count++;
            if (x == ')')
                count--;
            if (count == 0) {
                if (valueOP(x) <= lowOP) {
                    lowOP = valueOP(x);
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * Checking the formula validity(if start with = ,if it has valid parentheses ,if  it has valid parentheses a wrong char)
     * @param text
     * @return true if valid
     */

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
        if (!isValidForm(text))
            return false;
        try {
            double d = computeForm(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * compute the formula
     * @param form
     * @return the value of the formula
     */

    public static Double computeForm(String form) {

        form = form.replaceAll("\\s+", "");//delete spaces
        if (form.startsWith("=-")) {
            String x = form.substring(2, form.length());//exception handling
            return computeForm("=0-1*" + x);
        }
        if (form.startsWith("=+")) {
            String x = form.substring(2, form.length());
            return computeForm("=" + x);
        }
        if (!isValidForm(form))
            throw new RuntimeException("not valid");

        form = form.substring(1, form.length());

        if (isNumber(form)) //Stopping condition
            return Double.parseDouble(form);

        if (form.startsWith("(") & form.endsWith(")")) {//if in parentheses and inside the formula is correct
            String x = "=" + form.substring(1, form.length() - 1);
            if(isForm(x))
                return computeForm(x);
        }

        String left = form.substring(0, indOfMainOp(form));
        String right = form.substring(indOfMainOp(form) + 1, form.length());

        double Vl = computeForm("=" + left);//recoursion
        double Vr = computeForm("=" + right);

        char x = form.charAt(indOfMainOp(form));
        return cal(x, Vl, Vr);
    }
}



