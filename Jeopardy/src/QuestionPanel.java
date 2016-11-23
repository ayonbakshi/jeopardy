
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
		this.setPreferredSize(new Dimension(600, 500));
		this.setLayout(new GridBagLayout());
<<<<<<< HEAD
=======
		this.setSize(new Dimension(600, 430));
>>>>>>> cbfa102eeda36aa6d0138d05c68e455e714e78cf
		
		for (int i = 0; i < 6; i++){
			answers[i] = new JButton(question.getAnswer()[i]);
			answers[i].addActionListener(this);
		}
		
		this.question = new JLabel(question.getQuestion());
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 120 ,10);
		add(this.question, gbc);
		
		
		for(int i = 0; i < 6; i++){
			//first column of questions is in column 1
			//second column of questions is in column 4
			gbc.gridx = 1 + (i % 2) * 4;
			//first row is in row 5
			//second row is in row 7
			//third row is in row 9
			gbc.gridy = 5 + (i % 3) * 2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			//gbc.gridwidth = 2;
			gbc.ipadx = 20;
			gbc.ipady = 30;
			gbc.insets = new Insets(0, 10, 10 ,10);
			
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
		    cl.first(GUI.layout);
		}
		else{
			//blackout the answer chosen if answer is wrong
			answers[index].setEnabled(false);
		}
		
	}
	
	
}
