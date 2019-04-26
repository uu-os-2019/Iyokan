package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.RequestData;
import com.iyokan.geocapserver.route.Route;
import com.iyokan.geocapserver.route.RouteTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizSessionTest {

    @Test
    void main() {
        QuizRoundCollection quizRounds = new QuizRoundCollection();
        quizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));

        LocationCollection locations = new LocationCollection();
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        QuizSession quizSession = new QuizSession(quizRounds, locations.getLocation("domkyrkan"));

        assertNotNull(quizSession.getQuestion());
        assert(locations.getLocation("domkyrkan").equals(quizSession.getLocation()));
        assert(quizSession.getScore() == 0);

        String old_question = quizSession.getQuestion().getQuestion();
        QuizRound[] list_quiz = quizRounds.getAllQuizRounds();
        String answer_str = "";

        //find right answer to current question
        for(int i = 0; i < list_quiz.length; i++) {
            if(list_quiz[i].getQuestion().equals(old_question)) {
                answer_str = list_quiz[i].getAlternatives().get(0);
                break;
            }
        }

        assertTrue(quizSession.answer(answer_str));

        //new question
        assert(!old_question.equals(quizSession.getQuestion().getQuestion()));
        assertFalse(quizSession.isDone());

        assertFalse(quizSession.answer(answer_str));
        assertFalse(quizSession.isDone());

        //session done (3 questions)
        assertFalse(quizSession.answer(answer_str));
        assertTrue(quizSession.isDone());

        assert(quizSession.getScore() > 0);



    }
}