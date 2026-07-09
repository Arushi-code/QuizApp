import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class QuizWindow {
    private QuizManager manager;
    private JFrame frame;
    private JLabel questionLabel, progressLabel, timerLabel, categoryLabel;
    private JPanel optionsPanel;
    private Timer timer;
    private int timeLeft;
    private static final int TIME_PER_QUESTION = 30;

    public QuizWindow(QuizManager manager) {
        this.manager = manager;
        frame = UIHelper.createFrame("QuizMaster - Quiz", 700, 550);
        frame.setLayout(new BorderLayout());

        // Header
        frame.add(UIHelper.createGradientHeader("\uD83D\uDD0D", "QUIZMASTER"), BorderLayout.NORTH);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UIHelper.BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 25, 5, 25));

        progressLabel = UIHelper.createBoldLabel("Question 1/" + manager.getTotalQuestions(), 14);
        categoryLabel = UIHelper.createLabel("");
        categoryLabel.setForeground(UIHelper.TEXT_GRAY);

        timerLabel = new JLabel("\u23F1 " + TIME_PER_QUESTION + "s");
        timerLabel.setFont(UIHelper.getFont(Font.BOLD, 16));
        timerLabel.setForeground(UIHelper.TIMER_TEXT);

        topBar.add(progressLabel, BorderLayout.WEST);
        topBar.add(categoryLabel, BorderLayout.CENTER);
        topBar.add(timerLabel, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

        // Question Card
        JPanel questionCard = UIHelper.createCard();
        questionCard.setLayout(new BorderLayout());
        questionCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 25, 10, 25),
                new LineBorder(UIHelper.TABLE_BORDER, 1, true)));

        questionLabel = new JLabel("");
        questionLabel.setFont(UIHelper.getFont(Font.BOLD, 18));
        questionLabel.setForeground(UIHelper.TEXT_DARK);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionCard.add(questionLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(UIHelper.BG);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        centerPanel.add(questionCard, BorderLayout.NORTH);

        // Options
        optionsPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        optionsPanel.setBackground(UIHelper.BG);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        centerPanel.add(optionsPanel, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Start timer
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("\u23F1 " + timeLeft + "s");
            if (timeLeft <= 10) {
                timerLabel.setForeground(UIHelper.DANGER);
            }
            if (timeLeft <= 0) {
                timer.stop();
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

        progressLabel.setText("Question " + (manager.getCurrentIndex() + 1) + "/" + manager.getTotalQuestions());
        categoryLabel.setText(q.getCategory() + " | " + q.getDifficulty());
        questionLabel.setText("<html><center>" + q.getQuestion() + "</center></html>");

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
        manager.submitAnswer(index);
        nextQuestion();
    }

    private void nextQuestion() {
        if (manager.isFinished()) {
            frame.dispose();
            new ResultWindow(manager);
        } else {
            showQuestion();
        }
    }
}
