import java.awt.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class EndPanel extends JFrame{
	GridBagConstraints gbc;
	JLabel message, title;
	
	public EndPanel(){
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		title = new JLabel(new ImageIcon(GameUtils.findImage("title.png")));
		/**
		gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 3;
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.weighty = 0.55;
	    **/
		this.getContentPane().add(Box.createVerticalStrut(200));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.getContentPane().add(title);
	    
	    message = new JLabel("Thanks for playing!");
	    message.setFont(GameUtils.TITLE_FONT);
	    /**
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.gridwidth = 5;
	    gbc.weighty = 0.45;
	    //gbc.weightx = 1;
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.CENTER;
	    **/
	    this.getContentPane().add(Box.createVerticalStrut(100));
	    message.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.getContentPane().add(message);
	    
	    
	}
	
	public static void main(String[] args){
		EndPanel gui = new EndPanel();
        gui.setVisible(true);
        gui.setSize(500, 500);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
