import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * A game of Jeopardy.
 *
 * Jeopardy is a quiz-show style game with three contestants. The game
 * board consists of a grid of questions with 6 topics and 5 questions
 * per topic with values $200, $400, $600, $800, $1000. Players choose
 * a question, then are presented with the answer and must select the
 * correct question that goes with the answer. If the player guesses
 * correctly, they are awarded the value of the question. If the
 * player guesses incorrectly, the next player gets a chance to
 * guess. If no player guesses correctly, they are shown the answer
 * and nobody gets money. After a question has been played, it is
 * disabled. The last correct answerer chooses the next question.
 *
 * One question in the game is a daily double. The player who selects
 * the daily double can wager any amount of money up to their current
 * balance or $1000, whichever is larger. Only the player who selects
 * the daily double gets to guess. If the player is correct, they gain
 * the amount they wagered. If they are incorrect, they lose that
 * amount.
 *
 * The game ends when all the questions have been played. The player
 * with the largest amount of money at the end of the game wins.
 *
 * Approximate window layout:
 * <pre>
 * +----------------------------------------------------------------------------------------------+
 * | +------------------------------------------------------------------------------------------+ |
 * | |                                           Title                                          | |
 * | +------------------------------------------------------------------------------------------+ |
 * | +--------------+ +-------------------------------------------------------------------------+ |
 * | |  Scoreboard  | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | | Player 1  $n | | | Topic 1 | | Topic 2 | | Topic 3 | | Topic 4 | | Topic 5 | | Topic 6 | | |
 * | | Player 2  $n | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | | Player 3  $n | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | |  $200   | |  $200   | |  $200   | |  $200   | |  $200   | |  $200   | | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | |  $400   | |  $400   | |  $400   | |  $400   | |  $400   | |  $400   | | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | |  $600   | |  $600   | |  $600   | |  $600   | |  $600   | |  $600   | | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | |  $800   | |  $800   | |  $800   | |  $800   | |  $800   | |  $800   | | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | |              | | |  $1000  | |  $1000  | |  $1000  | |  $1000  | |  $1000  | |  $1000  | | |
 * | |              | | +---------+ +---------+ +---------+ +---------+ +---------+ +---------+ | |
 * | +--------------+ +-------------------------------------------------------------------------+ |
 * +----------------------------------------------------------------------------------------------+
 * </pre>
 */
public class Jeopardy extends JPanel implements ActionListener {
  /**
   * The three contestants in the game
   */
  public Player[] players;

  /**
   * The player who's turn it currently is
   */
  private int turn;

  // GUI components
  /**
   * The area on the game board that alternates between displaying the
   * question grid and a specific question
   */
  public JPanel questionArea;

  /**
   * The sidebar that displays the current balance of the three players.
   */
  private JPanel scoreboard;

  /**
   * The titles for each topic. These are placed vertically above the
   * topics in the question grid
   */
  private JLabel[] headers;

  /**
   * The buttons which select the question
   */
  private Question[][] buttons;

  /**
   * The name of the players on scoreboard
   */
  private JLabel[] playerTags;

  /**
   * The amount of money the players have on the scoreboard
   */
  private JLabel[] playerDollars;

  /**
   * The window the game is being conducted in
   */
  public static JFrame gameFrame;

  /**
   * Sets up the main game of Jeopardy.
   *
   * This method reads the questions from a CSV file, chooses 6 topics,
   * and sets up the main window.
   *
   * @param names the names of the three players
   * @throws FileNotFoundException if the question file can't be found
   */
  public Jeopardy(String[] names) throws FileNotFoundException {
    super(new GridBagLayout()); // Use a GridBagLayout for the game panel

    this.turn = 0; // It is player 1's turn first

    // Set up the players
    this.players = new Player[3];
    for (int i = 0; i < 3; i++) {
      if (names[i] == null || names[i].equals("")) {
        names[i] = "Player " + (i + 1);
      }

      this.players[i] = new Player(names[i]);
    }

    // Read questions
    Scanner qScan = null;
    String fName = GameUtils.findFile("questions.csv"); // Find the file
    if (fName != null) { // The file was found
      qScan = new Scanner(new File(fName));
    } else { // The file wasn't found
      JOptionPane.showMessageDialog(null, "Can't find question file.", "Error", JOptionPane.ERROR_MESSAGE);
      throw new FileNotFoundException();
    }

    // Read from the file
    List<Question> questions = new ArrayList<Question>(); // Each question in the file is a question in the list
    List<String> allTopics = new ArrayList<String>(); // This list stores the topics in the file, with no duplicates
    while (qScan.hasNextLine()) { // Go until there are no more lines
      String[] dataRow = qScan.nextLine().split("\t"); // Read a line and split it along tab characters

      if (dataRow.length == 1) { continue; } // Skip blank lines

      // Extract data
      String q = dataRow[0]; // The question
      String t = dataRow[1]; // The topic
      int v = Integer.parseInt(dataRow[2]); // The value (dollar amount)
      int c = Integer.parseInt(dataRow[3]); // The correct answer
      String[] a = { // The answers
        dataRow[4], dataRow[5], dataRow[6], dataRow[7]
      };

      // Create a Question object and add it to the list
      questions.add(new Question(q, a, c, v, t, false, new ImageIcon(GameUtils.findImage(v + ".png"))));

      // If this topic has not been seen before, add it to the list
      if (allTopics.indexOf(t) == -1) {
        allTopics.add(t);
      }
    }

    // Randomly choose 6 unique topics
    String[] topics = new String[6]; // The default value of an object in an array is null
    while (Arrays.asList(topics).indexOf(null) != -1) { // Keep going until each slot is filled
      // Choose a random topic, remove it from the list of all topics,
      // and add it to the list of chosen topics. The topic is removed
      // from the list of all topics so that no topic is chosen
      // twice. indexOf returns the first match it finds, so the slots
      // in the list are filled in order.
      topics[Arrays.asList(topics).indexOf(null)] = allTopics.remove((int)(Math.random() * allTopics.size()));
    }

    // Get the questions associated with the chosen topics
    this.buttons = new Question[6][5];
    for (Question q : questions) { // Go through each question
      String topic = q.getTopic(); // Get the topic
      int x = Arrays.asList(topics).indexOf(topic); // Find the x-coordinate of the button
      if (x != -1) { // This question's topic is one of the ones chosen
        int y = q.getValue() / 200 - 1; // $200 -> 0, $400 -> 1, $600 -> 2, etc.

        this.buttons[x][y] = q; // Add it to the grid
      }
    }

    // Make a random question a daily double
    int ddx = (int) (Math.random() * 6);
    int ddy = (int) (Math.random() * 5);
    buttons[ddx][ddy].makeDailyDouble();

    // Set up GUI
    GridBagConstraints gbc;
    // Title
    JLabel title = new JLabel("Jeopardy!");
    title.setFont(GameUtils.TITLE_FONT);

    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.anchor = GridBagConstraints.CENTER; // Centre the title
    this.add(title, gbc);

    // Scoreboard
    scoreboard = new JPanel(new GridBagLayout());

    // Scoreboard title
    JLabel titleSB = new JLabel("Scoreboard");
    titleSB.setFont(GameUtils.TITLE_FONT);

    gbc = new GridBagConstraints(); // Reset the constraints
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    scoreboard.add(titleSB, gbc);

    // Scoreboard player labels
    this.playerTags = new JLabel[3]; // Names
    this.playerDollars = new JLabel[3]; // Balances
    for (int i = 0; i < 3; i++) {
      this.playerTags[i] = new JLabel(players[i].getName());
      this.playerDollars[i] = new JLabel("$" + players[i].getDollars());
    }

    // Make the first player bold
    Font f = playerTags[0].getFont();
    this.playerTags[0].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    this.playerDollars[0].setFont(f.deriveFont(f.getStyle() | Font.BOLD));

    gbc = new GridBagConstraints();
    gbc.ipadx = 10;
    gbc.ipady = 10;
    for (int i = 0; i < 3; i++){
      gbc.gridx = 0;
      gbc.gridy = 1 + (i % 3);
      scoreboard.add(playerTags[i], gbc);

      gbc.gridx = 3;
      gbc.gridy = 1 + (i % 3);
      scoreboard.add(playerDollars[i], gbc);
    }

    // Add the scoreboard to the window
    gbc = new GridBagConstraints(); // Reset GridBagConstraints
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    gbc.gridwidth = 2;
    this.add(scoreboard, gbc);

    // Question area
    this.questionArea = new JPanel(new CardLayout()); // Switches between question grid and specific question display
    JPanel questionGrid = new JPanel(new GridBagLayout());

    // Common layout to questions and topic headers
    gbc = new GridBagConstraints(); // Reset constraints
    gbc.fill = GridBagConstraints.BOTH;
    gbc.ipadx = 20;
    gbc.ipady = 30;
    gbc.weightx = 1;
    gbc.weighty = 1;

    // Topic headers
    this.headers = new JLabel[6];
    int largestWidth = 0; // The width of the largest header

    for (int i = 0; i < 6; i++) {
      // Centre the topic headers
      this.headers[i] = new JLabel("<html><div style='text-align: center;'>"+topics[i]+"</div></html>",SwingConstants.CENTER);
      this.headers[i].setFont(GameUtils.TITLE_FONT);

      // Check if this is bigger than the largest header so far
      if (headers[i].getPreferredSize().getWidth() > largestWidth) {
        largestWidth = (int) headers[i].getPreferredSize().getWidth();
      }
    }

    // Make all the headers the same width as the widest one
    for (int i = 0; i < 6; i++) {
      headers[i].setPreferredSize(new Dimension(largestWidth,(int)headers[i].getPreferredSize().getHeight()));
    }

    // Add the headers to the question grid
    for (int i = 0; i < 6; i++) {
      gbc.gridx = i;
      gbc.gridy = 0;
      gbc.insets = new Insets(3, 3, 10, 3);
      gbc.anchor = GridBagConstraints.CENTER;
      questionGrid.add(headers[i], gbc);
    }

    // Question buttons
    for (int x = 0; x < 6; x++) {
      for (int y = 0; y < 5; y++) {
        this.buttons[x][y].addActionListener(this);

        gbc.gridx = x;
        gbc.gridy = y + 1;
        gbc.insets = new Insets(3, 3, 3, 3);
        questionGrid.add(buttons[x][y], gbc);
      }
    }
    this.questionArea.add(questionGrid);

    // Add the question area to the main window
    gbc = new GridBagConstraints(); // Reset constraints
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 2;
    gbc.gridy = 1;
    gbc.weightx = 0.9;
    this.add(questionArea, gbc);
  }

  /**
   * Handler for clicks on the question buttons. The handler switches
   * the display to the question panel and gets wagers if this
   * question is a daily double.
   *
   * @param e the button click that triggered the handler
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Question source = (Question) e.getSource(); // Get the button that triggered the handler
    source.setEnabled(false); // Disable the button

    if(source.getDailyDouble()) {
      // Display daily double panel. The panel asks for the amount the
      // player wants to wager. A player can wager up to all their
      // money or the value of the highest question in the round,
      // whichever is higher.
      int max = Math.max(players[turn].getDollars(), 1000);

      // Display "Daily Double" in the question area until a wager is made
      JPanel dailyDoublePanel = new JPanel(new GridBagLayout());
      JLabel dailyDoubleLabel = new JLabel("Daily Double!");
      dailyDoubleLabel.setFont(GameUtils.TITLE_FONT);
      dailyDoublePanel.add(dailyDoubleLabel);
      questionArea.add(dailyDoublePanel);
      CardLayout cl = (CardLayout) this.questionArea.getLayout();
      cl.last(questionArea);

      // Create the component to ask for the wager
      JPanel wager = new JPanel();
      JLabel wagerMessage = new JLabel("Min: $5, Max: $"+max);
      SpinnerNumberModel sModel = new SpinnerNumberModel(5, 5, max, 100);
      JSpinner spinner = new JSpinner(sModel);
      wager.add(wagerMessage);
      wager.add(spinner);

      // Get the wager
      JOptionPane.showMessageDialog(questionArea, wager, "Enter a wager", JOptionPane.QUESTION_MESSAGE);
      int value = (Integer) spinner.getValue();
      source.dailyDouble(value);
      questionArea.remove(dailyDoublePanel);
    }

    // Display the question
    QuestionPanel questionDisplay = new QuestionPanel(source, this);
    this.questionArea.add(questionDisplay);

    CardLayout cl = (CardLayout) this.questionArea.getLayout();
    cl.last(questionArea);
  }

  /**
   * Go to the next player and make the correct label bold on the
   * scoreboard.
   */
  public void incrementTurn() {
    // Unbold the previous player
    Font f = playerTags[turn].getFont();
    this.playerTags[turn].setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
    this.playerDollars[turn].setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

    this.turn = (this.turn + 1) % 3; // Increment the turn

    // Bold the current player
    f = playerTags[turn].getFont();
    this.playerTags[turn].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    this.playerDollars[turn].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
  }

  /**
   * @return whose turn it currently is
   */
  public int getTurn() {
    return this.turn;
  }

  /**
    * Update the amount of money displayed on the scoreboard for the
    * current player.
    */
  public void updateDollars() {
    Player current = this.players[this.turn];
    this.playerDollars[this.turn].setText("$" + current.getDollars());
  }

  /**
   * Run the game after the starting page has been displayed
   *
   * @param sp the starting panel, used to get the names entered by the user
   */
  public static void run(StartPanel sp) {
    try {
      Jeopardy game = new Jeopardy(sp.getNames()); // Create the game object
      Jeopardy.gameFrame.setContentPane(game); // Switch the content pane to the game

      // Repaint the frame
      Jeopardy.gameFrame.repaint();
      Jeopardy.gameFrame.revalidate();
    } catch (FileNotFoundException fnfe) {
      System.out.println(fnfe.getMessage()); // Print the error message
      System.exit(1); // Quit with a non-zero exit satus to indicate failure
    }
  }

  public static void main(String[] args) {
    // Set the default fonts of JButtons, JLabels, JTextFields, and JOptionPanes
    UIManager.put("Button.font", GameUtils.GAME_FONT);
    UIManager.put("Label.font", GameUtils.GAME_FONT);
    UIManager.put("TextField.font", GameUtils.GAME_FONT);
    UIManager.put("OptionPane.font", GameUtils.GAME_FONT);

    // Set up the game window
    Jeopardy.gameFrame = new JFrame();
    Jeopardy.gameFrame.setTitle("Jeopardy!");

    // Create and display the start window
    StartPanel sp = new StartPanel();
    Jeopardy.gameFrame.setContentPane(sp);

    // Window size - as big as possible, 4:3
    Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds(); // Get the biggest possible size for the window
    int width;
    int height;
    if (screen.getWidth() < screen.getHeight()) { // Protrait orientation
      width = (int) screen.getWidth();
      height = width / 4 * 3;
    } else { // Labscape orientation
      height = (int) screen.getHeight();
      width = height / 3 * 4;
    }
    Jeopardy.gameFrame.setSize(width,height);
    Jeopardy.gameFrame.setResizable(false); // Don't allow resizing the window
    Jeopardy.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Jeopardy.gameFrame.setVisible(true); // Display the window
  }
}
