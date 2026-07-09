import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ResultWindow extends JFrame {
    public ResultWindow(QuizManager manager) {
        manager.saveQuizToHistory();
        JFrame frame = UIHelper.createFrame("QuizMaster - Results", 720, 620);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83C\uDFC6", "QUIZ COMPLETE"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Score Card
        JPanel scoreCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIHelper.SHADOW);
                g2d.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 18, 18);
                GradientPaint gp = new GradientPaint(0, 0, new Color(34, 197, 94), getWidth(), getHeight(), new Color(22, 163, 74));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 18, 18);
            }
        };
        scoreCard.setOpaque(false);
        scoreCard.setPreferredSize(new Dimension(0, 130));
        scoreCard.setLayout(new GridBagLayout());

        JPanel scoreContent = new JPanel();
        scoreContent.setOpaque(false);
        scoreContent.setLayout(new BoxLayout(scoreContent, BoxLayout.Y_AXIS));

        int score = manager.getScore();
        int total = manager.getTotalQuestions();
        int pct = (score * 100) / total;
        int points = manager.getTotalPoints();
        int streak = manager.getMaxStreak();

        JLabel scoreLabel = new JLabel(score + " / " + total);
        scoreLabel.setFont(UIHelper.getFont(Font.BOLD, 40));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pctLabel = new JLabel(pct + "%  |  Grade: " + getGrade(pct));
        pctLabel.setFont(UIHelper.getFont(Font.BOLD, 16));
        pctLabel.setForeground(new Color(220, 255, 220));
        pctLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statsLabel = new JLabel("\u2B50 " + points + " pts   |   \uD83D\uDD25 Best Streak: " + streak);
        statsLabel.setFont(UIHelper.getFont(Font.PLAIN, 14));
        statsLabel.setForeground(new Color(200, 245, 200));
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreContent.add(scoreLabel);
        scoreContent.add(Box.createVerticalStrut(6));
        scoreContent.add(pctLabel);
        scoreContent.add(Box.createVerticalStrut(4));
        scoreContent.add(statsLabel);

        scoreCard.add(scoreContent);

        mainPanel.add(scoreCard, BorderLayout.NORTH);

        // Save score
        JPanel saveCard = UIHelper.createCard();
        saveCard.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 10));
        saveCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                new LineBorder(UIHelper.TABLE_BORDER, 1, true)));

        JLabel nameLabel = UIHelper.createLabel("Your Name:");
        nameLabel.setFont(UIHelper.getFont(Font.BOLD, 13));
        JTextField nameField = UIHelper.createTextField(12);
        nameField.setPreferredSize(new Dimension(160, 38));
        JButton saveBtn = UIHelper.createButton("Save Score", UIHelper.SUCCESS);
        saveBtn.setPreferredSize(new Dimension(130, 38));
        JLabel saveStatus = new JLabel(" ");
        saveStatus.setFont(UIHelper.getFont(Font.PLAIN, 12));

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                saveStatus.setForeground(UIHelper.DANGER);
                saveStatus.setText("\u274C Enter name!");
                return;
            }
            manager.saveHighScore(name);
            saveStatus.setForeground(UIHelper.SUCCESS);
            saveStatus.setText("\u2705 Saved!");
            saveBtn.setEnabled(false);
        });

        saveCard.add(nameLabel);
        saveCard.add(nameField);
        saveCard.add(saveBtn);
        saveCard.add(saveStatus);

        JPanel saveSection = new JPanel(new BorderLayout());
        saveSection.setBackground(UIHelper.BG);
        saveSection.add(saveCard, BorderLayout.CENTER);
        mainPanel.add(saveSection, BorderLayout.CENTER);

        // Answer Review
        JLabel reviewTitle = UIHelper.createBoldLabel("Answer Review", 16);
        reviewTitle.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        mainPanel.add(reviewTitle, BorderLayout.AFTER_LINE_ENDS);

        JPanel reviewPanel = new JPanel();
        reviewPanel.setBackground(UIHelper.BG);
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        List<Question> questions = manager.getCurrentQuiz();
        List<Integer> answers = manager.getUserAnswers();
        List<Integer> pointsList = manager.getPointsPerQuestion();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int userAns = answers.get(i);
            boolean correct = q.isCorrect(userAns);
            int qPoints = pointsList.get(i);

            JPanel row = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(UIHelper.CARD_BG);
                    g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                }
            };
            row.setOpaque(false);
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(0, 0, 6, 0),
                    BorderFactory.createCompoundBorder(
                            new LineBorder(UIHelper.TABLE_BORDER, 1, true),
                            BorderFactory.createEmptyBorder(10, 14, 10, 14))));

            String icon = correct ? "\u2705" : "\u274C";
            String pointsText = correct ? "  (+" + qPoints + " pts)" : "";
            JLabel qLabel = new JLabel(icon + "  Q" + (i + 1) + ". " + q.getQuestion() + pointsText);
            qLabel.setFont(UIHelper.getFont(Font.PLAIN, 13));
            row.add(qLabel, BorderLayout.CENTER);

            if (!correct) {
                String correctAns = q.getOptions()[q.getCorrectAnswer()];
                JLabel ansLabel = new JLabel("Correct: " + correctAns);
                ansLabel.setFont(UIHelper.getFont(Font.PLAIN, 11));
                ansLabel.setForeground(UIHelper.CORRECT);
                row.add(ansLabel, BorderLayout.EAST);
            }

            reviewPanel.add(row);
        }

        JScrollPane scrollPane = new JScrollPane(reviewPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIHelper.BG);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(UIHelper.BG);

        JButton retryBtn = UIHelper.createButton("Play Again", UIHelper.PRIMARY);
        retryBtn.addActionListener(e -> {
            frame.dispose();
            new Main();
        });

        JButton homeBtn = UIHelper.createButton("Home", UIHelper.DANGER);
        homeBtn.addActionListener(e -> {
            frame.dispose();
            new Main();
        });

        btnPanel.add(retryBtn);
        btnPanel.add(homeBtn);

        // Bottom layout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UIHelper.BG);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        // Main layout with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, saveSection, bottomPanel);
        splitPane.setDividerLocation(100);
        splitPane.setDividerSize(0);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setBackground(UIHelper.BG);

        mainPanel.add(splitPane, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private String getGrade(int pct) {
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B";
        if (pct >= 60) return "C";
        if (pct >= 50) return "D";
        return "F";
    }
}
