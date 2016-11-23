
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuestionPanel extends JPanel implements ActionListener{
	JButton[] answers= new JButton[6];
	JLabel question;
	Question qObj;
	GridBagConstraints gbc = new GridBagConstraints();
	
	public QuestionPanel(Question question){
		qObj = question;
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(600, 430));
		
		for (int i = 0; i < 6; i++){
			answers[i] = new JButton(question.getAnswer()[i]);
			answers[i].addActionListener(this);
		}
		
		this.question = new JLabel(question.getQuestion());
		gbc.gridx = 2;
		gbc.gridy = 0;
		add(this.question, gbc);
		
		
		for(int i = 0; i < 6; i++){
			//first column of questions is in column 1
			//second column of questions is in column 3
			gbc.gridx = 1 + (i % 2) * 2;
			//first row is in row 5
			//second row is in row 7
			//third row is in row 9
			gbc.gridy = 8 + (i % 3) * 2;
			add(answers[i], gbc);
		}
		
	}
	
	public void actionPerformed (ActionEvent e){
		int index = 0;
		
		for (int i = 0; i < 6; i++){
			if (answers[i] == (JButton)(e.getSource())){
				index = i;
			}
		}
		
		if(qObj.checkGuess(index)){
			//increment $$ here
			//display amount earned and balance
			CardLayout cl = (CardLayout)(GUI.layout.getLayout());
		    cl.previous(GUI.layout);
		}
		else{
			//blackout the answer chosen if answer is wrong
			answers[index].setEnabled(false);
		}
		
	}
	
	
}
