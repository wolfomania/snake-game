package graphics;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private int score;

    public ScorePanel() {
        score = 1;
        setSize(new Dimension(300, 100));
        setMinimumSize(new Dimension(200, 50));
    }

    public void setScore(int score) {
        this.score = score;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            String scoreText = "Score: " + score;
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20));
            g.drawString(scoreText, 0, 20);
    }

    public int getScore() {
        return score;
    }
}
