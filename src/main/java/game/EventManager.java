package game;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

public class EventManager extends Adapter{
    private final Logic logic;
    private Point mouseOffset;
    private int timerCount = 0;

    public EventManager(Logic logic) {
        this.logic = logic;
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        var source = e.getSource();
        if(source instanceof ExitButton) {
            System.exit(0);
        } else if (source instanceof Key) {
            Key key = (Key) e.getSource();
            String keyText = key.getText();
            if (keyText.equals("ENTER")) {
                logic.checkWord();

            } else if (keyText.equals("DELETE")) {
                logic.removeLetter();

            } else {
                logic.addLetter(keyText.charAt(0));
            }
        }
        else if (source instanceof JPanel) {
            mouseOffset = e.getPoint();       
    }
}
    @Override
    public void mouseDragged(MouseEvent e) {
        Point newLocation = e.getLocationOnScreen();
        newLocation.translate(-mouseOffset.x, -mouseOffset.y);
        logic.moveWindow(newLocation);
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() instanceof Window) {
            Window window = (Window) e.getSource();
            window.requestFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        char keyCode = event.getKeyChar();
        if (keyCode == KeyEvent.VK_ENTER) {
            logic.checkWord();

        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            logic.removeLetter();

        } else if (Character.isLetter(keyCode)) {
            char keyChar = Character.toUpperCase(keyCode);
            logic.addLetter(keyChar);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            Timer timer = (Timer) e.getSource();
            timerCount++;
            if (timerCount == 2) {
                timer.stop();
                timerCount = 0;
                logic.restartGame();
            }
        }
    }
}
