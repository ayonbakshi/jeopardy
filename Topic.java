public class Topic {
    private String topic;
    private Question[] questions;
    private boolean[] chosen;
    
    /**
     * 
     * @param questions the set of questions for the topic
     * @param answers the choices of answers for the topic
     * @param correct 
     * @param dollars
     * @param topic 
     */
    public Topic(String[] questions, String[][] answers, int[]correct, int[]dollars, String topic){
        this.topic = topic;
        for (int i = 0; i < questions.length; i++){
            Question[i] = new Question(dollars[i], correct[i], questions[i], answers[i]);
        }
        chosen = new boolean[5];
    }
    
    public Topic(Question[] questions, String topic){
        this.topic = topic;
        this.questions = questions;
        chosen = new boolean[5];
    }
    
    public Question[] getQuestion(int questionNum){
        return questions;
    }
}
