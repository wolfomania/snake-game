package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyPanelRenderer extends DefaultTableCellRenderer {

    private static final BufferedImage image = getImage();

    private static final JPanel backGroundPanel = getBackGroundPanel();

    private static final JPanel snakeHeadPanel = getSnakeHeadPanel();

    private static final JPanel snakeBodyPanel = getSnakeBodyPanel();

    private static final JPanel applePanel = getApplePanel();


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        int intValue = (int)value;
        switch (intValue) {
            case 1:
                return snakeHeadPanel;
            case 2:
                return snakeBodyPanel;
            case 3:
                return applePanel;
            default:
                break;
        }
        return backGroundPanel;
    }

    private static BufferedImage getImage() {
        try {
            return ImageIO.read(new File("src/graphics/apple.png"));
        } catch (IOException e) {
            System.err.println("Can't load this image");
        }
        return null;
    }

    private static JPanel getBackGroundPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        return panel;
    }


    private static JPanel getSnakeHeadPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        return panel;
    }

    private static JPanel getSnakeBodyPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        return panel;
    }

    private static JPanel getApplePanel() {
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(image != null) {
                    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
                }
            }
        };
        panel.setBackground(Color.BLACK);
        return panel;
    }

}
