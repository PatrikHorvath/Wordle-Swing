package game;

import java.awt.event.*;

public class Adapter implements MouseListener, MouseMotionListener, KeyListener, ActionListener, FocusListener {
    public void mouseClicked(MouseEvent e) {};
    public void mousePressed(MouseEvent e) {};
    public void mouseReleased(MouseEvent e) {};
    public void mouseEntered(MouseEvent e) {};
    public void mouseExited(MouseEvent e) {};
    public void mouseDragged(MouseEvent e) {};
    public void mouseMoved(MouseEvent e) {};
    public void keyTyped(KeyEvent e) {};
    public void keyPressed(KeyEvent e) {};
    public void keyReleased(KeyEvent e) {};
    public void actionPerformed(ActionEvent e) {};
    public void focusGained(FocusEvent e) {};
    public void focusLost(FocusEvent e) {};
}
