package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuizRoundCollection {
    private ArrayList<QuizRound> quizRounds;

    public QuizRoundCollection(){
        quizRounds = new ArrayList<QuizRound>();
    }

    public void loadQuizRounds(JSONArray array) {
        for (int i=0; i < array.length(); i++) {
            QuizRound quizRound = new QuizRound(array.getJSONObject(i));
            quizRounds.add(quizRound);
        }
    }
    public QuizRound[] getAllQuizRounds() {return quizRounds.toArray(new QuizRound[0]);}

    // Lägg till out of bounds index handling
    public QuizRound getQuizRound(int index){
        return quizRounds.get(index);
    }

    public QuizRound[] getRandomQuizRounds(int count) {
        // Select random values we haven't selected before until we have all we need
        if (count > quizRounds.size()) {
            throw new RuntimeException("Not enough questions available to fulfill this request");
        }

        QuizRound[] rounds = new QuizRound[count];

        Integer[] selectedIndexes = new Integer[count];
        Arrays.fill(selectedIndexes, -1);

        Random rnd = new Random();

        for (int i=0; i < count; i++) {
            int newIndex;
            do {
                newIndex = rnd.nextInt(quizRounds.size());
            } while (Utils.arrayContains(selectedIndexes, newIndex));

            selectedIndexes[i] = newIndex;
            rounds[i] = quizRounds.get(newIndex);
        }

        return rounds;
    }
}
