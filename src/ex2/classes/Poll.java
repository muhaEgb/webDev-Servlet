package ex2.classes;

import ex2.classes.Answer;

import java.util.ArrayList;

/**
 * Poll Class - <BR>contains three members:<BR>
 * 1. question: string of the question<BR>
 * 2. answers: an array list of Answer objects<BR>
 * 3. valid: boolean represents availability
 */
public class Poll {
    private String question;
    private ArrayList<Answer> answers;
    private boolean valid;

    /**
     * C-tor
     */
    public Poll() {
        question = "";
        answers = new ArrayList<Answer>();
        valid = true;
    }

    /**
     * C-tor
     *
     * @param question string
     * @param answers  ArrayList
     */
    public Poll(String question, ArrayList<Answer> answers) {
        this.question = question;
        this.answers = answers;
        this.valid = true;
    }

    /**
     * getter
     *
     * @return answers ArrayList
     */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    /**
     * getter
     *
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * setter
     *
     * @param question string
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * setter
     *
     * @param answers string
     */
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    /**
     * Check if poll is valid
     *
     * @return true if valid; otherwise false
     */
    public boolean isValid() {
        if (question.equals("") || answers.isEmpty() || answers.size() < 2 || !valid) {
            return false;
        }
        return true;
    }

    /**
     * set the value "false" to the member "valid"
     */
    public void isNotValid() {
        valid = false;
    }
}
