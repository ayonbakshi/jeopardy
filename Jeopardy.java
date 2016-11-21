import javax.swing.*;
import java.util.Scanner;

public class Jeopardy extends JFrame {
  private Player[] players;
  
  public Jeopardy() {
    this.players = new Player[3];
    
    for (int i = 0; i < 3; i++) {
      String name = JOptionPane.showInputDialog("Enter player " + (i + 1) + "'s name");

      if (name.equals("")) {
        name = "Player " + (i + 1);
      }

      this.players[i] = new Player(name);
    }
  }
  
  public static void main(String[] args) {
    Jeopardy game = new Jeopardy();
  }
}
