import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuizResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;
    private int totalQuestions;
    private LocalDateTime dateTime;

    public QuizResult(String playerName, int score, int totalQuestions) {
        this.playerName = playerName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.dateTime = LocalDateTime.now();
    }

    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public LocalDateTime getDateTime() { return dateTime; }

    public int getPercentage() {
        return (score * 100) / totalQuestions;
    }

    public String getGrade() {
        int pct = getPercentage();
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B";
        if (pct >= 60) return "C";
        if (pct >= 50) return "D";
        return "F";
    }

    public String getFormattedDate() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    @Override
    public String toString() {
        return String.format("%s - %d/%d (%d%%) - %s", playerName, score, totalQuestions, getPercentage(), getGrade());
    }
}
