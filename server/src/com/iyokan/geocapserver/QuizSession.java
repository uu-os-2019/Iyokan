package com.iyokan.geocapserver;

/**
 * Represents a session of quiz questions. Contains n number of questions and will store the score as you answer them
 */
public class QuizSession {
    /**
     * Represents a single quiz question
     */
    private class QuizInstance {
        private long timeStarted;
        private long timeEnded;
        private boolean answered;
        public QuizRound quiz;

        public QuizInstance(QuizRound round) {
            this.quiz = round;
        }

        public void start() {
            timeStarted = System.currentTimeMillis();
        }

        public void end() {
            answered = true;
            timeEnded = System.currentTimeMillis();
        }

        public long getDuration() {
            if (answered == false) {
                throw new RuntimeException("Can't get duration before question has ended");
            }
            return timeEnded - timeStarted;
        }
    }

    private static final int quizQuestionCount = 3;

    private QuizInstance[] rounds;
    private int score;
    private int questionIndex;
    private Location location;

    public QuizSession(QuizRoundCollection collection, Location location) {
        rounds = new QuizInstance[quizQuestionCount];
        this.location = location;

        QuizRound[] quizzes = collection.getRandomQuizRounds(location.getTags(), quizQuestionCount);

        for (int i = 0; i < quizQuestionCount; i++) {
            rounds[i] = new QuizInstance(quizzes[i]);
        }

        score = 0;
        questionIndex = 0;
        rounds[0].start();
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Gets the question that's at the current index
     * @return
     */
    public QuizRound getQuestion() {
        return rounds[questionIndex].quiz;
    }

    /**
     * Returns true if all questions in the session has been answered
     * @return true if all questions in the session has been answered
     */
    public boolean isDone() {
        return questionIndex >= quizQuestionCount;
    }

    /**
     * Answers the current quiz session with a provided answer. Updates score if correct and moves to the next question
     * @param answer
     * @return true if the answer was correct
     */
    public boolean answer(String answer) {
        boolean correct = false;
        QuizInstance instance = rounds[questionIndex];
        instance.end();

        // If correct answer increase the score
        if (instance.quiz.checkAnswer(answer)) {
            correct = true;
            // Removed because time shouldn't tie into score
            //double normalizedTime = Utils.clamp(instance.getDuration() / 10000D, 0, 1);
            score += 1000;
        }

        // Increments the question we're at
        questionIndex ++;

        // Starts the next question if it's available
        if (isDone() == false) {
            rounds[questionIndex].start();
        }

        return correct;
    }

    /**
     * Gets the score based on the current amount of questions answered
     * @return current score
     */
    public int getScore() {
        return score;
    }
}
