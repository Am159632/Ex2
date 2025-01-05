public class CellEntry  implements Index2D {
    private int x;
    private int y;
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int getX() {
        return Ex2Utils.ERR;
    }

    @Override
    public int getY() {
        return Ex2Utils.ERR;
    }
}