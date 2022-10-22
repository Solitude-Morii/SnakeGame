package GluttonousSnake;

@SuppressWarnings({"all"})
public class Food {
    private int x;  //食物的x坐标
    private int y;  //食物的y坐标
    private boolean isLive = false; //是否存再
    static int foodnum = 0; //食物的数量

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
