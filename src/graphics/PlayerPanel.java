package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerPanel extends JPanel {
    private final ArrayList<Player> players;

    private final JPanel buttonPanel;

    private final JButton exitButton;

    private final JButton restartGameButton;

    protected PlayerPanel(ArrayList<Player> records) {
        this.players = records;

        setLayout(new BorderLayout());

        JPanel personDisplayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Collections.sort(players);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                int i = 0;
                g.drawString("TOP RECORDS:", 200, (i + 1) * 25);
                i++;
                g.setFont(new Font("Arial", Font.PLAIN, 12));

                for (; i < Math.min(players.size() + 1, 11); ++i) {
                    String personDetails = "Name: " + players.get(i - 1).getName() + ", Score: " + players.get(i - 1).getScore();
                    g.drawString(personDetails, 200, (i + 1) * 25);
                }
            }
        };

        add(personDisplayPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());

        exitButton = new JButton("Exit");

        restartGameButton = new JButton("Back to menu");

        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    protected int getMinimum(){

        Collections.sort(players);
        int i = Math.min(players.size() - 1, 9);
        return i < 9 ? 0 : players.get(i).getScore();
    }


    protected void setActionOnExit(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    protected void setActionOnRestart(ActionListener listener){
        restartGameButton.addActionListener(listener);
    }

    protected void setEndGamePanel() {
        buttonPanel.remove(exitButton);
        buttonPanel.add(restartGameButton);
    }

    protected void setMenuPanel() {
        buttonPanel.add(exitButton);
        buttonPanel.remove(restartGameButton);
    }

}
