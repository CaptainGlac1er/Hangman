import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 3/17/13
 */
public class Player extends JPanel {
    private int life; // Increases as the player gets wrong answers

    public Player() {
        setBackground(Color.WHITE);
        life = 0;
    }

    public void reset() {
        life = 0;
    }

    public void loseLife() {
        life++;
    }

    public boolean isDead() {
        return life == 6;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //////// Stand ///////
        graphics.setColor(new Color(70, 20, 20));
        graphics.fillRect(25, 50, 150, 10);
        graphics.fillRect(50, 25, 10, 300);
        graphics.fillRect(150, 50, 10, 30);
        graphics.fillRect(25, 275, 175, 10);
        graphics.fillRect(150, 275, 10, 50);
        for (int i = 0; i < 5; i++)
            graphics.drawLine(50, 25 + i, 75 - i, 50);
        //////// Body ////////
        graphics.setColor(Color.BLACK);
        switch (life) {
            case 6:
                graphics.drawLine(155, 205, 155 + 20, 205 + 20); // other leg
            case 5:
                graphics.drawLine(155, 205, 155 - 20, 205 + 20); // leg
            case 4:
                graphics.drawLine(155, 150, 155 + 20, 150 - 10); // other arm
            case 3:
                graphics.drawLine(155, 150, 155 - 20, 150 - 10); // arm
            case 2:
                graphics.drawRect(150 + 5, 130, 0, 75); // body
            case 1:
                graphics.drawOval(125 + 5, 80, 50, 50); // face
        }
        graphics.setColor(Color.WHITE);
    }
}
