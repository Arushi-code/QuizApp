import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class QuizWindow {
    private QuizManager manager;
    private JFrame frame;
    private JLabel questionLabel, progressLabel, timerLabel, categoryLabel;
    private JLabel streakLabel, pointsLabel;
    private JPanel optionsPanel;
    private JProgressBar progressBar;
    private Timer timer;
    private int timeLeft;
    private static final int TIME_PER_QUESTION = 30;
    private JLabel feedbackLabel;
    private Timer feedbackTimer;

    public QuizWindow(QuizManager manager) {
        this.manager = manager;
        frame = UIHelper.createFrame("QuizMaster - Quiz", 700, 600);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83D\uDD0D", "QUIZMASTER"), BorderLayout.NORTH);

        // Top bar with stats
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UIHelper.BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 25, 5, 25));

        JPanel leftStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftStats.setOpaque(false);
        progressLabel = UIHelper.createBoldLabel("Q 1/" + manager.getTotalQuestions(), 14);
        categoryLabel = UIHelper.createLabel("");
        categoryLabel.setForeground(UIHelper.TEXT_GRAY);
        leftStats.add(progressLabel);
        leftStats.add(categoryLabel);

        JPanel rightStats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightStats.setOpaque(false);
        streakLabel = UIHelper.createBoldLabel("\uD83D\uDD25 Streak: 0", 13);
        streakLabel.setForeground(UIHelper.WARNING);
        pointsLabel = UIHelper.createBoldLabel("\u2B50 Points: 0", 13);
        pointsLabel.setForeground(UIHelper.PRIMARY);
        timerLabel = new JLabel("\u23F1 " + TIME_PER_QUESTION + "s");
        timerLabel.setFont(UIHelper.getFont(Font.BOLD, 15));
        timerLabel.setForeground(UIHelper.TIMER_TEXT);
        rightStats.add(streakLabel);
        rightStats.add(pointsLabel);
        rightStats.add(timerLabel);

        topBar.add(leftStats, BorderLayout.WEST);
        topBar.add(rightStats, BorderLayout.EAST);

        // Progress Bar
        progressBar = new JProgressBar(0, manager.getTotalQuestions());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setFont(UIHelper.getFont(Font.BOLD, 11));
        progressBar.setForeground(UIHelper.PRIMARY);
        progressBar.setBackground(UIHelper.TABLE_BORDER);
        progressBar.setPreferredSize(new Dimension(0, 22));
        progressBar.setBorder(BorderFactory.createEmptyBorder());

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(UIHelper.BG);
        topSection.add(topBar, BorderLayout.NORTH);
        topSection.add(progressBar, BorderLayout.SOUTH);
        frame.add(topSection, BorderLayout.NORTH);

        // Feedback label
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(UIHelper.getFont(Font.BOLD, 16));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackLabel.setPreferredSize(new Dimension(0, 35));
        feedbackLabel.setOpaque(true);
        feedbackLabel.setBackground(UIHelper.BG);

        feedbackTimer = new Timer(1200, e -> {
            feedbackLabel.setText(" ");
            feedbackLabel.setBackground(UIHelper.BG);
        });
        feedbackTimer.setRepeats(false);

        // Question Card
        JPanel questionCard = UIHelper.createCard();
        questionCard.setLayout(new BorderLayout());
        questionCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 25, 0, 25),
                new LineBorder(UIHelper.TABLE_BORDER, 1, true)));

        questionLabel = new JLabel("");
        questionLabel.setFont(UIHelper.getFont(Font.BOLD, 17));
        questionLabel.setForeground(UIHelper.TEXT_DARK);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        questionCard.add(questionLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(UIHelper.BG);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        centerPanel.add(questionCard, BorderLayout.NORTH);
        centerPanel.add(feedbackLabel, BorderLayout.CENTER);

        // Options
        optionsPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        optionsPanel.setBackground(UIHelper.BG);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        centerPanel.add(optionsPanel, BorderLayout.SOUTH);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Timer
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("\u23F1 " + timeLeft + "s");
            if (timeLeft <= 10) timerLabel.setForeground(UIHelper.DANGER);
            if (timeLeft <= 0) {
                timer.stop();
                playSound("timeout");
                manager.submitAnswer(-1);
                nextQuestion();
            }
        });

        showQuestion();
        frame.setVisible(true);
    }

    private void showQuestion() {
        Question q = manager.getCurrentQuestion();
        if (q == null) return;

        progressLabel.setText("Q " + (manager.getCurrentIndex() + 1) + "/" + manager.getTotalQuestions());
        categoryLabel.setText(q.getCategory() + " | " + q.getDifficulty());
        questionLabel.setText("<html><center>" + q.getQuestion() + "</center></html>");
        progressBar.setValue(manager.getCurrentIndex());
        progressBar.setString(manager.getCurrentIndex() + "/" + manager.getTotalQuestions());
        streakLabel.setText("\uD83D\uDD25 Streak: " + manager.getStreak());
        pointsLabel.setText("\u2B50 Points: " + manager.getTotalPoints());

        optionsPanel.removeAll();
        String[] options = q.getOptions();
        for (int i = 0; i < options.length; i++) {
            final int index = i;
            JPanel option = UIHelper.createOptionButton(options[i], i, e -> selectAnswer(index));
            optionsPanel.add(option);
        }
        optionsPanel.revalidate();
        optionsPanel.repaint();

        timeLeft = TIME_PER_QUESTION;
        timerLabel.setText("\u23F1 " + timeLeft + "s");
        timerLabel.setForeground(UIHelper.TIMER_TEXT);
        timer.start();
    }

    private void selectAnswer(int index) {
        timer.stop();
        Question q = manager.getCurrentQuestion();
        boolean correct = manager.submitAnswer(index);

        int points = manager.getPointsPerQuestion().get(manager.getPointsPerQuestion().size() - 1);

        if (correct) {
            playSound("correct");
            String streakMsg = manager.getStreak() > 1 ? " (" + manager.getStreak() + "x streak!)" : "";
            feedbackLabel.setText("\u2705 Correct! +" + points + " pts" + streakMsg);
            feedbackLabel.setForeground(UIHelper.CORRECT);
            feedbackLabel.setBackground(new Color(232, 245, 233));
        } else {
            playSound("wrong");
            String correctAns = q.getOptions()[q.getCorrectAnswer()];
            feedbackLabel.setText("\u274C Wrong! Answer: " + correctAns);
            feedbackLabel.setForeground(UIHelper.INCORRECT);
            feedbackLabel.setBackground(new Color(255, 235, 238));
        }
        feedbackTimer.restart();

        streakLabel.setText("\uD83D\uDD25 Streak: " + manager.getStreak());
        pointsLabel.setText("\u2B50 Points: " + manager.getTotalPoints());

        Timer delay = new Timer(1500, e -> nextQuestion());
        delay.setRepeats(false);
        delay.start();
    }

    private void nextQuestion() {
        if (manager.isFinished()) {
            progressBar.setValue(manager.getTotalQuestions());
            progressBar.setString("Complete!");
            frame.dispose();
            new ResultWindow(manager);
        } else {
            showQuestion();
        }
    }

    private void playSound(String type) {
        // Sound effects can be added by placing .wav files in the project folder
        // and using javax.sound.sampled.AudioSystem
    }
}
