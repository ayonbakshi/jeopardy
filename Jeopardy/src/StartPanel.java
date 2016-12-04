import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Display the Jeopardy title card and get the names of the players.
 */
public class StartPanel extends JPanel implements ActionListener{
  JTextField[] playerFields;
  String[] names = new String[3];

  public StartPanel(){
    this.setLayout(new GridBagLayout());

    // Start button
    JButton startBtn = new JButton("Start!");
    startBtn.addActionListener(this);

    // Title
    JLabel title = new JLabel("Jeopardy");
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1;
    this.add(title, gbc);

    // Player name labels
    JLabel[] playerLabels = new JLabel[3];
    for (int i = 0; i < 3; i++) {
      playerLabels[i] = new JLabel("Player " + (i+1) + ":");
    }

    // Player name text fields
    this.playerFields = new JTextField[3];
    for (int i = 0; i < 3; i++) {
      this.playerFields[i] = new JTextField(8);
    }

    // GUI positioning
    gbc = new GridBagConstraints(); // Reset the constraints
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weighty = 0.05;
    for (int i = 0; i < 3; i++) {
      gbc.gridx = i;
      gbc.gridy = 2;
      this.add(playerLabels[i], gbc);
      gbc.gridy = 3;
      this.add(this.playerFields[i], gbc);
    }

    gbc.gridx = 1;
    gbc.gridy = 4;
    this.add(startBtn, gbc);

  }

  public String[] getNames() {
    return this.names;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < 3; i++) {
      this.names[i] = this.playerFields[i].getText();
    }

    Jeopardy.run(this); // Start the game
  }
}
