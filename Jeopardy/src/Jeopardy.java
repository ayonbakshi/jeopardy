import javax.swing.*;
import java.util.Scanner;

public class Jeopardy extends JFrame {
  private Player[] players;
  static int turn = 0;
  
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
  
  public static void incrementTurn(){
		if (turn == 3){
			turn = 0;
		}
		else{
			turn++;
		}
	}
  
  public static void main(String[] args) {
    Jeopardy game = new Jeopardy();
    GUI gui = new GUI();
  }
}
