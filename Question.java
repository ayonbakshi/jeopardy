public class Question {
    private int correct, dollars;
    private String question;
    private String[] answers;
    
    public Question(int dollars, int correct, String question, String[] answers){
        //constructor
        this.dollars = dollars;
        this.correct = correct;//index in the answer array where the right answer can be found
        this.question = question;
        this.answers = answers;
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
    
    public int getCorrect(){//get the correct answer 
        return correct;
    }
    
    public boolean checkGuess(int guess){ //return the validity the guess
        return guess == correct;
    }
}
