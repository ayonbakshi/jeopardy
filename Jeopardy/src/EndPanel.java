import java.awt.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
/**
 * end screen for Jeopardy game
 */
public class EndPanel extends JPanel{
	GridBagConstraints gbc;
	JLabel message, title, winner;
	
	public EndPanel(Player[] players){ //creates panel to be displayed at end of game
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		ArrayList<Integer> winners;
		
		title = new JLabel(new ImageIcon(GameUtils.findImage("title.png")));
		this.add(Box.createVerticalStrut(200));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.add(title);
	    
	    winners = determineWinner(players);
	    if (winners.size() == 1){
	    	this.winner = new JLabel("Player " + (winners.get(0) + 1) + " has won");
	    }
	    else if (winners.size() == 2){
	    	this.winner = new JLabel("Player " + (winners.get(0) + 1) + " and Player " + + (winners.get(1) + 1)  + " have tied");
	    }
	    else{
	    	this.winner = new JLabel("Player 1, Player 2 and Player 3 have tied");
	    }
	    this.winner.setFont(GameUtils.TITLE_FONT);
	    this.add(Box.createVerticalStrut(100));
	    this.winner.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.add(this.winner);
	    
	    message = new JLabel("Thanks for playing!");
	    message.setFont(GameUtils.TITLE_FONT);
	    this.add(Box.createVerticalStrut(30));
	    message.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.add(message);
	    
	}
	
	public ArrayList<Integer> determineWinner(Player[] players){
		ArrayList<Integer> winners = new ArrayList<Integer>();
		if(players[0].getDollars() == players[1].getDollars() && players[0].getDollars() == players[2].getDollars() && players[1].getDollars() == players[2].getDollars()){
			winners.add(0);
			winners.add(1);
			winners.add(2);
		}
		else if (players[0].getDollars() == players[1].getDollars() && players[1].getDollars() > players[2].getDollars()){
			winners.add(0);
			winners.add(1);
		}
		else if (players[0].getDollars() == players[2].getDollars() && players[2].getDollars() > players[1].getDollars()){
			winners.add(0);
			winners.add(2);
		}
		else if (players[2].getDollars() == players[1].getDollars() && players[1].getDollars() > players[0].getDollars()){
			winners.add(1);
			winners.add(2);
		}
		else{
			int winner = 0;
			for(int i = 1; i < 3; i++){
				if(players[i].getDollars() > players[winner].getDollars()){
				winner = i;
				}
			}
			
			winners.add(winner);
		}
		
		return winners;
	}
	/*
	public static void main(String[] args){
		EndPanel gui = new EndPanel();
        gui.setVisible(true);
        gui.setSize(500, 500);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	*/
}
