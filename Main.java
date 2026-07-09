import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    private QuizManager manager;
    private JFrame currentFrame;

    public Main() {
        manager = new QuizManager();
        showHomeScreen();
    }

    private void showHomeScreen() {
        JFrame frame = UIHelper.createFrame("QuizMaster - Home", 580, 580);
        currentFrame = frame;
        frame.setLayout(new BorderLayout());

        // Header with dark mode toggle
        JPanel header = UIHelper.createGradientHeader("\uD83C\uDFC6", "QUIZMASTER");

        JToggleButton darkToggle = new JToggleButton(UIHelper.darkMode ? "\u2600" : "\u263E");
        darkToggle.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        darkToggle.setOpaque(false);
        darkToggle.setContentAreaFilled(false);
        darkToggle.setBorderPainted(false);
        darkToggle.setForeground(Color.WHITE);
        darkToggle.setToolTipText("Toggle Dark Mode");
        darkToggle.addActionListener(e -> {
            UIHelper.toggleDarkMode();
            darkToggle.setText(UIHelper.darkMode ? "\u2600" : "\u263E");
            frame.getContentPane().setBackground(UIHelper.BG);
            frame.repaint();
        });

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        headerRight.setOpaque(false);
        headerRight.add(darkToggle);
        header.add(headerRight, BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        // Main content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        // Welcome Card
        JPanel welcomeCard = UIHelper.createCard();
        welcomeCard.setLayout(new BoxLayout(welcomeCard, BoxLayout.Y_AXIS));
        welcomeCard.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel title = UIHelper.createBoldLabel("Welcome to QuizMaster!", 22);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = UIHelper.createLabel("Test your programming knowledge with timed quizzes");
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setForeground(UIHelper.TEXT_GRAY);

        JLabel pointsInfo = new JLabel("\uD83D\uDCA1  Earn points: Easy=1x  |  Medium=2x  |  Hard=3x  |  Streak bonus up to +5");
        pointsInfo.setFont(UIHelper.getFont(Font.PLAIN, 12));
        pointsInfo.setForeground(UIHelper.PRIMARY);
        pointsInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        welcomeCard.add(title);
        welcomeCard.add(Box.createVerticalStrut(8));
        welcomeCard.add(subtitle);
        welcomeCard.add(Box.createVerticalStrut(12));
        welcomeCard.add(pointsInfo);

        mainPanel.add(welcomeCard);
        mainPanel.add(Box.createVerticalStrut(15));

        // Settings Card
        JPanel settingsCard = UIHelper.createCard();
        settingsCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel settingsTitle = UIHelper.createBoldLabel("Quiz Settings", 16);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        settingsCard.add(settingsTitle, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        settingsCard.add(Box.createVerticalStrut(8), gbc);

        // Category
        gbc.gridy = 2;
        JLabel catLabel = UIHelper.createLabel("Category");
        catLabel.setFont(UIHelper.getFont(Font.BOLD, 12));
        settingsCard.add(catLabel, gbc);

        List<String> categories = manager.getCategories();
        categories.add(0, "All");
        JComboBox<String> catCombo = new JComboBox<>(categories.toArray(new String[0]));
        catCombo.setFont(UIHelper.getFont(Font.PLAIN, 13));
        catCombo.setPreferredSize(new Dimension(220, 38));
        gbc.gridx = 1;
        settingsCard.add(catCombo, gbc);

        // Difficulty
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel diffLabel = UIHelper.createLabel("Difficulty");
        diffLabel.setFont(UIHelper.getFont(Font.BOLD, 12));
        settingsCard.add(diffLabel, gbc);

        List<String> difficulties = manager.getDifficulties();
        JComboBox<String> diffCombo = new JComboBox<>(difficulties.toArray(new String[0]));
        diffCombo.setFont(UIHelper.getFont(Font.PLAIN, 13));
        diffCombo.setPreferredSize(new Dimension(220, 38));
        gbc.gridx = 1;
        settingsCard.add(diffCombo, gbc);

        // Count
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel countLabel = UIHelper.createLabel("Questions");
        countLabel.setFont(UIHelper.getFont(Font.BOLD, 12));
        settingsCard.add(countLabel, gbc);

        Integer[] counts = {5, 10, 15, 20};
        JComboBox<Integer> countCombo = new JComboBox<>(counts);
        countCombo.setFont(UIHelper.getFont(Font.PLAIN, 13));
        countCombo.setPreferredSize(new Dimension(220, 38));
        gbc.gridx = 1;
        settingsCard.add(countCombo, gbc);

        mainPanel.add(settingsCard);
        mainPanel.add(Box.createVerticalStrut(15));

        // Start Button
        JButton startBtn = UIHelper.createButton("Start Quiz", UIHelper.PRIMARY);
        startBtn.setPreferredSize(new Dimension(250, 50));
        startBtn.setFont(UIHelper.getFont(Font.BOLD, 15));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(e -> {
            String category = (String) catCombo.getSelectedItem();
            String difficulty = (String) diffCombo.getSelectedItem();
            int count = (Integer) countCombo.getSelectedItem();
            manager.startQuiz(category, difficulty, count);
            if (manager.getTotalQuestions() == 0) {
                JOptionPane.showMessageDialog(frame, "No questions available for this selection!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.dispose();
            new QuizWindow(manager);
        });

        JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startPanel.setBackground(UIHelper.BG);
        startPanel.add(startBtn);
        mainPanel.add(startPanel);

        mainPanel.add(Box.createVerticalStrut(10));

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setBackground(UIHelper.BG);

        JButton scoresBtn = UIHelper.createButton("High Scores", UIHelper.WARNING);
        scoresBtn.addActionListener(e -> showHighScores());

        JButton historyBtn = UIHelper.createButton("Quiz History", new Color(139, 92, 246));
        historyBtn.addActionListener(e -> showQuizHistory());

        bottomPanel.add(scoresBtn);
        bottomPanel.add(historyBtn);
        mainPanel.add(bottomPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showHighScores() {
        JFrame frame = UIHelper.createFrame("QuizMaster - High Scores", 650, 420);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83C\uDFC6", "HIGH SCORES"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        List<QuizResult> scores = manager.getHighScores();

        if (scores.isEmpty()) {
            JLabel emptyLabel = UIHelper.createLabel("No scores yet! Be the first to play.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            String[] columns = {"#", "Name", "Score", "Points", "Streak", "Grade"};
            Object[][] data = new Object[scores.size()][6];
            for (int i = 0; i < scores.size(); i++) {
                QuizResult r = scores.get(i);
                data[i][0] = i + 1;
                data[i][1] = r.getPlayerName();
                data[i][2] = r.getScore() + "/" + r.getTotalQuestions() + " (" + r.getPercentage() + "%)";
                data[i][3] = r.getTotalPoints() + " pts";
                data[i][4] = r.getMaxStreak() + "x";
                data[i][5] = r.getGrade();
            }

            JTable table = new JTable(data, columns);
            table.setFont(UIHelper.getFont(Font.PLAIN, 13));
            table.setRowHeight(38);
            table.getTableHeader().setFont(UIHelper.getFont(Font.BOLD, 13));
            table.getTableHeader().setBackground(UIHelper.PRIMARY);
            table.getTableHeader().setForeground(Color.WHITE);
            table.getTableHeader().setPreferredSize(new Dimension(0, 40));
            table.setEnabled(false);
            table.setBackground(UIHelper.CARD_BG);
            table.setForeground(UIHelper.TEXT_DARK);
            table.setGridColor(UIHelper.TABLE_BORDER);
            table.setShowGrid(true);
            table.setIntercellSpacing(new Dimension(1, 1));

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(UIHelper.CARD_BG);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }

        JButton closeBtn = UIHelper.createButton("Close", UIHelper.DANGER);
        closeBtn.addActionListener(e -> frame.dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(UIHelper.BG);
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showQuizHistory() {
        JFrame frame = UIHelper.createFrame("QuizMaster - Quiz History", 700, 450);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83D\uDCD6", "QUIZ HISTORY"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        List<QuizResult> history = manager.getQuizHistory();

        if (history.isEmpty()) {
            JLabel emptyLabel = UIHelper.createLabel("No quiz history yet! Play a quiz to see your history.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            String[] columns = {"#", "Name", "Score", "Points", "Streak", "Grade", "Date"};
            Object[][] data = new Object[history.size()][7];
            for (int i = 0; i < history.size(); i++) {
                QuizResult r = history.get(i);
                data[i][0] = i + 1;
                data[i][1] = r.getPlayerName();
                data[i][2] = r.getScore() + "/" + r.getTotalQuestions() + " (" + r.getPercentage() + "%)";
                data[i][3] = r.getTotalPoints() + " pts";
                data[i][4] = r.getMaxStreak() + "x";
                data[i][5] = r.getGrade();
                data[i][6] = r.getFormattedDate();
            }

            JTable table = new JTable(data, columns);
            table.setFont(UIHelper.getFont(Font.PLAIN, 12));
            table.setRowHeight(36);
            table.getTableHeader().setFont(UIHelper.getFont(Font.BOLD, 12));
            table.getTableHeader().setBackground(UIHelper.PRIMARY);
            table.getTableHeader().setForeground(Color.WHITE);
            table.getTableHeader().setPreferredSize(new Dimension(0, 40));
            table.setEnabled(false);
            table.setBackground(UIHelper.CARD_BG);
            table.setForeground(UIHelper.TEXT_DARK);
            table.setGridColor(UIHelper.TABLE_BORDER);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(UIHelper.CARD_BG);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }

        JButton closeBtn = UIHelper.createButton("Close", UIHelper.DANGER);
        closeBtn.addActionListener(e -> frame.dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(UIHelper.BG);
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
