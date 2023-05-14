package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;


public class Window extends JFrame {
    private Dimension windowSize;
    private JPanel mainBackground;
    private JLabel displayedWord;
    
    public Window() {
        this.setTitle("Wordle");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windowSize = new Dimension(600, 785);
        this.setPreferredSize(windowSize);
        this.setMinimumSize(windowSize);
        this.setFocusable(true);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, Colors.MENU_BLACK));

        displayedWord = new JLabel();
        displayedWord.setFont(displayedWord.getFont().deriveFont(20.0f));
        displayedWord.setHorizontalAlignment(SwingConstants.CENTER);
        displayedWord.setOpaque(false);
        displayedWord.setForeground(Colors.BOX_COLOR);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = screenSize.width / 2;
        int centerY = screenSize.height / 2;

        int windowX = centerX - windowSize.width / 2;
        int windowY = centerY - windowSize.height / 2;

        this.setLocation(windowX, windowY);

        mainBackground = new JPanel(new FlowLayout());
        mainBackground.setFocusable(false);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(getWidth(), 30));
        menuPanel.setBackground(Colors.MENU_BLACK);
        menuPanel.setFocusable(false);
        menuPanel.add(displayedWord, BorderLayout.WEST);

        this.add(mainBackground, BorderLayout.CENTER);
        this.add(menuPanel, BorderLayout.NORTH);
        mainBackground.setBackground(Colors.BACKGROUND_COLOR);

        ExitButton exitButton = new ExitButton();
        exitButton.setFocusable(false);
        
        menuPanel.add(exitButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new GridLayout(6,5));
        centerPanel.setFocusable(false);
        List<LetterBox> letterBoxes = new ArrayList<LetterBox>();
        for (int i = 0; i < 30; i++) {
            LetterBox letterBox = new LetterBox(Colors.BACKGROUND_COLOR);
            letterBoxes.add(letterBox);
            centerPanel.add(letterBox);
        }
        centerPanel.setBackground(Colors.BACKGROUND_COLOR);
        mainBackground.add(centerPanel);

        int topInset = 20;
        int leftInset = 0;
        int bottomInset = 20;
        int rightInset = 0;
        Border emptyBorder = BorderFactory.createEmptyBorder(topInset, leftInset, bottomInset, rightInset);
        centerPanel.setBorder(emptyBorder);

        Logic logic = new Logic(letterBoxes, this);
        exitButton.addMouseListener(logic.getEventManager());
        menuPanel.addMouseMotionListener(logic.getEventManager());
        menuPanel.addMouseListener(logic.getEventManager());
        this.addFocusListener(logic.getEventManager());
        this.addKeyListener(logic.getEventManager());

        JPanel keyRowOne = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel keyRowTwo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel keyRowThree = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        keyRowOne.setPreferredSize(new Dimension(10*55,75));
        keyRowTwo.setPreferredSize(new Dimension(10*55,75));
        keyRowThree.setPreferredSize(new Dimension(Math.round(10.5f*55),75));

        String[] letters = {"Q","W","E","R","T","Z","U","I","O","P",
                "A","S","D","F","G", "H","J","K","L",
                "ENTER","Y","X","C","V","B","N","M", "DELETE"};

        Map<Character,Key> keyMap = new HashMap<Character,Key>();

        for (int i = 0; i < 28; i++) {
            Key key = new Key(letters[i], Colors.BACKGROUND_COLOR);
            key.addMouseListener(logic.getEventManager());
            if (i < 10) {
                keyRowOne.add(key);
                keyMap.put(letters[i].charAt(0), key);
            } else if (i < 19) {
                keyRowTwo.add(key);
                keyMap.put(letters[i].charAt(0), key);
            } else {
                keyRowThree.add(key);
                if (i == 19 || i == 27) {
                    key.setWide();
                } else {
                    keyMap.put(letters[i].charAt(0), key);
                }
            }        
        }
        logic.setKeyMap(keyMap);

        JPanel keyboardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER ,0,0));
        keyboardPanel.setPreferredSize(new Dimension(getWidth(), 75*3));
        keyboardPanel.add(keyRowOne);
        keyboardPanel.add(keyRowTwo);
        keyboardPanel.add(keyRowThree);
        
        keyboardPanel.setBackground(Colors.BACKGROUND_COLOR);
        keyRowOne.setBackground(Colors.BACKGROUND_COLOR);
        keyRowTwo.setBackground(Colors.BACKGROUND_COLOR);
        keyRowThree.setBackground(Colors.BACKGROUND_COLOR);
        mainBackground.add(keyboardPanel);

        this.setUndecorated(true);
        this.pack();
        this.setVisible(true);
    }

    public void displayPrevious(String word) {
        displayedWord.setText(" Previous word: " + word);
    }
}