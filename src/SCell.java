public class SCell implements Cell {

    private String line;
    private int type;
    private int order;

    public SCell(String s) {
        setData(s);
    }

    @Override
    public int getOrder() {

        return 0;
    }

    //@Override
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
        // Add your code here

    }

    private static boolean isNumber(String text) {
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
    //בודק אם התא נכון

    public static int valueOP(char c){
        if (c=='+' | c=='-')
            return 1;
        if (c=='*' | c=='/')
            return 2;
        return 3;
    }
    public static int lowIOP(String text){
        int count=0,index=-1,lowOP=3;
        for (int i=0;i<text.length();i++){
            char x=text.charAt(i);
            if (x== '(')
                count++;
            if (x== ')')
                count--;
            if (count==0){
                if (valueOP(x)<lowOP){
                    lowOP=valueOP(x);
                    index=i;
                }
            }
        }
        return index;
    }
    public static double cal(char op,double l,double r){
        if (op=='*')
            return l*r;
        if (op=='+')
            return l+r;
        if(op=='-')
            return l-r;
        if (r==0)
            throw new RuntimeException("infinity");
        return l/r;
    }
    public boolean isValid(String text){
        if (!text.startsWith("="))
            return false;
        text=text.substring(1,text.length());
        int count=0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i)=='(')
                count++;
            if (text.charAt(i)==')'){
                if (count>0)
                    count--;
                else
                    return false;
            }
        }
        if (count>0)
            return false;
        for (int i = 0; i < text.length(); i++){
            char x=text.charAt(i);
            if (x!='*' && x!='/' && x!='+' && x!='-' && x!='(' && x!=')' && x!='.' && !Character.isDigit(x))
                return false;
        }
        return true;
    }
    public  boolean isForm(String text) {

        try {
           double d=computeForm(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Double computeForm(String form) {

        form=form.replaceAll("\\s+", "");

        if (!isValid(form))
            throw new RuntimeException("not valid");

        if (form.startsWith("="))
            form=form.substring(1,form.length());

        if (isNumber(form))
            return Double.parseDouble(form);

        if (lowIOP(form)==0)
            throw new RuntimeException("not valid");

        String left=form.substring(0,lowIOP(form));
        String right=form.substring(lowIOP(form)+1,form.length());
         if (left.startsWith("(") & left.endsWith(")")){
            left=form.substring(1,left.length()-1);
        }
        if (right.startsWith("(") & right.endsWith(")")){
            right=right.substring(1,right.length()-1);
        }
        double Vl=computeForm("="+left);
        double Vr=computeForm("="+right);

        char x=form.charAt(lowIOP(form));
        return cal(x,Vl,Vr);
    }
}


