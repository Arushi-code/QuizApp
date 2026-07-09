import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuizResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;
    private int totalQuestions;
    private int totalPoints;
    private int maxStreak;
    private LocalDateTime dateTime;

    public QuizResult(String playerName, int score, int totalQuestions, int totalPoints, int maxStreak) {
        this.playerName = playerName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.totalPoints = totalPoints;
        this.maxStreak = maxStreak;
        this.dateTime = LocalDateTime.now();
    }

    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getTotalPoints() { return totalPoints; }
    public int getMaxStreak() { return maxStreak; }
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
        return String.format("%s - %d/%d (%d%%) - %d pts - %s", playerName, score, totalQuestions, getPercentage(), totalPoints, getGrade());
    }
}
