package GluttonousSnake;

import javax.swing.*;

@SuppressWarnings({"all"})
public class gluttonousSnake extends JFrame {
    MyPanel mp = null;

    public static void main(String[] args) {
        new gluttonousSnake();
    }

    public gluttonousSnake() {
        mp = new MyPanel();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1200, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(mp).start();
    }
}
