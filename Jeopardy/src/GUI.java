import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{
	GridBagConstraints c = new GridBagConstraints(); // Grid Bag Constraints Object
	
	//Panels
	static JPanel layout = new JPanel(new CardLayout()); // Questions and Question grid, dimensions ~600x430
	JPanel questionsGrid = new JPanel(new GridBagLayout()); //Question Grid
	QuestionPanel questionPanel; // Question panel, to be determined with action listener
	
	//Title label
	JLabel title = new JLabel("Title");
	
	JButton[] headers = new JButton[6]; // Headers
	Question[][] buttons = new Question[5][6]; // Buttons to access questions
	
	Player[] players;
	
	public GUI(Player[] players) {
//<<<<<<< HEAD
		setTitle("Jeopardy");
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel question = new JPanel(new GridBagLayout());
		//back.addActionListener(this);
		
		//question.add(back);
		
		//This is some code for testing the Question Panel class, delete later
		/*
	    String[] answers = {"Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct"};
		Question qObj = new Question(200, 0, "Which is correct?", answers);
		QuestionPanel panel = new QuestionPanel(qObj);
		c.gridx = 0;
		c.gridy = 0;
		question.add(panel, c);
		*/
		//deletable code ends here
		
		JPanel questionsGrid = new JPanel(new GridBagLayout());

		this.setLayout(new FlowLayout());
		this.setTitle("Jeopardy");
		
		// Define and put in topic headers
		for(int i = 0; i < 6 ; i++){
			headers[i] = new JButton("Topic");
			headers[i].setEnabled(false);
		}
		for(int i = 0; i < 6 ; i++){
			c.fill = GridBagConstraints.BOTH;
			c.ipadx = 20;
			c.ipady = 30;
			c.gridx = i;
			c.gridy = 0;
			c.insets = new Insets(3,3,10,3);
			questionsGrid.add(headers[i],c);
		}
		//Define and put in question buttons in 2d array
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 6 ; x++){
				buttons[y][x] = new Question((y+1)*200);
				buttons[y][x].addActionListener(this);
			}
		}
		
		//TESTING, properly instantiates the first two question objects
	
		String[] answers = {"Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct"};
		buttons[0][0] = new Question(200, 0, "Question1", answers);
		buttons[0][0].addActionListener(this);
		buttons[0][1] = new Question(200, 0, "Question2", answers);
		buttons[0][1].addActionListener(this);
		
		//end of testing
		
		//Put question buttons in the questionGrid panel
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
		layout.add(questionsGrid); // Add question grid to the layout
		this.add(title);
		this.add(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){ // On button press
		questionPanel = new QuestionPanel((Question)e.getSource()); // sets questionPanel to a panel containing all the information about the question;
		layout.add(questionPanel); //Adds the question panel to the card layout
		
		//Disables button
		int index = search(buttons,(JButton)e.getSource());
		int y = index/6;
		int x = index%6;
		buttons[y][x].setEnabled(false);
		
		CardLayout cl = (CardLayout)(layout.getLayout()); // Cardlayout swaps to the question panel
	    cl.last(layout);
	}
	
	public static void main(String[] args){
		GUI jeopardy = new GUI();
		jeopardy.setSize( 600, 500 );  // Set the size of the window
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
