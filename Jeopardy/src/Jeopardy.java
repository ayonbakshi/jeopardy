import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Jeopardy extends JFrame implements ActionListener {
  private Player[] players;
  private int turn = 0;

  // GUI components
  private JPanel questionArea;

  private JLabel[] headers;
  private Question[][] buttons;
  
  public Jeopardy() throws FileNotFoundException {
    // Set up GUI
    super("Jeopardy!");

    // Initialize GUI components
    GridBagConstraints c = new GridBagConstraints();

    JPanel content = new JPanel(new GridBagLayout());
    this.questionArea = new JPanel(new CardLayout());
    JPanel questionGrid = new JPanel(new GridBagLayout());

    // Fill game board
    // Common layout
    c.fill = GridBagConstraints.BOTH;
    c.ipadx = 20;
    c.ipady = 30;
    
    // Topic headers
    this.headers = new JLabel[6];
    for (int i = 0; i < 6; i++) {
      this.headers[i] = new JLabel("Topic");

      // Layout stuff
      c.gridx = i;
      c.gridy = 0;
      c.insets = new Insets(3, 3, 10, 3);
      questionGrid.add(headers[i], c);
    }

    // Question buttons
    this.buttons = new Question[6][5];
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 6; x++) {
        this.buttons[x][y] = new Question((y + 1) * 200);
        this.buttons[x][y].addActionListener(this);

        // Layout
        c.gridx = x;
        c.gridy = y + 1;
        c.insets = new Insets(3, 3, 3, 3);
        questionGrid.add(buttons[x][y], c);
      }
    }

    this.questionArea.add(questionGrid);

    JLabel title = new JLabel("Jeopardy!");
    c.fill = GridBagConstraints.BOTH;
    c.ipadx = 0;
    c.ipady = 0;
    c.insets = new Insets(0, 0, 0, 0);
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    content.add(title, c);

    c.gridx = 1;
    c.gridy = 2;
    content.add(this.questionArea);
    
    // Read questions
    Scanner qScan;
    try {
      qScan = new Scanner(new File("data" + File.separator + "questions.csv"));
    } catch (FileNotFoundException fnfe) { // Can't find quesiton file
      JOptionPane.showMessageDialog(null, "Can't find question file.", "Error", JOptionPane.ERROR_MESSAGE);
      throw fnfe;
    }
    
    List<String> questions = new ArrayList<String>();
    List<String> topics = new ArrayList<String>();
    List<Integer> values = new ArrayList<Integer>();
    List<Integer> correct = new ArrayList<Integer>();
    List<String[]> answers = new ArrayList<String[]>();

    while (qScan.hasNextLine()) {
      String[] dataRow = qScan.nextLine().split("\t");
      questions.add(dataRow[0]);
      topics.add(dataRow[1]);
      values.add(Integer.parseInt(dataRow[2]));
      correct.add(Integer.parseInt(dataRow[3]));
      String[] answerArray = {dataRow[4], dataRow[5], dataRow[6], dataRow[7], dataRow[8], dataRow[9]};
      answers.add(answerArray);
    }

    // Get player names
    this.players = new Player[3];
    
    for (int i = 0; i < 3; i++) {
      String name = JOptionPane.showInputDialog("Enter player " + (i + 1) + "'s name");

      if (name == null || name.equals("")) {
        name = "Player " + (i + 1);
      }
      
      this.players[i] = new Player(name);
    }

    this.setContentPane(content);
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    Question source = (Question) e.getSource();
    QuestionPanel questionDisplay = new QuestionPanel(source);
    this.questionArea.add(questionDisplay);

    // Disable the button
    source.setEnabled(false);

    CardLayout cl = (CardLayout) this.questionArea.getLayout();
    cl.last(this.questionArea);
  }

  public void incrementTurn() {
    this.turn = (this.turn + 1) % 3;
  }
  
  public static void main(String[] args) throws FileNotFoundException {
    // Use the look and feel native to the system instead of Java's
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
      System.out.println("Native look and feel not supported: " + e);
    } catch (ClassNotFoundException e) {
      System.out.println("Not a recognized look and feel: " + e);
    } catch (InstantiationException e) {
      System.out.println("Couldn't set up native look and feel: " + e);
    } catch (IllegalAccessException e) {
      System.out.println("Couldn't set up native look and feel: " + e);
    }
    
    Jeopardy game = new Jeopardy();
  }
}
