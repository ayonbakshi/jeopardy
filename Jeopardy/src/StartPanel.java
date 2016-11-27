import javax.swing.*;
import java.awt.*;

public class StartPanel extends JFrame{
	JLabel title, player1, player2, player3;
	JTextField player1TF, player2TF, player3TF;
	JButton startBtn;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	public StartPanel(){
		setLayout(new GridBagLayout());
		
		title = new JLabel("Jeopardy");
		player1 = new JLabel("Player 1:");
		player2 = new JLabel("Player 2:");
		player3 = new JLabel("Player 3:");
		player1TF = new JTextField(8);
		player2TF = new JTextField(8);
		player3TF = new JTextField(8);
		startBtn = new JButton();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		add(title, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		add(player1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(player2, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(player3, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(player1TF, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(player2TF, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(player3TF, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(startBtn, gbc);
		
	}
	
	public static void main(String[] args){
		StartPanel gui = new StartPanel();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(500,500);
	}
}
