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
	static JPanel layout = new JPanel(new CardLayout());
	JButton[] headers = new JButton[6];
	Question[] buttons = new Question[30];
	JButton back = new JButton("Return");
	
	public GUI() {
		setTitle("Jeopardy");
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel question = new JPanel(new GridBagLayout());
		back.addActionListener(this);
		
		//question.add(back);
		
		//This is some code for testing the Question Panel class, delete later
	    String[] answers = {"Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct", "Not Correct"};
		Question qObj = new Question(200, 0, "Which is correct?", answers);
		QuestionPanel panel = new QuestionPanel(qObj);
		c.gridx = 0;
		c.gridy = 0;
		question.add(panel, c);
		//deletable code ends here
		
		JPanel questionsGrid = new JPanel(new GridBagLayout());
		
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
		
		for(int i = 0; i < 30; i++){
			buttons[i] = new Question(((i/6)+1)*200);
			buttons[i].addActionListener(this);
		}	
		
		for(int i = 0; i < 30; i++){
			c.fill = GridBagConstraints.BOTH;
			c.ipadx = 20;
			c.ipady = 30;
			c.gridx = i%6;
			c.gridy = i/6+1;
			c.insets = new Insets(3,3,3,3);
			questionsGrid.add(buttons[i],c);
		}
		layout.add(questionsGrid);
		layout.add(question);
		add(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()!=back)
			buttons[search(buttons,(JButton)e.getSource())].setEnabled(false);
		CardLayout cl = (CardLayout)(layout.getLayout());
	    cl.previous(layout);
	}
	
	public static void main(String[] args){
		GUI jeopardy = new GUI();
		jeopardy.setSize( 600, 500 );  // Set the size of the window
        jeopardy.setVisible(true); // Make it visible
	}
	
	public static int search(JButton[] buttons, JButton button){
	for(int i = 0; i < buttons.length ; i++)
		if(buttons[i]==button)
			return i;
	return 0;
	}

}
