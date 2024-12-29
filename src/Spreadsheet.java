public class Spreadsheet {
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
            char isnumber =c.charAt(1);
            if ( letter<'A' || letter>'Z' )
                return -1;
            if (!Character.isDigit(isnumber))
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