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

public class Jeopardy extends JPanel implements ActionListener {
  public Player[] players;
  private int turn = 0;

  // GUI components
  public JPanel questionArea;
  private JPanel scoreboard;
  private JLabel[] headers;
  private Question[][] buttons;
  private JLabel[] playerTags;
  private JLabel[] playerDollars;

  public static JFrame gameFrame;

  public Jeopardy(String[] names) throws FileNotFoundException {
    super(new GridBagLayout()); // Use a GridBagLayout for the game panel

    // Read questions
    Scanner qScan = null;
    // Open the file
    String fName = GameUtils.findFile("questions.csv");
    if (fName != null) {
      qScan = new Scanner(new File(fName));
    } else { // The file wasn't found
      JOptionPane.showMessageDialog(null, "Can't find question file.", "Error", JOptionPane.ERROR_MESSAGE);
      throw new FileNotFoundException();
    }

    List<Question> questions = new ArrayList<Question>();
    List<String> allTopics = new ArrayList<String>();
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
    String[] topics = new String[6];
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
    for (Question q : questions) {
      String topic = q.getTopic();
      int x = Arrays.asList(topics).indexOf(topic);
      if (x != -1) { // This question's topic is one of the ones chosen
        int y = q.getValue() / 200 - 1; // $200 -> 0, $400 -> 1, $600 -> 2, etc.

        this.buttons[x][y] = q; // Add it to the grid
      }
    }

    // Make a random question a daily double
    int ddx = (int) (Math.random() * 6);
    int ddy = (int) (Math.random() * 5);
    buttons[ddx][ddy].makeDailyDouble();

    // Get the players' names
    this.players = new Player[3];
    for (int i = 0; i < 3; i++) {
      if (names[i] == null || names[i].equals("")) {
        names[i] = "Player " + (i + 1);
      }

      this.players[i] = new Player(names[i]);
    }

    // Set up GUI
    GridBagConstraints c;
    // Title
    JLabel title = new JLabel("Jeopardy!");
    title.setFont(GameUtils.TITLE_FONT);
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.CENTER;
    this.add(title, c);

    // Scoreboard
    scoreboard = new JPanel(new GridBagLayout());

    // Scoreboard title
    JLabel titleSB = new JLabel("Scoreboard");
    titleSB.setFont(GameUtils.TITLE_FONT);
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.insets = new Insets(0, 0, 0, 0);
    c.anchor = GridBagConstraints.CENTER;
    scoreboard.add(titleSB, c);

    // Players
    this.playerTags = new JLabel[3];
    this.playerDollars = new JLabel[3];
    for (int i = 0; i < 3; i++) {
      this.playerTags[i] = new JLabel(players[i].getName());
      this.playerDollars[i] = new JLabel("$" + players[i].getDollars());
    }

    c = new GridBagConstraints();
    c.ipadx = 10;
    c.ipady = 10;
    for (int i = 0; i < 3; i++){
      c.gridx = 0;
      c.gridy = 1 + (i % 3);
      scoreboard.add(playerTags[i], c);

      c.gridx = 3;
      c.gridy = 1 + (i % 3);
      scoreboard.add(playerDollars[i], c);
    }

    c = new GridBagConstraints(); // Reset GridBagConstraints
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.1;
    c.weighty = 1;
    c.anchor = GridBagConstraints.FIRST_LINE_START;
    c.gridwidth = 2;
    this.add(scoreboard, c);

    Font f = playerTags[0].getFont();
    this.playerTags[0].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    this.playerDollars[0].setFont(f.deriveFont(f.getStyle() | Font.BOLD));

    // Fill game board
    this.questionArea = new JPanel(new CardLayout());
    JPanel questionGrid = new JPanel(new GridBagLayout());

    // Common layout
    c = new GridBagConstraints(); // Reset constraints
    c.fill = GridBagConstraints.BOTH;
    c.ipadx = 20;
    c.ipady = 30;
    c.weightx = 1;
    c.weighty = 1;

    // Topic headers
    this.headers = new JLabel[6];
    int largestWidth = 0;

    for (int i = 0; i < 6; i++) {
      this.headers[i] = new JLabel("<html><div style='text-align: center;'>"+topics[i]+"</div></html>",SwingConstants.CENTER);
      this.headers[i].setFont(GameUtils.TITLE_FONT);
      if (headers[i].getPreferredSize().getWidth() > largestWidth)
        largestWidth = (int) headers[i].getPreferredSize().getWidth();
    }
    for (int i = 0; i < 6; i++) {
      headers[i].setPreferredSize(new Dimension(largestWidth,(int)headers[i].getPreferredSize().getHeight()));
    }

    for (int i = 0; i < 6; i++) {
      // Layout stuff
      c.gridx = i;
      c.gridy = 0;
      c.insets = new Insets(3, 3, 10, 3);
      c.anchor = GridBagConstraints.CENTER;
      questionGrid.add(headers[i], c);
    }

    // Question buttons
    for (int x = 0; x < 6; x++) {
      for (int y = 0; y < 5; y++) {
        this.buttons[x][y].addActionListener(this);

        // Layout
        c.gridx = x;
        c.gridy = y + 1;
        c.insets = new Insets(3, 3, 3, 3);
        questionGrid.add(buttons[x][y], c);
      }
    }
    this.questionArea.add(questionGrid);

    // Add the question area to the main window
    c = new GridBagConstraints(); // Reset constraints
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 2;
    c.gridy = 1;
    c.weightx = 0.9;
    this.add(questionArea, c);
  }

  public void actionPerformed(ActionEvent e) {
    Question source = (Question) e.getSource();
    if(source.getDailyDouble()){
      //Display daily double panel here
      //Ask for wager
      // A player can wager up to all their money or the value of the highest question in the round, whichever is higher
      int max =  Math.max(players[turn].getDollars(), 1000);

      JPanel dailyDoublePanel = new JPanel(new GridBagLayout());
      JLabel dailyDoubleLabel = new JLabel("Daily Double!");
      dailyDoubleLabel.setFont(GameUtils.TITLE_FONT);
      dailyDoublePanel.add(dailyDoubleLabel);
      questionArea.add(dailyDoublePanel);
      CardLayout cl = (CardLayout) this.questionArea.getLayout();
      cl.last(questionArea);

      JPanel wager = new JPanel();
      JLabel wagerMessage = new JLabel("Min: $5, Max: $"+max);
      SpinnerNumberModel sModel = new SpinnerNumberModel(5, 5, max, 100);
      JSpinner spinner = new JSpinner(sModel);
      wager.add(wagerMessage);
      wager.add(spinner);
      JOptionPane.showMessageDialog(questionArea, wager, "Enter a wager", JOptionPane.QUESTION_MESSAGE);
      int value = (Integer) spinner.getValue();
      source.dailyDouble(value);
      questionArea.remove(dailyDoublePanel);
    }
    QuestionPanel questionDisplay = new QuestionPanel(source, this);
    this.questionArea.add(questionDisplay);

    source.setEnabled(false); // Disable the button

    CardLayout cl = (CardLayout) this.questionArea.getLayout();
    cl.last(questionArea);
  }

  public void incrementTurn() {//increments turn and bolds the label on scoreboard
    //unbolds previous player
    Font f = playerTags[turn].getFont();
    this.playerTags[turn].setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
    this.playerDollars[turn].setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

    //increments turn
    this.turn = (this.turn + 1) % 3;
    //bolds current player
    f = playerTags[turn].getFont();
    this.playerTags[turn].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    this.playerDollars[turn].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
  }

  public int getTurn() {
    return this.turn;
  }

  public void updateDollars() {
    Player current = this.players[this.turn];
    this.playerDollars[this.turn].setText("$" + current.getDollars());
  }

  public void assignNames(String[] names){//create player objects using names entred from startPanel
    for (int i = 0; i < 3; i++){
      players[i] = new Player(names[i]);
    }
  }

  public static void run(StartPanel sp) {
    try {
      Jeopardy game = new Jeopardy(sp.getNames());
      Jeopardy.gameFrame.setContentPane(game);
      Jeopardy.gameFrame.repaint();
      Jeopardy.gameFrame.revalidate();
    } catch (FileNotFoundException fnfe) {
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    // Set fonts
    UIManager.put("OptionPane.font", GameUtils.GAME_FONT);
    UIManager.put("Label.font", GameUtils.GAME_FONT);
    UIManager.put("Button.font", GameUtils.GAME_FONT);
    UIManager.put("TextField.font", GameUtils.GAME_FONT);

    StartPanel sp = new StartPanel();

    Jeopardy.gameFrame = new JFrame();
    Jeopardy.gameFrame.setTitle("Jeopardy!");
    // Window size - as big as possible, 4:3
    Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds(); // Get the biggest possible size for the window
    int width;
    int height;
    if (screen.getWidth() < screen.getHeight()) {
      width = (int) screen.getWidth();
      height = width / 4 * 3;
    } else {
      height = (int) screen.getHeight();
      width = height / 3 * 4;
    }
    Jeopardy.gameFrame.setSize(width,height);
    Jeopardy.gameFrame.setResizable(false);
    Jeopardy.gameFrame.setContentPane(sp);
    Jeopardy.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Jeopardy.gameFrame.setVisible(true);
  }
}
