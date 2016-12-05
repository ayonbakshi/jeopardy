import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Display the instructions for a game of Jeopardy, read from a file.
 */
public class Instructions implements ActionListener {
  /**
   * The instructions
   */
  private String helpText;

  /**
   * Set up the instructions by reading them from a file.
   *
   * @throws FileNotFoundException if the instructions file wasn't found
   */
  public Instructions() throws FileNotFoundException {
    // Open the instructions file
    String helpFile = GameUtils.findFile("instructions.txt");
    if (helpFile == null) { // The help file wasn't found
      throw new FileNotFoundException("instructions.txt");
    }

    // Read the instructions file
    Scanner helpScan = new Scanner(new File(helpFile));
    this.helpText = "";
    while (helpScan.hasNextLine()) { // Keep going until the file runs out of lines
      this.helpText += helpScan.nextLine() + "\n"; // Read a line and add it to the text
    }
    helpScan.close(); // Clean up the scannerx
  }

  /**
   * Display the help text in a popup. This action handler is for the
   * instructions button on the start page.
   *
   * @param e the action that triggered this handler
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(null, this.helpText, "Instructions", JOptionPane.INFORMATION_MESSAGE);
  }
}
