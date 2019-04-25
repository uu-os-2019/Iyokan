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
    private UserGuid user;

    public QuizSession(QuizRoundCollection collection, Location location, UserGuid user) {
        rounds = new QuizInstance[quizQuestionCount];
        this.location = location;
        this.user = user;

        QuizRound[] quizzes = collection.getRandomQuizRounds(quizQuestionCount);

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

    public UserGuid getUser() {
        return user;
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
            double normalizedTime = Utils.clamp(instance.getDuration() / 10000D, 0, 1);
            score += 1000 - normalizedTime * 500 ;
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