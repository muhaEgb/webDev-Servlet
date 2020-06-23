package ex2.classes;

/**
 * Answer Class - <BR>contains two members:<BR>
 * 1. answer: string represents answer<BR>
 * 2. count: counter of voted times
 */
public class Answer {
    private String answer;
    private int count;

    /**
     * C-tor
     */
    public Answer() {
        this.answer = "";
        this.count = 0;
    }

    /**
     * C-tor
     *
     * @param answer string
     */
    public Answer(String answer) {
        this.answer = answer;
        this.count = 0;
    }

    /**
     * getter
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * getter
     *
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * setter
     *
     * @param count int
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * setter
     *
     * @param answer string
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * incline counter
     */
    public void inc() {
        this.count++;
    }
}
