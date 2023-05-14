package game;

import java.awt.*;
import javax.swing.*;

public class ExitButton extends JPanel {
    private static final int SIZE = 30;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color OUTLINE_COLOR = Color.RED;
    private static final Color X_COLOR = Color.RED;

    public ExitButton() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBorder(null);
        setBackground(BACKGROUND_COLOR);
        setFocusable(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(OUTLINE_COLOR);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(5, 5, SIZE - 10, SIZE - 10, 8, 8);

        g2.setColor(X_COLOR);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(12, 12, SIZE - 12, SIZE - 12);
        g2.drawLine(SIZE - 12, 12, 12, SIZE - 12);
    }
}
