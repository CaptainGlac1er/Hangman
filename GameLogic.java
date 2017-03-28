import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 3/16/13
 */
public class GameLogic {
    private static List<String> Words = new ArrayList<String>();
    private static String randomWord;
    private static String buildingWord = "";


    public GameLogic() {
        Words.add("TEST");
        try {
            System.out.println(getClass().getResource("dict.txt")/*.toURI()*/);
            Scanner importFile = new Scanner(new File(getClass().getResource("dict.txt").toURI()));
            while (importFile.hasNext())
                Words.add(importFile.next().toUpperCase());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        randomWord = getNewWord();
        for (int i = 0; i < randomWord.length(); i++)
            buildingWord += "_";
    }

    public void reset() {
        randomWord = getNewWord();
        buildingWord = "";
        for (int i = 0; i < randomWord.length(); i++)
            buildingWord += "_";
    }

    /// Inputs the letter into ////
    /// the letters you have   ////
    /// gotten correct         ////
    public boolean inputLetter(char input) {
        if (randomWord.indexOf(input) != -1)
            for (int i = randomWord.indexOf(input); i < randomWord.length(); i = randomWord.indexOf(input, i + 1))
                if (i != -1)
                    buildingWord = buildingWord.substring(0, i) + input + buildingWord.substring(i + 1, randomWord.length());
                else
                    break;
        else
            return false;
        return true;

    }

    //// Gets the letters that ///
    //// you have gotten right ///
    public String getBuildingWord() {
        String output = "";
        if (buildingWord.contains("GAME OVER"))
            return buildingWord;
        for (int i = 0; i < buildingWord.length(); i++)
            output += buildingWord.charAt(i) + " ";
        return output;
    }

    public boolean isComplete() {
        return randomWord.equals(buildingWord);
    }

    /// Picks a random word ////
    public String getNewWord() {
        String Word = Words.get((int) (Math.random() * Words.size()));
        while (Word.length() > 7)
            Word = Words.get((int) (Math.random() * Words.size()));
        return Word;
    }

    public void gameOver() {
        buildingWord = randomWord;
    }
}
