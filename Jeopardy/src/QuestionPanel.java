import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The panel that displays a single question and its answers.
 *
 * This class handles displaying the question, checking answers,
 * updating player money, and changing the turn.
 */
public class QuestionPanel extends JPanel implements ActionListener {
  /**
   * The buttons with the answers to the question
   */
  private JButton[] answers;

  /**
   * The question that is being displayed
   */
  private Question qObj;

  /**
   * The Jeopardy game that made this question
   *
   * This is a field of the QuestionPanel so that it can update the
   * turn and amount of money the players have.
   */
  private Jeopardy game;

  /**
   * The number of guesses the users have made.
   *
   * This is limited to three - one per player.
   */
  private int guesses;

  /**
   * Create a question panel with the question and answer buttons.
   *
   * @param question the Question object being displayed
   * @param game the Jeopardy game that this came from
   */
  public QuestionPanel(Question question, Jeopardy game) {
    this.game = game;
    this.guesses = 0;

    this.setPreferredSize(new Dimension(600, 500));
    this.setLayout(new GridBagLayout());
    this.setSize(new Dimension(600, 430));

    this.qObj = question;

    // The answer buttons
    this.answers = new JButton[4];
    for (int i = 0; i < 4; i++) {
      answers[i] = new JButton(question.getAnswers()[i]);
      answers[i].addActionListener(this);
    }

    // The text of the question
    JLabel qText = new JLabel(question.getQuestion());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.weighty = 0.5;
    gbc.weightx = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    this.add(qText, gbc);

    // The answer buttons
    JPanel answersPnl = new JPanel(new GridLayout(2, 2));
    for (int i = 0; i < 4; i++) {
      answers[i].setPreferredSize(new Dimension(300, 60));
      answersPnl.add(answers[i]);
    }

    gbc = new GridBagConstraints(); // Reset the constraints object
    gbc.gridx = 0;
    gbc.gridy = 1;
    this.add(answersPnl, gbc);
  }

  /**
   * Handle clicks on the answer buttons.
   *
   * @param e the action that occured
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    // Find the index of the answer chosen
    int index = 0;
    for (int i = 0; i < 4; i++) {
      if (answers[i] == (JButton)(e.getSource())) {
        index = i;
      }
    }

    Player current = game.players[game.getTurn()]; // Get the current player
    guesses++; // A guess was made, increment the number of guesses

    // Check the answer
    if (this.qObj.checkGuess(index)) { // If the answer was correct
      CardLayout cl = (CardLayout)(game.questionArea.getLayout());
      cl.first(game.questionArea); // Go to the panel in the question area (the button grid)

      current.addDollars(this.qObj.getValue()); // Add money to the current player
      game.updateDollars(); // Update the dollar amount in the sidebar

      // Tell the user they were correct and their new balance
      JOptionPane.showMessageDialog(game,
                                    current.getName() + " now has $" + current.getDollars(),
                                    "Correct",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      // Blackout the answer chosen if answer is wrong
      if(this.qObj.getDailyDouble()) { // Only one player gets to guess on a daily double
        // Only one guess, so show the correct answer
        JOptionPane.showMessageDialog(game,
                                      "Incorrect. The correct answer was " + this.qObj.getAnswers()[this.qObj.getCorrect()]);

        // Go back to the grid of questions
        CardLayout cl = (CardLayout)(game.questionArea.getLayout());
        cl.first(game.questionArea);
      }

      answers[index].setEnabled(false); // Disable the answer chosen
      game.incrementTurn(); // Move on to the next player

      if (guesses == 3) { // If all the players get the question wrong
        // All the players guessed, so show the correct answer
        JOptionPane.showMessageDialog(game,
                                      "Incorrect. The correct answer was " + this.qObj.getAnswers()[this.qObj.getCorrect()]);

        // Go back to the question grid
        CardLayout cl = (CardLayout)(game.questionArea.getLayout());
        cl.first(game.questionArea);
      } else {
        current = game.players[game.getTurn()]; // Get the current player

        // Inform the player they were incorrect and show who the next player is
        JOptionPane.showMessageDialog(game,
                                      "Incorrect. It is now " + current.getName() + "'s turn.", "Incorrect", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
}
