import javax.swing.JButton;

public class Question extends JButton{
  private int correct, dollars;
  private String question;
  private String[] answers;
  private String topic;
    
  public Question(int dollars){
    //constructor
    super("$" + dollars);
    this.dollars = dollars;
    this.correct = 0;//index in the answer array where the right answer can be found
    this.question = null;
    this.answers = null;
    this.topic = null;
  }
    
  public Question(int dollars, int correct, String question, String[] answers, String topic){
    //constructor
    super("$" + dollars);
    this.dollars = dollars;
    this.correct = correct;//index in the answer array where the right answer can be found
    this.question = question;
    this.answers = answers;
    this.topic = topic;
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

  public int getCorrect() {
    return this.correct;
  }

  public String getTopic() {
    return this.topic;
  }
    
  public boolean checkGuess(int guess){ //return the validity the guess
    return guess == correct;
  }
}
