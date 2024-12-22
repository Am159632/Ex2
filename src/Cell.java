public class Cell {
    private String content;
    private Double value;
    private String type;

    // Constructor
    public Cell(String content) {
        this.content = content;
        if (isNumber(content)) {
            this.type = "Number";
            this.value = Double.parseDouble(content);
        } else if (isForm(content)) {
            this.type = "Formula";
            this.value = computeForm(content); // נוסחה תחושב בהמשך
        } else {
            this.type = "Text";
            this.value = null; // טקסט לא מחושב
        }
    }

    // Getters
    public String getContent() {
        return this.content;
    }

    public Double getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    private boolean isNumber(String text){
        boolean ans=true;
        for (int i=0;i<text.length();i++){
            if (!Character.isDigit(text.charAt(i)))
                return false;
        }
        return ans;
    }
    private boolean isText(String text){
        if (isText(text) && !isForm(text))
            return true;
        return false;
    }
    private boolean isForm(String text){
        /*boolean ans=true;
        if (this.content.startsWith("=")){
            for (int i=0;i<text.length();i++){
                char x=text.charAt(i);
                if ( !Character.isDigit(x) || x!='.' || x!='+' || x!='-' || x!='*' || x!='/' || x!='(' || x!=')' )
                    return false;
            }
        }
        else { ans=false;}*/
        if (text.indexOf(0)=='=')
            return true;
        return false;
    }

    private Double computeForm(String form) {
        double ans = 0.0;
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
            String after = form.substring(count + 2, this.content.length());
            double a = Double.parseDouble(after);
            if (this.content.charAt(count + 1) == '*')
                ans = b * computeForm(after);
            else if (this.content.charAt(count + 1) == '+')
                ans = b + computeForm(after);
            else if (this.content.charAt(count + 1) == '/')
                ans = b / computeForm(after);
            else if (this.content.charAt(count + 1) == '-')
                ans = b - computeForm(after);
            return ans;
        }
        return ans;

    }
}
