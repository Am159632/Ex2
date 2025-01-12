public class CellEntry  implements Index2D {
    private int x;
    private int y;
    public CellEntry(int x,int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean isValid() {
        return this.x>=0 && this.x<=('Z'-'A') && this.y>=0 && this.y<=99;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public String toString(){
        char xx= (char) (this.x+65);
        return ""+xx+this.y;
    }
}