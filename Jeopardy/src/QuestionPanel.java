import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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

    // The topic and value of the question
    JLabel qInfo = new JLabel(qObj.getTopic() + " - $" + qObj.getValue());
    qInfo.setFont(GameUtils.TITLE_FONT);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.weighty = 0.15;
    gbc.anchor = GridBagConstraints.CENTER;
    this.add(qInfo, gbc);

    // The text of the question
    JTextPane qText = new JTextPane();
    qText.setText(qObj.getQuestion());
    qText.setFont(GameUtils.GAME_FONT);

    // Centre the question
    StyledDocument doc = qText.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);

    qText.setEditable(false);
    qText.setOpaque(false);

    gbc = new GridBagConstraints(); // Reset the constraints
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    gbc.weighty = 0.55;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    this.add(qText, gbc);


    // The answer buttons
    this.answers = new JButton[4];
    for (int i = 0; i < 4; i++) {
      answers[i] = new JButton(question.getAnswers()[i]);
      answers[i].addActionListener(this);
    }

    // The answer buttons
    JPanel answersPnl = new JPanel(new GridLayout(2, 2));
    for (int i = 0; i < 4; i++) {
      answers[i].setPreferredSize(new Dimension(400, 70));
      answersPnl.add(answers[i]);
    }
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 0.3;
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
    	game.incrementQuestionsAsked();//increments amount of questions asked
    	current.addDollars(this.qObj.getValue()); // Add money to the current player
        game.updateDollars(); // Update the dollar amount in the sidebar
        
     // Tell the user they were correct and their new balance
        JOptionPane.showMessageDialog(game,
                                      current.getName() + " now has $" + current.getDollars(),
                                      "Correct",
                                      JOptionPane.INFORMATION_MESSAGE);
        
    	if(game.getQuestionsAsked() == 30){//if all questions have been asked, determine the winner
    		game.unboldScoreboard();
        	EndPanel endDisplay = new EndPanel(game.players);
            game.questionArea.add(endDisplay);
        	CardLayout cl = (CardLayout) game.questionArea.getLayout();
        	cl.last(game.questionArea);
        }
    	else{
      CardLayout cl = (CardLayout)(game.questionArea.getLayout());
      cl.first(game.questionArea); // Go to the panel in the question area (the button grid)
    	}

      

     
    }
    else {
      answers[index].setEnabled(false); // Disable the answer chosen
      current.addDollars(-this.qObj.getValue()); // Subtract the value of the question or the amount the user wagered if this is a daily double
      game.updateDollars();
      game.incrementTurn(); // Move on to the next player

      if (this.qObj.getDailyDouble()) { // Only one player gets to guess on a daily double
        // Only one guess, so show the correct answer
        JOptionPane.showMessageDialog(game,
                                      "Incorrect. The correct answer was " + this.qObj.getAnswers()[this.qObj.getCorrect()]);

        // Go back to the grid of questions
        CardLayout cl = (CardLayout)(game.questionArea.getLayout());
        cl.first(game.questionArea);
      } else if (guesses == 3) { // If all the players get the question wrong
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
