package GluttonousSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

@SuppressWarnings({"all"})
public class MyPanel extends JPanel implements KeyListener, Runnable {

    //蛇的属性
    Vector<Snake> snakes = null; //蛇
    private char direct = 'U'; //方向
    private boolean isLiver = false; //是否存活

    //食物的属性
    private Food food = null; //食物

    //游戏的属性
    private boolean isStart = false; //是否开始游戏
    private int score = 0; //本局得分

    public MyPanel() {
        //蛇的初始化
        snakes = new Vector<>(Snake.snakeLength);
        snakes.add(new Snake(500, 400)); // 第一截(会覆盖)
        snakes.add(new Snake(500, 400));
        for (int i = 1; i < Snake.snakeLength; i++) {
            Snake snake = new Snake(500, 400 + i * 15);
            snakes.add(snake);
        }

        //食物的初始化
        food = new Food((int) (Math.random() * 700), (int) (Math.random() * 600));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //界面绘制
        g.fillRect(0, 0, 1000, 750);

        //得分绘制
        snakeScore(g);
        
        //游戏暂停
        if (isLiver == true && isStart == false) {
            g.setColor(Color.PINK);
            Font font = new Font("微软雅黑", Font.ITALIC, 50);
            g.setFont(font);
            g.drawString("游戏暂停", 370, 320);
            font = new Font("宋体", Font.BOLD, 25);
            g.setFont(font);
            g.drawString("(按空格继续游戏)", 360, 360);
        }

        //开始游戏
        if (isLiver == false && isStart == false) {
            g.setColor(Color.PINK);
            Font font = new Font("微软雅黑", Font.ITALIC, 50);
            g.setFont(font);
            g.drawString("新游戏", 390, 320);
            font = new Font("宋体", Font.BOLD, 25);
            g.setFont(font);
            g.drawString("(按空格开始游戏)", 360, 360);
        }

        //游戏开始
        if (isLiver == true && isStart == true) { //蛇存活并且已开始游戏
            snakeRun(); //蛇的移动
            snakePaint(g);  //蛇的绘制
            if (food.isLive() == false) { //食物不存在时
                snakeFood(g); //食物的绘制
            }
        }
    }

    //蛇的绘制
    public void snakePaint(Graphics g) {
        for (int i = 0; i < snakes.size(); i++) {
            Snake snake = snakes.get(i);
            if (i == 1) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fill3DRect(snake.getX(), snake.getY(), 15, 15, true);
        }
    }

    //蛇是否的碰撞
    private boolean snakeCrash() {
        Snake snake = snakes.get(0);
        if (!(snake.getY() > 0 && snake.getX() < 1000 - 20 && snake.getY() < 750 - 20 && snake.getX() > 0)) {
            snakes.clear(); //清除上一次的游戏记录
            isStart = false;
            isLiver = false;
            score = 0;
            Snake.snakeLength = 3;
            snakes.add(new Snake(500, 400)); // 第一截(会覆盖)
            snakes.add(new Snake(500, 400));
            for (int i = 1; i < Snake.snakeLength; i++) {
                Snake snake1 = new Snake(500, 400 + i * 15);
                snakes.add(snake1);
            }
            return true;
        }
        return false;
    }

    //蛇的移动
    public void snakeRun() {
        if (snakeCrash()) { //判断碰撞
            isStart = false;    //游戏结束
            isLiver = false;    //蛇死亡
            return;
        }
        Snake snake = snakes.get(0);
        switch (direct) {
            case 'U': //向上
                snake.setY(snake.getY() - 15);
                break;
            case 'R': //向右
                snake.setX(snake.getX() + 15);
                break;
            case 'D': //向下
                snake.setY(snake.getY() + 15);
                break;
            case 'L': //向左
                snake.setX(snake.getX() - 15);
                break;
        }
        //后一截身体位置 等于 前一截身体的位置
        for (int i = snakes.size() - 1; i > 0; i--) {
            Snake snake_q = snakes.get(i);  //后一个位置
            Snake snake_h = snakes.get(i - 1);  //前一个位置
            snake_q.setX(snake_h.getX());
            snake_q.setY(snake_h.getY());
        }
    }

    //绘制食物
    public void snakeFood(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fill3DRect(food.getX(), food.getY(), 10, 10, false); //食物
        Snake snake = snakes.get(0);
        int centerX = (int) Math.abs((snake.getY() + 7.5) - (food.getY() + 5)); //x圆心距
        int centerY = centerY = (int) Math.abs((snake.getX() + 7.5) - (food.getX() + 5)); //x圆心距
        if (centerX <= 12.5 && centerY <= 12.5) {
            food.setLive(false);
            Food.foodnum++; //食物的数量
            score++; //得分
            food.setX((int) (Math.random() * 800 + 100));
            food.setY((int) (Math.random() * 500 + 100));
        }
        if (food.foodnum == 2) {
            food.foodnum = 0;
            Snake.snakeLength++;
            snakes.add(new Snake(500, 400));
        }
    }

    //得分绘制
    public void snakeScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(1000, 0, 200, 750);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.setColor(Color.BLACK);
        g.drawString("本局得分:" + score, 1030, 50);
        g.drawString("本局长度:" + Snake.snakeLength, 1030, 100);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isStart == false) {
                isStart = true;    //游戏开始
                isLiver = true;
            } else {
                isStart = false;   //游戏暂停
            }
        }
        if (isLiver == true) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                direct = 'U';   //向上移动
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                direct = 'R';   //向右移动
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                direct = 'D';   //向下移动
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                direct = 'L';   //向左移动
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
