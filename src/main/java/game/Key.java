package game;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Key extends JPanel {
    public final int WIDTH = 55;
    public final int HEIGHT = 75;
    public final int OFFSET = 3;
    private Color boxColor = null;

    private JLabel label;

    public Key(String letter, Color backgroundColor) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel(letter);
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(25.0f));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(false);
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.add(Box.createHorizontalGlue());
        horizontalPanel.add(label);
        horizontalPanel.add(Box.createHorizontalGlue());
        horizontalPanel.setOpaque(false);
        this.add(Box.createVerticalGlue());
        this.add(horizontalPanel);
        this.add(Box.createVerticalGlue());
        this.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
        this.setFocusable(false);
        this.setBackground(backgroundColor);
    }

    public void resetColor() {
        boxColor = null;
        repaint();
    }

    public void setCorrect() {
        boxColor = Colors.CORRECT_COLOR;
        repaint();
    }

    public void setPresent() {
        boxColor = Colors.PRESENT_COLOR;
        repaint();
    }

    public void setAbsent() {
        boxColor = Colors.ABSENT_COLOR;
        repaint();
    }
    
    public String getText() {
        return label.getText();
    }

    public void setWide() {
        this.setPreferredSize(new java.awt.Dimension(Math.round(WIDTH*1.5f), HEIGHT));
        label.setFont(label.getFont().deriveFont(15.0f));
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new java.awt.BasicStroke(3));
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        if (boxColor != null) {
            g2.setColor(boxColor);
        } else {
            g2.setColor(Colors.BOX_COLOR);
        }

        g2.fillRoundRect(OFFSET, OFFSET, getWidth() - 2 * OFFSET, getHeight() - 2 * OFFSET, 5, 5);
    }
}
