import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A multiple-choice question in Jeopardy. A question has 4 possible
 * answers, only one of which is correct. A question has a certain
 * dollar value and is part of a topic.
 */
public class Question extends JButton {
  /**
   * The question text
   */
  private String question;

  /**
   * The possible answers to the question
   */
  private String[] answers;

  /**
   * The index of the correct answer
   */
  private int correct;

  /**
   * The value of the question.
   */
  private int dollars;

  /**
   * The topic of the question
   */
  private String topic;

  /**
   * If the question is a daily double
   */
  private boolean isDailyDouble;

  /**
   * Creates a new question.
   *
   * @param question the text of the question
   * @param answers the answers of the question
   * @param correct the index in the array of answers of the correct
   * answer
   * @param dollars the value awarded to the player for correctly answering the question
   * @param topic the topic of the question
   * @param isDailyDouble if the question is a daily double
   *
   * @throws IllegalArgumentException if the dollar value is negative,
   * the correct answer isn't in [0, 3], or there aren't 4 answers
   */
  public Question(String question, String[] answers, int correct, int dollars, String topic, boolean isDailyDouble, ImageIcon icon) {

    super(icon);
    setBorder(null);

    if (dollars < 0) {
      throw new IllegalArgumentException("A question can't have negative value.");
    }
    this.dollars = dollars;

    if (correct < 0 || correct > 3) {
      throw new IllegalArgumentException("Invalid correct answer: " + correct);
    }
    this.correct = correct; // Index in the answer array where the right answer can be found

    this.question = question;

    if (answers.length != 4) {
      throw new IllegalArgumentException("Invalid number of answers: " + answers.length + ". Must be four answers.");
    }
    this.answers = answers;

    this.topic = topic;
    this.isDailyDouble = isDailyDouble;
  }

  /**
   * Get the value awarded to the player for correctly answering the question.
   *
   * @return the dollar value of the question
   */
  public int getValue() {
    return dollars;
  }

  /**
   * @return the text of the question
   */
  public String getQuestion() {//get the question
    return question;
  }

  /**
   * @return all the possible answers to the question
   */
  public String[] getAnswers() {
    return answers;
  }

  /**
   * @return the index of the correct answer in the array of answers
   */
  public int getCorrect() {
    return this.correct;
  }

  /**
   * @return the topic of the question
   */
  public String getTopic() {
    return this.topic;
  }

  /**
   * Check if a guess is correct
   *
   * @return true if the guess was correct, false otherwise
   */
  public boolean checkGuess(int guess) {
    return guess == correct;
  }

  /**
   * @return if this question is a daily double
   */
  public boolean getDailyDouble() {
    return this.isDailyDouble;
  }

  /**
   * Make the question a daily double
   */
  public void makeDailyDouble() {
    this.isDailyDouble = true;
  }

  /**
   * Set the amount of money the question is worth if this is a daily
   * double.
   *
   * @param amount the amount of money the player bid
   */
  public void dailyDouble(int amount) {
    if(isDailyDouble) {
      this.dollars = amount;
    }
  }
}
