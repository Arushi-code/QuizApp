# 🧠 QuizMaster - Java Quiz Application

A professional quiz application with timed questions, multiple categories, and high scores.

## Features

- **Timed Questions** — 30 seconds per question with visual countdown
- **Multiple Categories** — Java, Data Structures, General Programming, Database
- **Difficulty Levels** — Easy, Medium, Hard, Mixed
- **Custom Quiz** — Choose category, difficulty, and number of questions
- **Instant Feedback** — See correct/wrong answers immediately
- **Score Tracking** — Save high scores with name and grade
- **Answer Review** — Review all answers after quiz completion
- **High Scores Leaderboard** — View top 10 scores
- **File Handling** — High scores persist across sessions

## Requirements

- Java JDK 8 or higher
- No external libraries required

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/Arushi-code/QuizApp.git
   ```

2. Navigate to the project folder:
   ```bash
   cd QuizApp
   ```

3. Compile all Java files:
   ```bash
   javac *.java
   ```

4. Run the application:
   ```bash
   java Main
   ```

## Project Structure

```
QuizApp/
├── Main.java              # Entry point - home screen with settings
├── Question.java          # Question model (encapsulation)
├── QuizManager.java       # Quiz logic, questions, high scores
├── QuizResult.java        # Result model with grade calculation
├── UIHelper.java          # Reusable Swing UI components
├── QuizWindow.java        # Quiz interface with timer
├── ResultWindow.java      # Score display and answer review
├── highscores.dat         # High scores data (auto-generated)
└── .gitignore
```

## OOP Concepts Used

- **Encapsulation** — Private fields with getters/setters
- **Inheritance** — Custom exceptions extend Exception
- **Polymorphism** — Timer callbacks, event listeners
- **Abstraction** — Modular class design (Question, QuizManager, UIHelper)

## Question Categories

| Category | Topics |
|----------|--------|
| Java | Classes, inheritance, exceptions, collections |
| Data Structures | Stacks, queues, trees, sorting |
| General | OOP concepts, compilers, version control |
| Database | SQL commands, joins, primary keys |

## Author

**Arushi Jha**
- GitHub: [@Arushi-code](https://github.com/Arushi-code)
- Email: aarushijha12@gmail.com
