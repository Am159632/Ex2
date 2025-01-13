import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ex2Sheet implements Sheet {
    private SCell[][] table;

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

    /**
     * Validates letter patterns in cell references
     * KEY FEATURE: Ensures only single letters are used in cell references (e.g., A1 is valid, AA1 is not)
     * @param data
     * @return if valid
     */

    private boolean isLetterValid(String data) {
        int maxL = 0, count = 0;
        for (int i = 0; i < data.length(); i++) {
            if (Character.isLetter(data.charAt(i))) {
                count++;
            } else {
                if (count > 1)
                    return false;
                count = 0;
            }
        }
        return true;
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;

        Cell c = get(x, y);
        c.setData(c.getData());//uptading data(including type) ans order
        c.setOrder(depth()[x][y]);

        if (c != null)
            ans = c.toString();
        if (c.getType() == Ex2Utils.ERR_FORM_FORMAT && !isLetterValid(c.getData()))
            return Ex2Utils.ERR_FORM;

        if (ans == Ex2Utils.EMPTY_CELL)
            return Ex2Utils.EMPTY_CELL;
        if (c.getOrder() == Ex2Utils.ERR_CYCLE_FORM) {
            get(x, y).setType(Ex2Utils.ERR_CYCLE_FORM);
            return Ex2Utils.ERR_CYCLE;
        }
        if (c.getType() == Ex2Utils.TEXT)
            return ans;
        if (c.getType() == Ex2Utils.NUMBER)
            return ans;

        if (c.getType() == Ex2Utils.ERR_FORM_FORMAT || (c.getType() == Ex2Utils.FORM)) {
            SCell newcell = new SCell(eval(x, y));//change the cell that he depend on
            if (newcell.getType() == Ex2Utils.ERR_FORM_FORMAT)
                return Ex2Utils.ERR_FORM;
            if (newcell.getType() == Ex2Utils.FORM) {
                ans = "" + SCell.computeForm(newcell.getData());
                get(x, y).setType(Ex2Utils.FORM);
            }
        }
        return ans;
    }

    @Override
    public SCell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public SCell get(String cords) {
        SCell ans;
        if (cords.isEmpty())
            return new SCell(Ex2Utils.EMPTY_CELL);
        cords = cords.toUpperCase();
        String ycell = cords.substring(1);
        ans = table[cords.charAt(0) - 'A'][Integer.parseInt(ycell)];
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
        SCell c = new SCell(s);
        table[x][y] = c;
    }

    @Override
    public void eval() {

    }

    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && yy >= 0 && table.length > xx && table[0].length > yy;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[this.width()][this.height()];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                int order = table[i][j].getOrder();
                if (table[i][j].getType() == Ex2Utils.ERR_FORM_FORMAT) {
                    order = calculateOrder(i, j);
                }
                table[i][j].setOrder(order);//update the order of each cell
                ans[i][j] = order;
            }
        }

        return ans;
    }

    /**
     * Calculates dependency depth for specific cell
     * @param i
     * @param j
     * @return calculate of the depth of the cells he depends on
     */

    private int calculateOrder(int i, int j) {
        Cell cell = table[i][j];
        String xy = String.valueOf((i + 'A') + j).toUpperCase();

        List<String> visited = new ArrayList<>();
        visited.add(xy);

        return calculateOrder(visited, extractCells(cell));
    }

    /**
     * Recursive order calculation for dependency tracking
     * @param visited
     * @param cells
     * @return if cycle=-1,else the max of the celles he depens on
     */


    private int calculateOrder(List<String> visited, List<String> cells) {
        if (cells.isEmpty()) {//base case
            return 0;
        }

        int maxOrder = 0;


        for (String cellStr : cells) {
            List<String> currentPath = new ArrayList<>(visited);

            int x = cellStr.toLowerCase().charAt(0) - 'a';
            int y = Integer.parseInt(cellStr.substring(1));
            Cell cell = table[x][y];

            if (currentPath.contains(cellStr)) {
                return -1;  // Cyclic dependency detected
            }

            currentPath.add(cellStr);
            int orderResult = calculateOrder(currentPath, extractCells(cell));//recursion

            if (orderResult == -1) {
                return -1;  // Return -1 if cycle detected in deeper levels
            }

            maxOrder = Math.max(maxOrder, orderResult + 1);
        }

        return maxOrder;
    }

    /**
     * add to a list all the cells he depends on
     * @param cell
     * @return list that has all the cells he depends on
     */

    private List<String> extractCells(Cell cell) {
        List<String> cells = new ArrayList<>();
        List<String> currentRoundVisited = new ArrayList<>();

        Pattern pattern = Pattern.compile("[a-zA-Z](\\d+)");
        Matcher matcher = pattern.matcher(cell.getData());

        while (matcher.find()) {
            if (!currentRoundVisited.contains(matcher.group())) {//if same cell in the same stage,don't add
                currentRoundVisited.add(matcher.group());
                cells.add(matcher.group());
            }
        }

        return cells;
    }

    /**
     * Loads spreadsheet data from a file
     *
     * @param fileName The path of the file to load
     * @throws IOException If there is an error reading the file
     */

    @Override
    public void load(String fileName) throws IOException {
        // Initialize table with empty cells
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new SCell("");
            }
        }

        String line;
        String delimiter = ",";
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) { // Skip the header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Split the line into components
                String[] values = line.split(delimiter);

                // Ensure we have all required components (row, column, data)
                if (values.length >= 3) {
                    String x = values[0];
                    String y = values[1];
                    String data = values[2];

                    // Validate that row and column are numbers
                    if (isNumber(x) && isNumber(y)) {
                        int row = Integer.parseInt(x);
                        int col = Integer.parseInt(y);

                        // Create new cell with loaded data
                        table[row][col] = new SCell(data);
                    }
                }
            }
        }
    }


    private boolean isNumber(String text) {
        try {
            Integer.parseInt(text.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Saves the spreadsheet data to a file
     *
     * @param fileName The path where the file will be saved
     * @throws IOException If there is an error writing to the file
     */

    public void save(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("I2CS ArielU: SpreadSheet (Ex2) assignment -\n"); // header line

            // Iterate through the table and save each cell's data
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    String data = table[i][j].getData();
                    // Save in format: row,column,data
                    writer.append(i + "," + j + "," + data + "\n");
                }
            }
        }
        System.out.println("Data saved successfully to " + fileName);
    }


    @Override
    public String eval(int x, int y) {
        return (changeCell(get(x, y).getData()));
    }

    /**
     *
     * @param cell
     * @return Returns the cell after it has been replaced by
     * cells that depend on it for their data
     */

    private String changeCell(String cell) {
        StringBuffer ans = new StringBuffer(cell);

        Pattern pattern = Pattern.compile("[a-zA-Z]+\\d+");
        Matcher matcher = pattern.matcher(ans);

        boolean found = false;

        while (matcher.find()) {
            found = true;
            String match = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            if (get(match).getType() == Ex2Utils.NUMBER) //in parentheses to avoid mistakes
                ans.replace(start, end, "(" + get(match).getData() + ")");
            if (get(match).getType() == Ex2Utils.FORM || get(match).getType() == Ex2Utils.ERR_FORM_FORMAT)
                ans.replace(start, end, "(" + get(match).getData().substring(1) + ")");//without the =
            if (get(match).getType() == Ex2Utils.TEXT || get(match).getData() == Ex2Utils.EMPTY_CELL)
                ans.replace(start, end, "=@");
            if (get(match).getType() == Ex2Utils.ERR_CYCLE_FORM)
                ans.replace(start, end, "(" + get(match).getData().substring(1) + ")");
        }
        if (!found) //base case
            return ans.toString();

        return changeCell(ans.toString());
    }
}