import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    private QuizManager manager;

    public Main() {
        manager = new QuizManager();
        showHomeScreen();
    }

    private void showHomeScreen() {
        JFrame frame = UIHelper.createFrame("QuizMaster - Home", 550, 500);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83C\uDFC6", "QUIZMASTER"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Welcome Card
        JPanel welcomeCard = UIHelper.createCard();
        welcomeCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel title = UIHelper.createBoldLabel("Welcome to QuizMaster!", 20);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel subtitle = UIHelper.createLabel("Test your programming knowledge");
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setForeground(UIHelper.TEXT_GRAY);

        gbc.gridx = 0; gbc.gridy = 0;
        welcomeCard.add(title, gbc);
        gbc.gridy = 1;
        welcomeCard.add(subtitle, gbc);

        mainPanel.add(welcomeCard, BorderLayout.NORTH);

        // Settings Card
        JPanel settingsCard = UIHelper.createCard();
        settingsCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(8, 10, 8, 10);
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.weightx = 1;

        // Category
        JLabel catLabel = UIHelper.createLabel("Category:");
        catLabel.setFont(UIHelper.getFont(Font.BOLD, 13));
        List<String> categories = manager.getCategories();
        categories.add(0, "All");
        JComboBox<String> catCombo = new JComboBox<>(categories.toArray(new String[0]));
        catCombo.setFont(UIHelper.getFont(Font.PLAIN, 13));
        catCombo.setPreferredSize(new Dimension(200, 35));

        // Difficulty
        JLabel diffLabel = UIHelper.createLabel("Difficulty:");
        diffLabel.setFont(UIHelper.getFont(Font.BOLD, 13));
        List<String> difficulties = manager.getDifficulties();
        JComboBox<String> diffCombo = new JComboBox<>(difficulties.toArray(new String[0]));
        diffCombo.setFont(UIHelper.getFont(Font.PLAIN, 13));
        diffCombo.setPreferredSize(new Dimension(200, 35));

        // Number of questions
        JLabel countLabel = UIHelper.createLabel("Number of Questions:");
        countLabel.setFont(UIHelper.getFont(Font.BOLD, 13));
        Integer[] counts = {5, 10, 15, 20};
        JComboBox<Integer> countCombo = new JComboBox<>(counts);
        countCombo.setFont(UIHelper.getFont(Font.PLAIN, 13));
        countCombo.setPreferredSize(new Dimension(200, 35));

        gbc2.gridx = 0; gbc2.gridy = 0;
        settingsCard.add(catLabel, gbc2);
        gbc2.gridy = 1;
        settingsCard.add(catCombo, gbc2);
        gbc2.gridy = 2;
        settingsCard.add(diffLabel, gbc2);
        gbc2.gridy = 3;
        settingsCard.add(diffCombo, gbc2);
        gbc2.gridy = 4;
        settingsCard.add(countLabel, gbc2);
        gbc2.gridy = 5;
        settingsCard.add(countCombo, gbc2);

        // Start Button
        JButton startBtn = UIHelper.createButton("Start Quiz", UIHelper.PRIMARY);
        startBtn.setPreferredSize(new Dimension(200, 45));
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

        gbc2.gridy = 6;
        settingsCard.add(startBtn, gbc2);

        mainPanel.add(settingsCard, BorderLayout.CENTER);

        // High Scores Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(UIHelper.BG);

        JButton scoresBtn = UIHelper.createButton("High Scores", UIHelper.WARNING);
        scoresBtn.addActionListener(e -> showHighScores(frame));

        bottomPanel.add(scoresBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showHighScores(JFrame parent) {
        JFrame frame = UIHelper.createFrame("QuizMaster - High Scores", 500, 400);
        frame.setLayout(new BorderLayout());

        frame.add(UIHelper.createGradientHeader("\uD83C\uDFC6", "HIGH SCORES"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIHelper.BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        List<QuizResult> scores = manager.getHighScores();

        if (scores.isEmpty()) {
            JLabel emptyLabel = UIHelper.createLabel("No scores yet! Be the first to play.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            String[] columns = {"#", "Name", "Score", "Grade", "Date"};
            Object[][] data = new Object[scores.size()][5];
            for (int i = 0; i < scores.size(); i++) {
                QuizResult r = scores.get(i);
                data[i][0] = i + 1;
                data[i][1] = r.getPlayerName();
                data[i][2] = r.getScore() + "/" + r.getTotalQuestions() + " (" + r.getPercentage() + "%)";
                data[i][3] = r.getGrade();
                data[i][4] = r.getFormattedDate();
            }

            JTable table = new JTable(data, columns);
            table.setFont(UIHelper.getFont(Font.PLAIN, 13));
            table.setRowHeight(35);
            table.getTableHeader().setFont(UIHelper.getFont(Font.BOLD, 13));
            table.getTableHeader().setBackground(UIHelper.PRIMARY);
            table.getTableHeader().setForeground(Color.WHITE);
            table.setEnabled(false);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
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
