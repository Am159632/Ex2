import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;

        Cell c = get(x, y);
        c.setOrder(depth()[x][y]);
        if (c != null) {
            ans = c.toString();
        }
        if (ans == Ex2Utils.EMPTY_CELL)
            return Ex2Utils.EMPTY_CELL;
        if (c.getOrder() == Ex2Utils.ERR_CYCLE_FORM)
            return Ex2Utils.ERR_CYCLE;
        if (c.getType() == Ex2Utils.TEXT)
            return ans;
        if (c.getType() == Ex2Utils.NUMBER)
            return ans;
        if (c.getType() == Ex2Utils.FORM)
            ans = "" + SCell.computeForm(c.getData());
        if (c.getType() == Ex2Utils.ERR_FORM_FORMAT) {
           if (eval(x,y)==Ex2Utils.ERR_FORM)
               ans = Ex2Utils.ERR_FORM;
           else
               ans="" + SCell.computeForm(eval(x,y));
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
        int[][] dd = depth();
        int depthI =-1;
        for (int i = 0; i < dd.length; i++) {
            for (int j = 0; j < dd[0].length; j++) {
                if (dd[i][j]==depthI){

                }
            }
        }
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
                table[i][j].setOrder(order);
                ans[i][j] = order;
            }
        }

        return ans;
    }

    private int calculateOrder(int i, int j) {
        Cell cell = table[i][j];
        String xy = String.valueOf((i + 'A') + j).toUpperCase();

        List<String> visited = new ArrayList<>();
        visited.add(xy);

        return calculateOrder(visited, extractCells(cell));
    }

    private int calculateOrder(List<String> visited, List<String> cells) {
        if (cells.isEmpty()) {
            return 0;
        }

        int order = 1;

        int max = 0;

        boolean isCyclic = false;

        for (int index = 0; index < cells.size() && !isCyclic; index++) {
            String cellStr = cells.get(index);
            // get the X & Y of the cell and if not cyclic -> dig deeper
            int x = cellStr.toLowerCase().charAt(0) - 'a';
            int y = Integer.parseInt(cellStr.substring(1));
            Cell cell = table[x][y];
            if (visited.contains(cellStr)) {
                isCyclic = true;
                visited.clear();
            }
            if (!isCyclic) {
                visited.add(cellStr);
                order += calculateOrder(visited, extractCells(cell));
            }
        }

        if (visited.isEmpty()) return -1;

        max = Math.max(max, order);

        return max;
    }

    private List<String> extractCells(Cell cell) {
        List<String> cells = new ArrayList<>();

        Pattern pattern = Pattern.compile("[a-zA-Z]+\\d+");
        Matcher matcher = pattern.matcher(cell.getData());

        while (matcher.find()) {
            cells.add(matcher.group());
        }

        return cells;
    }

    @Override
    public void load(String fileName) throws IOException {
        table = new SCell[Ex2Utils.WIDTH][Ex2Utils.HEIGHT];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new SCell("");
            }
        }
        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                // 3 is a normal line, 4 with remark
                if (values.length == 3 || values.length == 4) {
                    String x = values[0];
                    String y = values[1];
                    String data = values[2];

                    if (isNumber(x) && isNumber(y)) {
                        table[Integer.parseInt(x)][Integer.parseInt(y)] = new SCell(data);
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

    @Override
    public void save(String fileName) throws IOException {

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Header");

            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[0].length; j++) {
                    if (table[i][j] != null && !Objects.equals(table[i][j].getData(), "")) {
                        writer.append(String.valueOf(i))
                                .append(",")
                                .append(String.valueOf(j))
                                .append(",")
                                .append(table[i][j].getData())
                                .append("\n");
                    }
                }
            }
        }

    }

    @Override
    public String eval(int x, int y) {
        SCell cell = get(x, y);
        if (cell == null)
            return Ex2Utils.EMPTY_CELL;
        return changeCellEval(cell.getData());
    }

    private String changeCellEval(String ans) {
        if (ans.isEmpty())
            return null;
        boolean ok=true;
        for (int i=0;i<ans.length();i++){
            if (Character.isLetter(ans.charAt(i)))
                ok=false;
        }
        if (ok)
            return ans;
        int startI = 0, count = 0;
        for (int i = 0; i < ans.length(); i++) {
            if (ans.charAt(i) >= 'A' && ans.charAt(i) <= 'Z') {
                count = 0;
                startI = i;
                for (int j = i + 1; j < ans.length(); j++) {
                    if (Character.isDigit(ans.charAt(j))) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == 0) {
                    return Ex2Utils.ERR_FORM;
                } else {
                    break;
                }
            }
        }
        SCell c = get(ans.substring(startI, startI + count + 1));
        if(c.getData()==Ex2Utils.EMPTY_CELL)
            return Ex2Utils.ERR_FORM;
        if (c.getType()== Ex2Utils.FORM ) {
            if (ans.length() > startI + count + 1)
                return ans.substring(0, startI) + SCell.computeForm(c.getData()) + changeCellEval(ans.substring(startI + count + 1));
            return ans.substring(0, startI) + SCell.computeForm(c.getData());
        }
        if (c.getType()== Ex2Utils.NUMBER){
            if (ans.length() > startI + count + 1)
                return ans.substring(0, startI) + SCell.computeForm("="+c.getData()) + changeCellEval(ans.substring(startI + count + 1));
            return ans.substring(0, startI) + SCell.computeForm("="+c.getData());
        }
        if (ans.length()>startI+count+1)
            return ans.substring(0, startI) + changeCellEval(c.getData()) + changeCellEval(ans.substring(startI + count + 1));
        return ans.substring(0, startI) + changeCellEval(c.getData());
    }
}


    /*@Override
    public String eval(int x, int y) {
            if (changeCellEval(get(x,y).getData()).getType() == Ex2Utils.ERR_FORM_FORMAT)
                return Ex2Utils.ERR_FORM;
        return value(x, y);
    }
    private double getCellValue(String cell){
        CellEntry c=CellEntry.ConvertString(cell);
        return SCell.computeForm(get(c.getX(),c.getY()).getData());
    }


    private Cell changeCellEval(String ans) {
      SCell c=new SCell(Ex2Utils.EMPTY_CELL);
      for (int i=1;i<ans.length();i++){
          if (ans.charAt(i)>='A' && ans.charAt(i)<='Z'){
              int count=0;
              for (int j=i+1;j<ans.length();j++){
                  if (Character.isDigit(ans.charAt(j)))
                      count++;
                  else
                      break;
              }
              if (count==0){
                  c.setData(ans);
                  return c;
              }
              else {
                  c.setData(c.getData()+getCellValue(ans.substring(i,i+count+1)));
                  i=i+count;
              }
          }
          else
              c.setData(c.getData()+ans.charAt(i));
      }
      return c;
    }
*/