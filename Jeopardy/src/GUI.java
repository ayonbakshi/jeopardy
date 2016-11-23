import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{
	GridBagConstraints c = new GridBagConstraints();
	static JPanel layout = new JPanel(new CardLayout());
	JPanel questionsGrid = new JPanel(new GridBagLayout());
	QuestionPanel questionPanel;
	
	JButton[] headers = new JButton[6];
	Question[][] buttons = new Question[5][6];
	QuestionPanel[][] questions = new QuestionPanel[5][6];
	JButton back = new JButton("Return");
	
	
	public GUI() {
		setTitle("Jeopardy");
		
		for(int i = 0; i < 6 ; i++){
			headers[i] = new JButton("Topic");
			headers[i].setEnabled(false);
		}
		
		for(int i = 0; i < 6 ; i++){
			c.fill = GridBagConstraints.BOTH;
			c.ipadx = 20;
			c.ipady = 30;
			c.gridx=i;
			c.gridy=0;
			c.insets = new Insets(3,3,10,3);
			questionsGrid.add(headers[i],c);
		}
		
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 6 ; x++){
				buttons[y][x] = new Question((y+1)*200);
				buttons[y][x].addActionListener(this);
			}
		}
		String[] answers = {"Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct"};
		buttons[0][0] = new Question(200, 0, "Question1", answers);
		buttons[0][0].addActionListener(this);
		buttons[0][1] = new Question(200, 0, "Question2", answers);
		buttons[0][1].addActionListener(this);
		
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 20;
		c.ipady = 30;
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 6 ; x++){
				c.gridx = x;
				c.gridy = y+1;
				c.insets = new Insets(3,3,3,3);
				questionsGrid.add(buttons[y][x],c);
			}
		}	
		layout.add(questionsGrid);
		add(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		questionPanel = new QuestionPanel((Question)e.getSource());
		layout.add(questionPanel);
			int index = search(buttons,(JButton)e.getSource());
			int y = index/6;
			int x = index%6;
			buttons[y][x].setEnabled(false);
		CardLayout cl = (CardLayout)(layout.getLayout());
	    cl.previous(layout);
	}
	
	public static void main(String[] args){
		GUI jeopardy = new GUI();
		jeopardy.setSize( 600, 430 );  // Set the size of the window
        jeopardy.setVisible(true); // Make it visible
	}
	
	public static int search(JButton[][] buttons, JButton button){
	for(int y = 0; y < buttons.length ; y++)
		for(int x = 0; x < buttons[0].length; x++)
			if(buttons[y][x]==button)
				return y*6+x;
	return 0;
	}

}
