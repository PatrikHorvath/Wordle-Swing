package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LetterBox extends JPanel {
    public final int OFFSET = 5;
    private JLabel label;
    private Color boxColor = null;
    public LetterBox(Color backgroundColor) {
        this.setPreferredSize(new Dimension(75, 75));
        this.setBackground(backgroundColor);
    
        label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(35.0f));
        label.setHorizontalAlignment(SwingConstants.CENTER); 
        label.setOpaque(false); 
    
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.add(Box.createHorizontalGlue());
        horizontalPanel.add(label);
        horizontalPanel.add(Box.createHorizontalGlue());
        horizontalPanel.setOpaque(false);
    
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue()); 
        this.add(horizontalPanel); 
        this.add(Box.createVerticalGlue());
        this.setFocusable(false);
    }
    
    public void setLetter(char letter) {
        label.setText("" + letter);
        boxColor = null;
    }
    public char getLetter() {
        return label.getText().charAt(0);
    }
    
    public void setEmpty() {
        label.setText("");
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


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (boxColor != null) {
            g2.setColor(boxColor);
            g2.fillRect(OFFSET, OFFSET, getWidth()-2*OFFSET, getHeight()-2*OFFSET);
        } else {
            g.setColor(Colors.OUTLINE_COLOR);
            g2.drawRect(OFFSET, OFFSET, getWidth()-2*OFFSET, getHeight()-2*OFFSET);
        }
    }
}
