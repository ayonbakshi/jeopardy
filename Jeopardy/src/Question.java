import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
public class Question extends JButton implements MouseListener{
    private int correct, dollars;
    private String question;
    private String[] answers;
    
    public Question(int dollars){
        //constructor
        super("$" + dollars);
        this.setBackground(Color.blue);
        this.dollars = dollars;
        this.correct = 0;//index in the answer array where the right answer can be found
        this.question = null;
        this.answers = null;
        this.addMouseListener(this);
    }
    
    public Question(int dollars, int correct, String question, String[] answers){
        //constructor
        super("$" + dollars);
        this.setBackground(Color.blue);
        this.dollars = dollars;
        this.correct = correct;//index in the answer array where the right answer can be found
        this.question = question;
        this.answers = answers;
        this.addMouseListener(this);
    }
    
    public int getValue(){//get the award of a correctly answered question
        return dollars;
    }
    
    public String getQuestion(){//get the question
        return question;
    }
    
    public String[] getAnswer(){//get the possible answers
        return answers;
    }
    
    public boolean checkGuess(int guess){ //return the validity the guess
        return guess == correct;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBackground(Color.cyan);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setBackground(Color.blue);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.setBackground(Color.magenta);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.setBackground(Color.blue);
		
	}
}
