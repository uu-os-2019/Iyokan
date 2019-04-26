package com.iyokan.geocapserver;

import com.iyokan.geocapserver.FileReader;
import com.iyokan.geocapserver.LocationCollection;
import com.iyokan.geocapserver.route.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoutesQuizTest {

    @Test
    void handle() {

        RequestData data = new RequestData(new JSONObject(), null);

        SessionVault sessions = new SessionVault();
        UserCollection users = new UserCollection();
        Highscore hs = new Highscore();

        LocationCollection locations = new LocationCollection();
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        QuizRoundCollection quizRounds = new QuizRoundCollection();
        quizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));

        var testa = new User(Utils.generateUserGuid(), "Testa fiesta");
        sessions.insert("OsthyvelOsthyvelOsthyvelOsthyvel", testa);
        users.addUser(testa);

        Route route = new RouteQuizStart(quizRounds, locations);
        Route route_answer = new RouteQuizAnswer(quizRounds, hs);

        // /quiz/start without location
        JSONObject response = route.handle(data);
        assert(response.get("reason").equals("no user for token"));

        // /quiz/start with working location
        JSONObject working = new JSONObject();
        working.put("location", "domkyrkan");
        RequestData data2 = new RequestData(working,  testa);
        response = route.handle(data2);
        assert(response.get("success").equals(true));
        String question = response.get("question").toString();
        String answer_str = "";

        // /get correct answer to question
        QuizRound[] list_quiz = quizRounds.getAllQuizRounds();
        for(int i = 0; i < list_quiz.length; i++) {
            if(list_quiz[i].getQuestion().equals(question)) {
                answer_str = list_quiz[i].getAlternatives().get(0);
                break;
            }
        }

        // /quiz/answer with correct answer
        JSONObject answer = new JSONObject();
        answer.put("answer", answer_str);
        data = new RequestData(answer, testa);
        response = route_answer.handle(data);
        assert(response.get("correct").equals(true));
        assert(!response.get("new_question").equals(JSONObject.NULL));

        // quiz/answer with wrong answer
        JSONObject answer2 = new JSONObject();
        answer2.put("answer", "wrong_answer");
        data = new RequestData(answer2, testa);
        response = route_answer.handle(data);
        assert(response.get("correct").equals(false));
        assert(!response.get("new_question").equals(JSONObject.NULL));


        // quiz/answer with wrong answer, no more question
        JSONObject answer3 = new JSONObject();
        answer3.put("answer", "wrong_answer");
        data = new RequestData(answer3, testa);
        response = route_answer.handle(data);
        assert(response.get("correct").equals(false));
        assert(response.get("new_question").equals(JSONObject.NULL));

        // quiz/answer with no session
        JSONObject answer4 = new JSONObject();
        answer4.put("answer", "wrong_answer");
        data = new RequestData(answer4, testa);
        response = route_answer.handle(data);
        assert(response.get("success").equals(false));



    }
}