package com.iyokan.geocapserver;

public class QuizSession {

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
            timeStarted = System.currentTimeMillis();
        }

        public long getDuration() {
            return timeEnded - timeStarted;
        }
    }

    private static final int quizQuestionCount = 3;

    private QuizInstance[] rounds;
    private int score;
    private int questionIndex;

    public QuizSession(QuizRoundCollection collection) {
        rounds = new QuizInstance[quizQuestionCount];

        QuizRound[] quizzes = collection.getRandomQuizRounds(quizQuestionCount);

        for (int i = 0; i < quizQuestionCount; i++) {
            rounds[i] = new QuizInstance(quizzes[i]);
        }

        score = 0;
        questionIndex = 0;
    }

    public QuizRound getQuestion() {
        return rounds[questionIndex].quiz;
    }

    public boolean isDone() {
        return questionIndex >= quizQuestionCount;
    }
    
    public boolean answer(String answer) {
        boolean correct = false;
        QuizInstance instance = rounds[questionIndex];
        instance.end();

        // If correct answer increase the score
        if (instance.quiz.checkAnswer(answer)) {
            correct = true;
            long normalizedTime = instance.getDuration() / 10000;
            score += 1000 - normalizedTime * 500 ;
        }

        // Increments the question we're at
        questionIndex ++;

        // Starts the next question
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
