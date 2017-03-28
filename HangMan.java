import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 3/14/13
 */
public class HangMan extends JFrame implements ActionListener {
    private static int textSize = 25;
    private static JButton reset = new JButton("RESTART");
    private static JLabel Word = new JLabel("");
    private static JPanel Game = new JPanel();
    private static Player gamePlayer = new Player();

    //// Button ArrayList /////
    private static ArrayList<JButton> inputButtons = new ArrayList<JButton>(100);

    private static GameLogic logic;


    /////// Main Method ///////
    public static void main(String[] args) {
        new HangMan();
        logic = new GameLogic();
        Word.setText(logic.getBuildingWord());
    }

    public HangMan() {
        /////// Form Settings /////////
        setSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
        setTitle("HangMan");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        //////// JPanels /////////
        JPanel Body = new JPanel();
        JPanel Game = new JPanel();
        JPanel GameStatus = new JPanel();

        /////// JPanel Specs /////
        GameStatus.setMaximumSize(new Dimension(320, 50));
        GameStatus.setBackground(Color.GRAY);
        gamePlayer.setBackground(new Color(106, 142, 25));
        gamePlayer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 9, 2, 9), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

        /////// Layout stuff /////
        BoxLayout GameLayout = new BoxLayout(Game, BoxLayout.Y_AXIS);
        BoxLayout BodyLayout = new BoxLayout(Body, BoxLayout.X_AXIS);
        Game.setLayout(GameLayout);
        Body.setLayout(BodyLayout);

        ////// Setup Buttons /////
        Word.setFont(new Font("Arial", Font.PLAIN, textSize));
        Word.setBounds(0, 20, 0, 20);
        reset.addActionListener(this);

        ////// Adding Stuff /////
        Game.add(addInputButtons());
        GameStatus.add(Word);
        GameStatus.add(Box.createHorizontalStrut(10));
        GameStatus.add(reset);
        GameStatus.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        Game.add(GameStatus);
        Body.add(Game);
        Body.add(gamePlayer);

        add(Body);
    }


    /////// Adds the Alphabet //
    private JPanel addInputButtons() {
        ///// JPanel Specs ////
        JPanel inputStuff = new JPanel();
        inputStuff.setMaximumSize(new Dimension(320, 1200));
        inputStuff.setLayout(new GridLayout(5, 4, 3, 3));
        inputStuff.setBackground(new Color(20, 84, 204));
        inputStuff.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ///// Adds the Stuff //
        for (int letter = 'A'; letter <= 'Z'; letter++)
            inputButtons.add(new JButton((char) letter + ""));
        for (JButton button : inputButtons) {
            button.setBackground(Color.WHITE);
            button.addActionListener(this);
            inputStuff.add(button);
        }
        return inputStuff;
    }


    public void gameOver() {
        Game.removeAll();
        Word.setFont(new Font("Arial", Font.PLAIN, 16));
        for (JButton button : inputButtons) {
            button.removeActionListener(this);
            button.setBackground(Color.WHITE);
        }
        revalidate();
        if(logic.isComplete())
            JOptionPane.showMessageDialog(this, "Correct! Good Job", "Hangman", 1);
        else
            JOptionPane.showMessageDialog(this, "Game Over", "Hangman", 1);
        logic.gameOver();
    }

    public void reset() {
        Game.removeAll();
        gamePlayer.reset();
        logic.reset();
        Word.setFont(new Font("Arial", Font.PLAIN, textSize));
        Word.setText(logic.getBuildingWord());

        for (JButton button : inputButtons) {
            button.addActionListener(this);
            button.setBackground(Color.WHITE);
        }

        repaint();
        //revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            reset();
            return;
        }
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).getBackground().equals(Color.WHITE)) {
            if (logic.inputLetter(((JButton) e.getSource()).getText().charAt(0)))
                ((JButton) e.getSource()).setBackground(new Color(20, 178, 55));
            else {
                ((JButton) e.getSource()).setBackground(new Color(178, 1, 20));
                gamePlayer.loseLife();
            }
            if (gamePlayer.isDead() || logic.isComplete())
                gameOver();
            gamePlayer.repaint();

            Word.setText(logic.getBuildingWord());
        }
    }
}

