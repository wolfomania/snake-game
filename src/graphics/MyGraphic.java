package graphics;

import events.*;
import logic.Board;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyGraphic implements ScoreChangeListener, GameOverListener {

    private AbstractTableModel board;

    private DirectionChangeListener directionChangeListener;

    private DifficultySetterListener difficultySetterListener;

    private GameRestartListener gameRestartListener;

    private JPanel upper;

    private ScorePanel scorePanel;

    private JTable jTable;

    private JPanel menu;

    private JFrame jFrame;

    private ArrayList<Player> playersRecords;

    private PlayerPanel playerPanel;

    private ActionListener returnToMenuListener;

    public MyGraphic() {
        SwingUtilities.invokeLater(() -> {

            playersRecords = readPlayerScores("scores.txt");
            jFrame = new JFrame("Logic.Snake");
            jFrame.setSize(1000, 720);
            jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.setBackground(Color.BLACK);

            upper = new JPanel();
            upper.setLayout(new GridLayout());
            upper.setMinimumSize(new Dimension(200, 100));
            upper.setMaximumSize(new Dimension(1920, 100));
            upper.setBackground(new Color(211, 211, 211));

            scorePanel = new ScorePanel();
            scorePanel.setMinimumSize(new Dimension(200, 100));
            scorePanel.setBackground(new Color(211, 211, 211));
            scorePanel.setVisible(false);

            JPanel logoPanel = new JPanel(new GridBagLayout());

            JLabel logo = new JLabel("SNAKE GAME ");
            logo.setHorizontalAlignment(SwingConstants.CENTER);
            logo.setFont(new Font("Snake", Font.ITALIC, 30));
            logo.setBackground(new Color(211, 211, 211));

            logoPanel.setBackground(new Color(211, 211, 211));
            logoPanel.add(logo);

            upper.add(logoPanel);

            playerPanel = new PlayerPanel(playersRecords);
            returnToMenuListener = e1 -> {
                jFrame.remove(playerPanel);
                jFrame.setSize(1280, 720);
                jFrame.add(menu);
                jFrame.revalidate();
                jFrame.repaint();
            };
            playerPanel.setActionOnExit(returnToMenuListener);

            playerPanel.setActionOnRestart(e -> {
                playerPanel.setMenuPanel();
                fireRestartGame();
                jFrame.remove(playerPanel);
                jFrame.setSize(1280, 720);
                jFrame.add(menu);
                jFrame.revalidate();
                jFrame.repaint();
            });

            menu = new JPanel(new GridLayout(3,1,0,0));

            JButton startTheGame = new JButton("Start the game");
            startTheGame.addActionListener(e -> {
                startGame();
                scorePanel.setVisible(true);
                jTable.revalidate();
                jTable.repaint();
                jFrame.revalidate();
                jFrame.repaint();
            });
            startTheGame.setBackground(new Color(190, 190, 190));
            menu.add(startTheGame);

            JButton showLeaders = new JButton("Show top players");
            showLeaders.addActionListener(e -> {
                jFrame.remove(menu);
                jFrame.add(playerPanel);
                jFrame.setSize(500, 400);
                jFrame.revalidate();
                jFrame.repaint();
            });
            showLeaders.setBackground(new Color(175, 175, 175));
            menu.add(showLeaders);

            JButton exit = new JButton("Exit");
            exit.addActionListener(e -> System.exit(0));
            exit.setBackground(new Color(160, 160, 160));
            menu.add(exit);


            jTable = new JTable(16, 25);
            jTable.setBorder(BorderFactory.createEmptyBorder());
            jTable.setModel(board);
            jTable.setDefaultRenderer(JPanel.class, new MyPanelRenderer());
            jTable.setShowVerticalLines(false);
            jTable.setShowHorizontalLines(false);
            jTable.setIntercellSpacing(new Dimension(0, 0));
            jTable.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    // is not necessary
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            fireDirectionChange(2);
                            break;
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_D:
                            fireDirectionChange(3);
                            break;
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_S:
                            fireDirectionChange(0);
                            break;
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_A:
                            fireDirectionChange(1);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // // is not necessary
                }
            });

            jFrame.addComponentListener(new ComponentAdapter(){
                @Override
                public void componentResized(ComponentEvent e) {
                    int width = jFrame.getWidth();
                    int height = jFrame.getHeight() - upper.getHeight();
                    int cellSize = Math.min(width / jTable.getColumnCount(), height / jTable.getRowCount());

                    jTable.setRowHeight(cellSize);
                    for (int i = 0; i < jTable.getColumnCount(); i++) {
                        jTable.getColumnModel().getColumn(i).setWidth(cellSize);
                    }
                }
            });

            jFrame.add(upper);
            jFrame.setVisible(true);
            addMenu();
        });

    }

    public void init(Board board) {
        this.board = board;
        this.directionChangeListener = board;
        this.difficultySetterListener = board;
        this.gameRestartListener = board;
    }

    private void fireRestartGame(){
        gameRestartListener.restartGame();
    }

    protected void fireDirectionChange(int direction) {
        directionChangeListener.changeDirection(new DirectionEvent(this, direction));
    }

    private void addMenu(){

        jFrame.add(menu);
    }

    private void startGame(){
        if(changeDifficulty() == 1){
            jFrame.remove(menu);
            upper.add(scorePanel, 0);
            jFrame.add(jTable);
            jTable.revalidate();
            jTable.repaint();
            jTable.requestFocus();
        }
        jFrame.revalidate();
        jFrame.repaint();
    }

    @Override
    public void changeScore(ScoreEvent evt) {
        scorePanel.setScore(evt.getScore());
    }

    @Override
    public void endGame() {
        String name;
        if (scorePanel.getScore() < playerPanel.getMinimum()) {
            final JOptionPane optionPane = new JOptionPane("GAME-OVER\nYou are not in top 10", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            final JDialog dialog = new JDialog();
            dialog.setTitle("");
            dialog.setContentPane(optionPane);
            dialog.setUndecorated(true);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Sleep for the specified duration
                    dialog.dispose(); // Close the dialog after the delay
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } else {
            if(scorePanel.getScore() == board.getRowCount()*board.getColumnCount()){
                name = JOptionPane.showInputDialog(null, "Congratulation with finishing the game.\n Please enter your name", "Game Over", JOptionPane.PLAIN_MESSAGE);

            } else {
                name = JOptionPane.showInputDialog(null, "Game-Over.\n Please enter your name", "Game Over", JOptionPane.PLAIN_MESSAGE);
            }
            if(name == null || name.isEmpty())
                name = "NO-NAME";
            playersRecords.add(new Player(scorePanel.getScore(), name));
            appendPlayerScore(name, scorePanel.getScore(), "scores.txt");
        }
        jFrame.remove(jTable);
        playerPanel.setEndGamePanel();
        jFrame.add(playerPanel);
        jFrame.setSize(500, 400);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private int changeDifficulty() {
        String[] options = { "Noob", "Average", "Master", "Expert" };

        int selectedOption = JOptionPane.showOptionDialog(
                null,                        // Parent component (null for centering on screen)
                "Choose the difficulty:",         // Message to display
                "Option Dialog",             // Dialog title
                JOptionPane.DEFAULT_OPTION,  // Option type (default)
                JOptionPane.PLAIN_MESSAGE,   // Message type (plain)
                null,                        // Icon (null for default)
                options,                     // Array of options
                options[3]                   // Default option
        );

        if (selectedOption >= 0) {
            fireSetDifficultyEvent(selectedOption);
            return 1;
        } else {
            return 0;
        }
    }

    private static void appendPlayerScore(String playerName, int score, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename, true)) {

            int nameLength = playerName.length();
            fos.write(nameLength);

            //write 2 bytes characters
            for(char c : playerName.toCharArray()){
                byte[] bytes = new byte[]{(byte)(c >> 8), (byte)c};
                fos.write(bytes);
            }

            fos.write((score >> 24) & 0xFF);
            fos.write((score >> 16) & 0xFF);
            fos.write((score >> 8) & 0xFF);
            fos.write(score & 0xFF);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Player> readPlayerScores(String filename) {
        ArrayList<Player> players = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filename)) {
            while (fis.available() > 0) {

                int nameLength = fis.read();

                //Reading 2 bytes characters
                byte[] nameBytes = new byte[nameLength * 2];
                fis.read(nameBytes);
                char[] chars = new char[nameBytes.length / 2];
                for (int i = 0, j = 0; i < nameBytes.length; i+=2, j++) {
                    chars[j] = (char)((nameBytes[i] << 8) | (nameBytes[i + 1] & 0xff));
                }

                String playerName = new String(chars);

                // Read the score as 4 bytes in big-endian order
                int score = (fis.read() << 24) | (fis.read() << 16) | (fis.read() << 8) | fis.read();

                Player player = new Player(score, playerName);
                players.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    private void fireSetDifficultyEvent(int difficulty){
        difficultySetterListener.setDifficulty(new DifficultyEvent(this, difficulty));
    }

}
