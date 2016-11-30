
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuestionPanel extends JPanel implements ActionListener{
  private JButton[] answers;
  private JLabel question, blank;
  private Question qObj;
  private GridBagConstraints gbc = new GridBagConstraints();
  private Jeopardy game;
  private int guesses;
  private JPanel answersPnl;

  public QuestionPanel(Question question, Jeopardy game) {
    this.game = game;
    this.guesses = 0;

    qObj = question;
    this.setPreferredSize(new Dimension(600, 500));
    this.setLayout(new GridBagLayout());

    this.setSize(new Dimension(600, 430));

    this.answers = new JButton[4];
    for (int i = 0; i < 4; i++){
      answers[i] = new JButton(question.getAnswers()[i]);
      answers[i].addActionListener(this);
    }

    
    this.question = new JLabel(question.getQuestion());
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.weighty = 0.5;
    gbc.weightx = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    add(this.question, gbc);
    
    answersPnl = new JPanel();
    answersPnl.setLayout(new GridLayout(2,2));
    for (int i = 0; i < 4; i++){
    	answers[i].setPreferredSize(new Dimension(300, 60));
    	answersPnl.add(answers[i]);
    }
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    this.add(answersPnl, gbc);
    
  }

  public void actionPerformed (ActionEvent e){
    int index = 0;

    for (int i = 0; i < 4; i++){
      if (answers[i] == (JButton)(e.getSource())){
        index = i;
      }
    }

    Player current = game.players[game.getTurn()];
    guesses++;

    if(qObj.checkGuess(index)){
      //increment $$ here
      //display amount earned and balance
      CardLayout cl = (CardLayout)(game.questionArea.getLayout());
      cl.first(game.questionArea);

      current.addDollars(qObj.getValue());
      game.updateDollars();
      JOptionPane.showMessageDialog(game, current.getName() + " now has $" + current.getDollars(), "Correct", JOptionPane.INFORMATION_MESSAGE);
      // TODO custom check icon
    }
    else {
      //blackout the answer chosen if answer is wrong
    	if(qObj.getDailyDouble()){
    		JOptionPane.showMessageDialog(game, "Incorrect. The correct answer was " + qObj.getAnswers()[qObj.getCorrect()]);
            CardLayout cl = (CardLayout)(game.questionArea.getLayout());
            cl.first(game.questionArea);
    	}
      answers[index].setEnabled(false);
      game.incrementTurn();
       if (guesses == 3) {
        JOptionPane.showMessageDialog(game, "Incorrect. The correct answer was " + qObj.getAnswers()[qObj.getCorrect()]);
        CardLayout cl = (CardLayout)(game.questionArea.getLayout());
        cl.first(game.questionArea);
       } else {
         current = game.players[game.getTurn()];
         JOptionPane.showMessageDialog(game, "Incorrect. It is now " + current.getName() + "'s turn.", "Incorrect", JOptionPane.INFORMATION_MESSAGE);
       }
    }

  }

}
