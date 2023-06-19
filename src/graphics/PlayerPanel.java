package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerPanel extends JPanel {
    private final ArrayList<Player> players;

    JButton exitButton;


    public PlayerPanel(ArrayList<Player> records) {
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

        exitButton = new JButton("Exit");


        add(exitButton, BorderLayout.SOUTH);
    }

    public int getMinimum(){

        Collections.sort(players);
        int i = Math.min(players.size() - 1, 9);
        return i < 9 ? 0 : players.get(i).getScore();
    }
    public void setActionL(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
}
