import java.awt.*;

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
	JLabel message, title;
	
	public EndPanel(){ //creates panel to be displayed at end of game
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		title = new JLabel(new ImageIcon(GameUtils.findImage("title.png")));
		this.add(Box.createVerticalStrut(200));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.add(title);
	    
	    message = new JLabel("Thanks for playing!");
	    message.setFont(GameUtils.TITLE_FONT);
	    this.add(Box.createVerticalStrut(100));
	    message.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.add(message);
	    
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
