package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import com.google.common.io.Resources;

import lombok.Getter;

public class Logic {
    private List<LetterBox> letterBoxes;
    private Map<Character, Key> keyMap;
    private Window window;
    private int currentLetter;
    private int currentRow;
    private String chosenWord;
    private boolean gameWon;
    private Timer timer;
    @Getter
    private final EventManager eventManager = new EventManager(this);

    public Logic(List<LetterBox> letterBoxes, Window window) {
        this.letterBoxes = letterBoxes;
        timer = new Timer(1000, eventManager);
        timer.addActionListener(eventManager);

        this.window = window;
        chooseNewWord();
    }

    public void setKeyMap(Map<Character, Key> keyMap) {
        this.keyMap = keyMap;
    }

    public void chooseNewWord() {
        try {
            URL url = Resources.getResource("words.txt");
            String text = Resources.toString(url, StandardCharsets.UTF_8);
            String[] words = text.split("\n");
            chosenWord = words[(int) (Math.random() * words.length)];
            chosenWord = chosenWord.trim().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addLetter(char letter) {
        if (currentLetter < letterBoxes.size() && currentLetter < currentRow * 5 + 5 && !gameWon) {
            letterBoxes.get(currentLetter).setLetter(letter);
            currentLetter++;
        }
    }

    public void removeLetter() {
        if (currentLetter > 0 && currentLetter > currentRow * 5 && !gameWon) {
            currentLetter--;
            letterBoxes.get(currentLetter).setEmpty();
        }
    }

    public void checkWord() {
        if (currentLetter == currentRow * 5+5) {
            URL url = Resources.getResource("words.txt");
            try {
                String word = lettersToString();
                String text = Resources.toString(url, StandardCharsets.UTF_8);

                if (text.contains(word.toLowerCase())) {
                    letterCorrectness();
                    currentRow++;
                    if (word.equals(chosenWord)) {
                        gameWon = true;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (currentRow > 5 || gameWon) {
                window.displayPrevious(chosenWord);
                timer.start();
            }
        }
    }

    private void letterCorrectness() {
        for (int i = currentRow*5; i < currentRow*5+5; i++) {
            if (letterBoxes.get(i).getLetter() == chosenWord.charAt(i%5)) {
                letterBoxes.get(i).setCorrect();
                keyMap.get(letterBoxes.get(i).getLetter()).setCorrect();
            } else if (chosenWord.contains("" + letterBoxes.get(i).getLetter())) {
                int sameLetterCount = 0;
                for (char c : chosenWord.toCharArray()) {
                    if (c == letterBoxes.get(i).getLetter()) {
                        sameLetterCount++;
                    }
                }

                int previousLetterCount = 0;
                for (int j = currentRow*5; j < i; j++) {
                    if (letterBoxes.get(j).getLetter() == letterBoxes.get(i).getLetter()) {
                        previousLetterCount++;
                    }
                }

                if (sameLetterCount > previousLetterCount) {
                    letterBoxes.get(i).setPresent();
                    keyMap.get(letterBoxes.get(i).getLetter()).setPresent();
                } else {
                    letterBoxes.get(i).setAbsent();
                }
            } else {
                letterBoxes.get(i).setAbsent();
                keyMap.get(letterBoxes.get(i).getLetter()).setAbsent();
            }
        }
    }

    public void restartGame() {
        currentLetter = 0;
        currentRow = 0;
        chooseNewWord();
        gameWon = false;
        for (LetterBox letterBox : letterBoxes) {
            letterBox.setEmpty();
        }
        for (Key key : keyMap.values()) {
            key.resetColor();
        }
    }

    private String lettersToString() {
        String word = "";
        for (int i = currentRow*5; i < currentRow*5+5; i++) {
            word += letterBoxes.get(i).getLetter();
        }
        return word;
    }

    public void moveWindow(Point point) {
            int x = point.x;
            int y = point.y;
        
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
            if (x < 0) {
                x = 0;
            }
        
            if (x + window.getSize().width > screenSize.width) {
                x = screenSize.width - window.getSize().width;
            }
        
            if (y < 0) {
                y = 0;
            }
        
            if (y + window.getSize().height > screenSize.height) {
                y = screenSize.height - window.getSize().height;
            }
        
            window.setLocation(x, y);
    }
}
