package boal;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class baol {
    private static final int BALL_SIZE = 20;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        BallPanel ballPanel = new BallPanel();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(ballPanel);
        frame.setVisible(true);

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballPanel.moveBall();
                ballPanel.repaint();
            }
        });
        timer.start();
    }

    static class BallPanel extends JPanel {
        int x = FRAME_WIDTH / 2;
        int y = FRAME_HEIGHT / 2;
        int xSpeed = 3;
        int ySpeed = 2;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.fillOval(x, y, BALL_SIZE, BALL_SIZE);
        }

        void moveBall() {
            if (x + xSpeed < 0 || x + xSpeed > getWidth() - BALL_SIZE) {
                xSpeed = -xSpeed;
            }
            if (y + ySpeed < 0 || y + ySpeed > getHeight() - BALL_SIZE) {
                ySpeed = -ySpeed;
            }
            x += xSpeed;
            y += ySpeed;
        }
    }
}
