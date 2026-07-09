import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ResultWindow extends JFrame {
    public ResultWindow(QuizManager manager) {
        JFrame frame = UIHelper.createFrame("QuizMaster - Results", 700, 600);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83C\uDFC6", "QUIZ COMPLETE"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // Score Card
        JPanel scoreCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(UIHelper.SHADOW);
                g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 18, 18);
                GradientPaint gp = new GradientPaint(0, 0, new Color(46, 160, 67), getWidth(), getHeight(), new Color(36, 130, 52));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 18, 18);
            }
        };
        scoreCard.setOpaque(false);
        scoreCard.setPreferredSize(new Dimension(0, 120));
        scoreCard.setLayout(new GridBagLayout());

        JPanel scoreContent = new JPanel();
        scoreContent.setOpaque(false);
        scoreContent.setLayout(new BoxLayout(scoreContent, BoxLayout.Y_AXIS));

        int score = manager.getScore();
        int total = manager.getTotalQuestions();
        int pct = (score * 100) / total;

        JLabel scoreLabel = new JLabel(score + " / " + total);
        scoreLabel.setFont(UIHelper.getFont(Font.BOLD, 36));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pctLabel = new JLabel(pct + "%  |  Grade: " + getGrade(pct));
        pctLabel.setFont(UIHelper.getFont(Font.PLAIN, 16));
        pctLabel.setForeground(new Color(220, 255, 220));
        pctLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreContent.add(scoreLabel);
        scoreContent.add(Box.createVerticalStrut(5));
        scoreContent.add(pctLabel);

        scoreCard.add(scoreContent);

        JPanel scoreSection = new JPanel(new BorderLayout());
        scoreSection.setBackground(UIHelper.BG);
        scoreSection.add(scoreCard, BorderLayout.CENTER);
        mainPanel.add(scoreSection, BorderLayout.NORTH);

        // Save score
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        savePanel.setBackground(UIHelper.BG);

        JTextField nameField = UIHelper.createTextField(15);
        nameField.setPreferredSize(new Dimension(180, 35));

        JButton saveBtn = UIHelper.createButton("Save Score", UIHelper.SUCCESS);
        JLabel saveStatus = new JLabel(" ");
        saveStatus.setFont(UIHelper.getFont(Font.PLAIN, 12));

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                saveStatus.setForeground(UIHelper.DANGER);
                saveStatus.setText("Enter your name!");
                return;
            }
            manager.saveHighScore(name);
            saveStatus.setForeground(UIHelper.SUCCESS);
            saveStatus.setText("Score saved!");
            saveBtn.setEnabled(false);
        });

        savePanel.add(UIHelper.createLabel("Your Name:"));
        savePanel.add(nameField);
        savePanel.add(saveBtn);
        savePanel.add(saveStatus);

        mainPanel.add(savePanel, BorderLayout.CENTER);

        // Answer Review
        JLabel reviewTitle = UIHelper.createBoldLabel("Answer Review", 16);
        reviewTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        mainPanel.add(reviewTitle, BorderLayout.CENTER);

        JPanel reviewPanel = new JPanel();
        reviewPanel.setBackground(UIHelper.BG);
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        List<Question> questions = manager.getCurrentQuiz();
        List<Integer> answers = manager.getUserAnswers();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int userAns = answers.get(i);
            boolean correct = q.isCorrect(userAns);

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(UIHelper.CARD_BG);
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(0, 0, 8, 0),
                    new LineBorder(UIHelper.TABLE_BORDER, 1, true)));

            String icon = correct ? "\u2705" : "\u274C";
            JLabel qLabel = new JLabel(icon + "  Q" + (i + 1) + ". " + q.getQuestion());
            qLabel.setFont(UIHelper.getFont(Font.PLAIN, 13));
            qLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            row.add(qLabel, BorderLayout.CENTER);

            if (!correct) {
                String correctAns = q.getOptions()[q.getCorrectAnswer()];
                JLabel ansLabel = new JLabel("Correct: " + correctAns);
                ansLabel.setFont(UIHelper.getFont(Font.PLAIN, 11));
                ansLabel.setForeground(UIHelper.CORRECT);
                ansLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));
                row.add(ansLabel, BorderLayout.SOUTH);
            }

            reviewPanel.add(row);
        }

        JScrollPane scrollPane = new JScrollPane(reviewPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIHelper.BG);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
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

        // Final layout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UIHelper.BG);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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
