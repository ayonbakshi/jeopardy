import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Display the Jeopardy title card and get the names of the players.
 */
public class StartPanel extends JPanel implements ActionListener {
  /**
   * The text fields where the users enter their names.
   */
  JTextField[] playerFields;

  /**
   * Create a new start panel containing the title and three text
   * fields to get the player names.
   */
  public StartPanel() {
    this.setLayout(new GridBagLayout());

    // Start/help buttons
    JPanel btnPanel = new JPanel();
    JButton helpBtn = new JButton("Instructions");

    // Try to set up the instructions. It isn't a fatal error if the
    // instructions file can't be found, so inform the user, disable
    // the instructions button, and move on.
    try {
      helpBtn.addActionListener(new Instructions());
    } catch (FileNotFoundException fnfe) {
      JOptionPane.showMessageDialog(this, "Can't find instructions file.", "Error", JOptionPane.ERROR_MESSAGE);
      helpBtn.setEnabled(false);
    }

    JButton startBtn = new JButton("Start!");
    startBtn.addActionListener(this);

    btnPanel.add(helpBtn);
    btnPanel.add(startBtn);

    // Title
    JLabel title = new JLabel(new ImageIcon(GameUtils.findImage("title.png")));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1;
    this.add(title, gbc);
    
    //panel for input  of player names
    JPanel inputFields = new JPanel();
    inputFields.setLayout(new GridLayout(2, 3));
    
    // Player name labels
    JLabel[] playerLabels = new JLabel[3];
    for (int i = 0; i < 3; i++) {
      playerLabels[i] = new JLabel("Player " + (i+1) + ":");
      playerLabels[i].setHorizontalAlignment(JLabel.CENTER);
      inputFields.add(playerLabels[i]);
    }

    // Player name text fields
    this.playerFields = new JTextField[3];
    for (int i = 0; i < 3; i++) {
      this.playerFields[i] = new JTextField(8);
      inputFields.add(playerFields[i]);
    }

    // GUI positioning
    gbc = new GridBagConstraints(); // Reset the constraints
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.weighty = 0.05;
    this.add(inputFields, gbc);
    /**
    for (int i = 0; i < 3; i++) {
      gbc.gridx = i;
      gbc.gridy = 1;
      this.add(playerLabels[i], gbc);
      gbc.gridy = 2;
      this.add(this.playerFields[i], gbc);
    }
    */
    
    gbc.gridx = 1;
    gbc.gridy = 3;
    this.add(btnPanel, gbc);
  }

  /**
   * @return the names entered in the text fields, without leading and
   * trailing whitespace
   */
  public String[] getNames() {
    String[] names = new String[3];
    for (int i = 0; i < 3; i++) {
      names[i] = this.playerFields[i].getText().trim();
    }
    return names;
  }

  /**
   * Handle actions on the start button. Starts the main Jeopardy game
   * by invoking {@link Jeopardy#run};
   *
   * @param e the event which triggered the action. This handler
   * should only be used on the start button, so the source doesn't matter.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Jeopardy.run(this); // Start the game
  }
}
