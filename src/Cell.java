public class Cell {
    private String content;
    private Double value;
    private String type;

    // Constructors
    public Cell(){
    }

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

    private boolean isNumber(String text){
        boolean ans=true;
        for (int i=0;i<text.length();i++){
            if (!Character.isDigit(text.charAt(i)) && text.charAt(i)!='.' )
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
        if (text.startsWith(=))
            return true;
        return false;
    }

    private void calS(String form){
        form=form.substring(1);
        int countA=0,countB=0;
        String ans="";
        for (int i=0;i<form.length();i++){
            if (form.indexOf(i)=='*' || form.indexOf(i)=='/' ){
                for (int j=i-1;j>=0;j--){
                    if (!Character.isDigit(form.indexOf(j))){
                        countA=j;
                        break;
                    }
                }
                for (int j=i+1;j<form.length();j++){
                    if (!Character.isDigit(form.indexOf(j))){
                        countB=j;
                        break;
                    }
                }
                if (form.indexOf(i)=='*'){
                    form=form.substring(0,i-countA)+form.substring(i+countB+1,form.length());
                }
            }
        }
    }

    private Double computeForm(String form) {
        String parentheses="";
        form=form.substring(1);
        for (int i=0;i<form.length();i++){
            if (form.indexOf(i)=='(') {
                    for (int j=i;j<form.length();j++){
                        if (form.indexOf(i)==')'){
                            parentheses=form.substring(i+1,j-1);
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
        }

        return 1.0;
        //לעשות תנאי עצירה+ סדר פעולות
    }
}
