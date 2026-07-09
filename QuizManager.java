import java.io.*;
import java.util.*;

public class QuizManager {
    private List<Question> allQuestions;
    private List<Question> currentQuiz;
    private List<QuizResult> highScores;
    private static final String SCORES_FILE = "highscores.dat";
    private int currentIndex;
    private int score;
    private List<Integer> userAnswers;

    public QuizManager() {
        allQuestions = new ArrayList<>();
        highScores = new ArrayList<>();
        loadQuestions();
        loadHighScores();
    }

    private void loadQuestions() {
        // Java Programming
        allQuestions.add(new Question(
                "Which keyword is used to create a class in Java?",
                new String[]{"create", "class", "define", "struct"}, 1,
                "Java", "Easy"));
        allQuestions.add(new Question(
                "What is the size of an int in Java?",
                new String[]{"2 bytes", "4 bytes", "8 bytes", "16 bytes"}, 1,
                "Java", "Easy"));
        allQuestions.add(new Question(
                "Which method is the entry point of a Java application?",
                new String[]{"start()", "main()", "run()", "init()"}, 1,
                "Java", "Easy"));
        allQuestions.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"}, 1,
                "Java", "Medium"));
        allQuestions.add(new Question(
                "What is the default value of a boolean variable?",
                new String[]{"true", "false", "0", "null"}, 1,
                "Java", "Easy"));
        allQuestions.add(new Question(
                "Which exception is thrown when an array is accessed out of bounds?",
                new String[]{"IOException", "ArrayIndexOutOfBoundsException", "NullPointerException", "ClassNotFoundException"}, 1,
                "Java", "Medium"));
        allQuestions.add(new Question(
                "Which modifier makes a method accessible only within the same class?",
                new String[]{"public", "protected", "private", "default"}, 2,
                "Java", "Medium"));
        allQuestions.add(new Question(
                "Which collection class does not allow duplicate elements?",
                new String[]{"ArrayList", "LinkedList", "HashSet", "Vector"}, 2,
                "Java", "Medium"));
        allQuestions.add(new Question(
                "What is the parent class of all classes in Java?",
                new String[]{"Base", "Object", "Root", "Parent"}, 1,
                "Java", "Easy"));
        allQuestions.add(new Question(
                "Which keyword is used to define an interface in Java?",
                new String[]{"class", "interface", "abstract", "type"}, 1,
                "Java", "Medium"));

        // Data Structures
        allQuestions.add(new Question(
                "Which data structure uses FIFO (First In First Out)?",
                new String[]{"Stack", "Queue", "Tree", "Graph"}, 1,
                "Data Structures", "Easy"));
        allQuestions.add(new Question(
                "Which data structure uses LIFO (Last In First Out)?",
                new String[]{"Queue", "Stack", "Array", "List"}, 1,
                "Data Structures", "Easy"));
        allQuestions.add(new Question(
                "What is the time complexity of binary search?",
                new String[]{"O(n)", "O(log n)", "O(n^2)", "O(1)"}, 1,
                "Data Structures", "Medium"));
        allQuestions.add(new Question(
                "Which sorting algorithm has the best average case time complexity?",
                new String[]{"Bubble Sort", "Selection Sort", "Quick Sort", "Insertion Sort"}, 2,
                "Data Structures", "Medium"));
        allQuestions.add(new Question(
                "What is a binary tree?",
                new String[]{"A tree with 2 nodes", "A tree where each node has at most 2 children",
                            "A tree with 2 levels", "A tree with 2 roots"}, 1,
                "Data Structures", "Medium"));

        // General Programming
        allQuestions.add(new Question(
                "What does OOP stand for?",
                new String[]{"Object Oriented Programming", "Open Object Protocol",
                            "Operational Object Process", "Online Object Programming"}, 0,
                "General", "Easy"));
        allQuestions.add(new Question(
                "Which of these is NOT a programming paradigm?",
                new String[]{"Object-Oriented", "Procedural", "Functional", "Structural"}, 3,
                "General", "Easy"));
        allQuestions.add(new Question(
                "What is a compiler?",
                new String[]{"A program that runs code", "A program that translates code to machine language",
                            "A type of bug", "A programming language"}, 1,
                "General", "Easy"));
        allQuestions.add(new Question(
                "Which of these is a version control system?",
                new String[]{"Java", "Python", "Git", "SQL"}, 2,
                "General", "Easy"));
        allQuestions.add(new Question(
                "What does API stand for?",
                new String[]{"Application Programming Interface", "Advanced Program Integration",
                            "Automated Protocol Interface", "Application Process Integration"}, 0,
                "General", "Medium"));

        // Database
        allQuestions.add(new Question(
                "What does SQL stand for?",
                new String[]{"Structured Query Language", "Simple Query Language",
                            "Standard Query Logic", "System Query Language"}, 0,
                "Database", "Easy"));
        allQuestions.add(new Question(
                "Which SQL command is used to retrieve data?",
                new String[]{"GET", "FETCH", "SELECT", "RETRIEVE"}, 2,
                "Database", "Easy"));
        allQuestions.add(new Question(
                "Which SQL command is used to remove a table?",
                new String[]{"REMOVE", "DELETE", "DROP", "CLEAR"}, 2,
                "Database", "Medium"));
        allQuestions.add(new Question(
                "What is a primary key?",
                new String[]{"A key that opens the database", "A unique identifier for a record",
                            "The first column in a table", "A password for the database"}, 1,
                "Database", "Easy"));
        allQuestions.add(new Question(
                "Which join returns all rows from both tables?",
                new String[]{"INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL OUTER JOIN"}, 3,
                "Database", "Hard"));
    }

    public List<String> getCategories() {
        Set<String> cats = new LinkedHashSet<>();
        for (Question q : allQuestions) cats.add(q.getCategory());
        return new ArrayList<>(cats);
    }

    public List<String> getDifficulties() {
        return Arrays.asList("Easy", "Medium", "Hard", "Mixed");
    }

    public void startQuiz(String category, String difficulty, int questionCount) {
        currentQuiz = new ArrayList<>();
        for (Question q : allQuestions) {
            boolean matchCat = category.equals("All") || q.getCategory().equals(category);
            boolean matchDiff = difficulty.equals("Mixed") || q.getDifficulty().equals(difficulty);
            if (matchCat && matchDiff) currentQuiz.add(q);
        }
        Collections.shuffle(currentQuiz);
        if (currentQuiz.size() > questionCount) {
            currentQuiz = currentQuiz.subList(0, questionCount);
        }
        currentIndex = 0;
        score = 0;
        userAnswers = new ArrayList<>();
    }

    public Question getCurrentQuestion() {
        if (currentIndex < currentQuiz.size()) {
            return currentQuiz.get(currentIndex);
        }
        return null;
    }

    public int getCurrentIndex() { return currentIndex; }
    public int getTotalQuestions() { return currentQuiz.size(); }
    public int getScore() { return score; }
    public List<Integer> getUserAnswers() { return userAnswers; }
    public List<Question> getCurrentQuiz() { return currentQuiz; }

    public boolean submitAnswer(int answer) {
        if (currentIndex >= currentQuiz.size()) return false;
        Question q = currentQuiz.get(currentIndex);
        boolean correct = q.isCorrect(answer);
        if (correct) score++;
        userAnswers.add(answer);
        currentIndex++;
        return correct;
    }

    public boolean isFinished() {
        return currentIndex >= currentQuiz.size();
    }

    public void saveHighScore(String playerName) {
        QuizResult result = new QuizResult(playerName, score, currentQuiz.size());
        highScores.add(result);
        highScores.sort((a, b) -> b.getPercentage() - a.getPercentage());
        if (highScores.size() > 10) highScores = highScores.subList(0, 10);
        saveHighScores();
    }

    public List<QuizResult> getHighScores() {
        return new ArrayList<>(highScores);
    }

    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            System.out.println("Error saving high scores: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadHighScores() {
        File file = new File(SCORES_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            highScores = (List<QuizResult>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading high scores: " + e.getMessage());
        }
    }
}
