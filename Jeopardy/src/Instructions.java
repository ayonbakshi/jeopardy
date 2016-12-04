import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridLayout;

public class Instructions extends JPanel implements ActionListener {
  public Instructions() {
    super(new GridLayout(1, 2));

    JTextArea helpText = new JTextArea("Jeopardy is a quiz-show style game with three contestants. The game " +
                                       "board consists of a grid of questions with 6 topics and 5 questions " +
                                       "per topic with values $200, $400, $600, $800, $1000. Players choose " +
                                       "a question, then are presented with the answer and must select the " +
                                       "correct question that goes with the answer. If the player guesses " +
                                       "correctly, they are awarded the value of the question. If the " +
                                       "player guesses incorrectly, the next player gets a chance to " +
                                       "guess. If no player guesses correctly, they are shown the answer " +
                                       "and nobody gets money. After a question has been played, it is " +
                                       "disabled. The last correct answerer chooses the next question. " +
                                       "\n\n" +
                                       "One question in the game is a daily double. The player who selects " +
                                       "the daily double can wager any amount of money up to their current " +
                                       "balance or $1000, whichever is larger. Only the player who selects " +
                                       "the daily double gets to guess. If the player is correct, they gain " +
                                       "the amount they wagered. If they are incorrect, they lose that " +
                                       "amount. " +
                                       "\n\n" +
                                       "The game ends when all the questions have been played. The player" +
                                       "with the largest amount of money at the end of the game wins.");
    helpText.setEditable(false); // The help text isn't editable
    this.add(helpText);

    JButton backButton = new JButton("Back");
    backButton.addActionListener(this);
    this.add(backButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
