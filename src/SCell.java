public class SCell implements Cell {

    private String line;
    private int type;
    // Add your code here

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
        // Add your code here
        line = s;
        /////////////////////
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

    public static boolean isCell(String cell){
        String x=cell.substring(1,cell.length()-1);
        if (cell.charAt(0)>='A' & cell.charAt(0)<='Z'){
            try {
                int a= Integer.parseInt(x);
                if (a>=0 & a<=99)
                    return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static boolean RecourseIsForm(String text){
        for (int i=0;i<text.length();i++){

        }
        return true;
    }
    public static boolean isForm(String text) {
        if (!text.startsWith("="))
            return false;
        text=text.substring(1,text.length()-1);
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
    }

    private void calS(String form) {
        form = form.substring(1);
        int countA = 0, countB = 0;
        String ans = "";
        for (int i = 0; i < form.length(); i++) {
            if (form.indexOf(i) == '*' || form.indexOf(i) == '/') {
                for (int j = i - 1; j >= 0; j--) {
                    if (!Character.isDigit(form.indexOf(j))) {
                        countA = j;
                        break;
                    }
                }
                for (int j = i + 1; j < form.length(); j++) {
                    if (!Character.isDigit(form.indexOf(j))) {
                        countB = j;
                        break;
                    }
                }
                if (form.indexOf(i) == '*') {
                    form = form.substring(0, i - countA) + form.substring(i + countB + 1, form.length());
                }
            }
        }
    }


    private Double computeForm(String form) {
        String parentheses = "";
        form = form.substring(1);
        for (int i = 0; i < form.length(); i++) {
            if (form.indexOf(i) == '(') {
                for (int j = i; j < form.length(); j++) {
                    if (form.indexOf(i) == ')') {
                        parentheses = form.substring(i + 1, j - 1);
                    }
                }
            }
        }

        /*double ans = 0.0;
        while (!isNumber(form)) {
            int count = 0;
            for (int i = 1; i < form.length(); i++) {
                char x = form.charAt(i);
                if (!Character.isDigit(x) && x != '.') {
                    count = i;
                    break;
                }
            }
            String before = form.substring(1, count);
            double b = Double.parseDouble(before);
            String after = form.substring(count + 2, form.length());
            if (form.charAt(count + 1) == '*')
                ans = b * computeForm(after);
            else if (form.charAt(count + 1) == '+')
                ans = b + computeForm(after);
            else if (form.charAt(count + 1) == '/')
                ans = b / computeForm(after);
            else if (form.charAt(count + 1) == '-')
                ans = b - computeForm(after);
            return ans;*/


        return 1.0;
        //לעשות תנאי עצירה+ סדר פעולות
    }
}


