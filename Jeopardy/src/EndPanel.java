import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * End screen for the Jeopardy game. This screen reports which player
 * won and what the final balance for each player was.
 */
public class EndPanel extends JPanel {
  /**
   * Create the panel to display at the end of the game.
   *
   * @param players the players from the Jeopardy game
   */
  public EndPanel(Player[] players) {
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    ArrayList<Integer> winners;

    JLabel title = new JLabel(new ImageIcon(GameUtils.findImage("title.png")));
    this.add(Box.createVerticalStrut(200));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(title);

    JLabel winner;
    winners = determineWinner(players);
    if (winners.size() == 1) {
      winner = new JLabel("Player " + (winners.get(0) + 1) + " has won");
    } else if (winners.size() == 2) {
      winner = new JLabel("Player " + (winners.get(0) + 1) + " and Player " + + (winners.get(1) + 1)  + " have tied");
    } else {
      winner = new JLabel("Player 1, Player 2 and Player 3 have tied");
    }
    winner.setFont(GameUtils.TITLE_FONT);
    this.add(Box.createVerticalStrut(100));
    winner.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(winner);

    JLabel message = new JLabel("Thanks for playing!");
    message.setFont(GameUtils.TITLE_FONT);
    this.add(Box.createVerticalStrut(30));
    message.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(message);
  }

  /**
   * Determine the winner of the Jeopardy game. If there was a tie,
   * return all the players in the tie.
   *
   * @param players the players in the Jeopardy game
   * @return the player(s) who won or tied for first
   */
  public ArrayList<Integer> determineWinner(Player[] players) {
    ArrayList<Integer> winners = new ArrayList<Integer>();
    if (players[0].getDollars() == players[1].getDollars() && players[0].getDollars() == players[2].getDollars() && players[1].getDollars() == players[2].getDollars()) {
      winners.add(0);
      winners.add(1);
      winners.add(2);
    } else if (players[0].getDollars() == players[1].getDollars() && players[1].getDollars() > players[2].getDollars()) {
      winners.add(0);
      winners.add(1);
    } else if (players[0].getDollars() == players[2].getDollars() && players[2].getDollars() > players[1].getDollars()) {
      winners.add(0);
      winners.add(2);
    } else if (players[2].getDollars() == players[1].getDollars() && players[1].getDollars() > players[0].getDollars()) {
      winners.add(1);
      winners.add(2);
    } else {
      int winner = 0;
      for (int i = 1; i < 3; i++) {
        if(players[i].getDollars() > players[winner].getDollars()) {
          winner = i;
        }
      }

      winners.add(winner);
    }

    return winners;
  }
}
