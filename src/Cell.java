
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
            this.value = null; // נוסחה תחושב בהמשך
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

    public boolean isNumber(){
        boolean ans=true;
        for (int i=0;i<this.content.length();i++){
            if (!Character.isDigit(this.content.charAt(i)))
                return false;
        }
        return ans;
    }
    public boolean isText(){

    }
    public boolean isForm(){
        boolean ans=true;
        if (this.content.startsWith("=")){
            for (int i=0;i<this.content.length();i++){
                if (!Character.isDigit(this.content.charAt(i))&&)
            }
        }
        else { ans=false;}
        return ans;
    }
}
