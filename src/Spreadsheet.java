public class Spreadsheet {
        private Cell[][] cells;
        private int width;
        private int height;

        // Constructor
        public Spreadsheet(int width, int height) {
            this.width = width;
            this.height = height;
            cells = new Cell[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    cells[i][j] = new Cell(""); // תאים ריקים בהתחלה
                }
            }
        }

        //Getter
        public Cell getCell(int x,int y){
            return this.cells[x][y];
        }

        //Setter
        public void setCell(int x,int y,Cell c) {
            this.cells[x][y]=c;
        }

        private int xCell(String c){
            char letter=c.charAt(0);
            if ( letter<'A' || letter>'Z' )
                return -1;
            return letter-'A';
        }

        private int yCell(String c){
            String n=c.substring(1,c.length());
            for (int i=0;i<n.length();i++){
                if (!Character.isDigit(n.indexOf(i)))
                    return -1;
            }
            return Integer.parseInt(n);
        }


}