# 🧠 QuizMaster - Java Quiz Application

A professional quiz application with timed questions, multiple categories, and gamification features.

## Features

- **Timed Questions** — 30 seconds per question with visual countdown
- **Multiple Categories** — Java, Data Structures, General Programming, Database
- **Difficulty Levels** — Easy, Medium, Hard, Mixed
- **Custom Quiz** — Choose category, difficulty, and number of questions
- **Streak Bonus** — Extra points for consecutive correct answers (up to +5)
- **Difficulty Multiplier** — Easy=1x, Medium=2x, Hard=3x points
- **Progress Bar** — Visual quiz progress indicator
- **Dark Mode** — Toggle between light and dark themes
- **Instant Feedback** — See correct/wrong answers immediately
- **Score Tracking** — Save high scores with name and grade
- **Quiz History** — View all past attempts (last 50 quizzes)
- **Answer Review** — Review all answers after quiz completion
- **High Scores Leaderboard** — View top 10 scores
- **File Handling** — High scores and history persist across sessions

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
├── QuizManager.java       # Quiz logic, questions, scoring
├── QuizResult.java        # Result model with grade calculation
├── UIHelper.java          # Reusable Swing UI components
├── QuizWindow.java        # Quiz interface with timer
├── ResultWindow.java      # Score display and answer review
├── highscores.dat         # High scores data (auto-generated)
├── history.dat            # Quiz history data (auto-generated)
├── README.md              # Project documentation
└── .gitignore
```

## OOP Concepts Used

- **Encapsulation** — Private fields with getters/setters
- **Polymorphism** — Timer callbacks, event listeners
- **Abstraction** — Modular class design (Question, QuizManager, UIHelper)

## Scoring System

| Difficulty | Multiplier | Base Points |
|------------|------------|-------------|
| Easy | 1x | 10 pts |
| Medium | 2x | 20 pts |
| Hard | 3x | 30 pts |

- **Streak Bonus:** +1 to +5 extra points for consecutive correct answers
- **Max Streak Bonus:** 5 points per correct answer in a streak

## Author

**Arushi Jha**
- GitHub: [@Arushi-code](https://github.com/Arushi-code)
- Email: aarushijha12@gmail.com
