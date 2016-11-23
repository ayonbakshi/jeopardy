import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class Jeopardy extends JFrame {
  private Player[] players;
  static int turn = 0;
  
  public Jeopardy() throws FileNotFoundException {
    super("Jeopardy!");

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
    
    this.players = new Player[3];
    
    for (int i = 0; i < 3; i++) {
      String name = JOptionPane.showInputDialog("Enter player " + (i + 1) + "'s name");

      if (name == null || name.equals("")) {
        name = "Player " + (i + 1);
      }
      
      this.players[i] = new Player(name);
    }
  }

  public static void incrementTurn(){
		if (turn == 3){
			turn = 0;
		}
		else{
			turn++;
		}
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
    GUI gui = new GUI();
  }
}
