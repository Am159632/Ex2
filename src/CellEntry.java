public class CellEntry  implements Index2D {

    @Override
    public boolean isValid() {
            return false;
    }

    @Override
    public int getX() {return Ex2Utils.ERR;}

    @Override
    public int getY() {return Ex2Utils.ERR;}

}
/* public static boolean isCell(String cell){
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
        return false;*/