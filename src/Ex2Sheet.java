import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i = i + 1) {
            for (int j = 0; j < y; j = j + 1) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell c = get(x, y);
        if (c != null) {
            ans = c.toString();
        }
        if (c.getType() == Ex2Utils.ERR_FORM_FORMAT) {
            c.setData(eval(x, y));
            if (c.getType() == Ex2Utils.ERR_FORM_FORMAT)
                ans = Ex2Utils.ERR_FORM;
            else if (c.getType() == Ex2Utils.FORM)
                ans = "" + SCell.computeForm(c.getData());
        }
        if (c.getType() == Ex2Utils.ERR_CYCLE_FORM)
            return Ex2Utils.ERR_CYCLE;
        if (c.getType() == Ex2Utils.TEXT)
            return ans;
        if (c.getType() == Ex2Utils.NUMBER)
            return ans;
        if (c.getType() == Ex2Utils.FORM) {
            ans = "" + SCell.computeForm(c.getData());
        }
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        // Add your code here

        /////////////////////
        return ans;
    }

    @Override
    public int width() {
        return table.length;
    }

    @Override
    public int height() {
        return table[0].length;
    }

    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }

    @Override
    public void eval() {
        int[][] dd = depth();
        for (int i = 0; i < dd.length; i++) {
            for (int j = 0; j < dd[0].length; j++) {
            }
        }
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx >= 0 && yy >= 0;
        // Add your code here

        /////////////////////
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here

        // ///////////////////
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if (get(x, y) != null)
            ans = value(x,y);
        if (get(x, y).getType() == Ex2Utils.ERR_FORM_FORMAT) {
            ans = changeCellEval(ans);
            ans = "" + SCell.computeForm(get(x, y).getData());
        } else ans = value(x, y);
        return ans;
    }
    private double getCellValue(String cell){
        CellEntry c=CellEntry.ConvertString(cell);
        return SCell.computeForm(get(c.getX(),c.getY()).getData());
    }

    private String changeCellEval(String ans) {
       CellEntry cell;
        for (int i = 1; i < ans.length(); i++) {
           cell=CellEntry.ConvertString(ans.substring(i,i+2));
            if (cell.isValid()){
                int type=get(cell.getX(),cell.getY()).getType();
                if (type==Ex2Utils.FORM || type==Ex2Utils.NUMBER){
                  SCell.computeForm(get(cell.getX(),cell.getY()).getData());
                    ans=ans.substring(0,i)+SCell.computeForm(get(cell.getX(),cell.getY()).getData())+ans.substring(i+2);
                }
            }
            cell=CellEntry.ConvertString(ans.substring(i,i+3));
            if (cell.isValid()){
                int type=get(cell.getX(),cell.getY()).getType();
                if (type==Ex2Utils.FORM || type==Ex2Utils.NUMBER){
                    SCell.computeForm(get(cell.getX(),cell.getY()).getData());
                    ans=ans.substring(0,i)+SCell.computeForm(get(cell.getX(),cell.getY()).getData())+ans.substring(i+3);
                }
            }
        }
        return ans;
    }
}



/*public class Spreadsheet {
        private SCell[][] SCells;
        private int width;
        private int height;

        // Constructor
        public Spreadsheet(int width, int height) {
            this.width = width;
            this.height = height;
            SCells = new SCell[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    SCells[i][j] = new SCell(""); // תאים ריקים בהתחלה
                }
            }
        }

        //Getter
        public SCell getCell(int x, int y){
            return this.SCells[x][y];
        }

        //Setter
        public void setCell(int x, int y, SCell c) {
            this.SCells[x][y]=c;
        }
        private int xCell(String c){
        char letter=c.charAt(0);
        return letter-'A';
    }

    private int yCell(String c){
        String n=c.substring(1,c.length());
        return Integer.parseInt(n);
    }
    public static boolean isCell(String cell) {
        String x = cell.substring(1, cell.length() );
        if (cell.charAt(0) >= 'A' & cell.charAt(0) <= 'Z') {
            try {
                int a = Integer.parseInt(x);
                if (a >= 0 & a <= 99)
                    return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }


    }*/
