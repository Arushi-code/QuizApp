import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private String question;
    private String[] options;
    private int correctAnswer;
    private String category;
    private String difficulty;

    public Question(String question, String[] options, int correctAnswer, String category, String difficulty) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }

    public boolean isCorrect(int selectedAnswer) {
        return selectedAnswer == correctAnswer;
    }

    @Override
    public String toString() {
        return question;
    }
}
