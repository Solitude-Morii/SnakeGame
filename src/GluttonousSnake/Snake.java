package GluttonousSnake;

@SuppressWarnings({"all"})
public class Snake {
    private int x;  //x的坐标
    private int y;  //y的坐标
    static int snakeLength = 3; //蛇的长度

    public Snake(int x, int y) {
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
}
