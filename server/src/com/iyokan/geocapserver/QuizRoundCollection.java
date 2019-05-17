package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

public class QuizRoundCollection {
    private HashMap< String, QuizRoundTagList> tagSet;

    public QuizRoundCollection(){
        tagSet = new HashMap<>();
    }

    private ArrayList<QuizRoundTag> getTags(JSONObject obj, QuizRound round) {
        ArrayList<QuizRoundTag> tags = new ArrayList<>();
        Iterator<String> keys = obj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            QuizRoundTag tag = new QuizRoundTag(key, obj.getInt(key), round);
            tags.add(tag);
        }
        return tags;
    }

    public void loadQuizRounds(JSONArray array) {
        for (int i=0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            QuizRound quizRound = new QuizRound(jsonObject);

            ArrayList<QuizRoundTag> tags = getTags(jsonObject.getJSONObject("tags"), quizRound);
            for (QuizRoundTag tag: tags) {
                QuizRoundTagList list;
                // If the tag is unrecorded put it in the map
                if (tagSet.containsKey(tag.tag) == false) {
                    list = new QuizRoundTagList();
                    tagSet.put(tag.tag, list);
                } else {
                    list = tagSet.get(tag.tag);
                }
                list.addEntry(tag);
            }
        }


    }

    /**
     * Selects some random quiz rounds that matches the tags
     * @param count
     * @return
     */
    public QuizRound[] getRandomQuizRounds(LocationTag[] tags, int count) {
        int sum = 0;
        int total = 0;

        QuizRound[] selectedRounds = new QuizRound[count];

        for(LocationTag tag : tags) {
            QuizRoundTagList list = tagSet.get(tag.tag);
            if (list == null) {
                continue;
            }

            // Add to the sum
            sum += tag.weight * list.getTotalWeight();
            total += list.getCount();
        }

        // Add general tags to the sum so they can be selected
        QuizRoundTagList generalTags = tagSet.get("general");
        sum += 10*generalTags.getTotalWeight();
        total += generalTags.getCount();

        if (total < count) {
            throw new RuntimeException("Error in selecting question, count is less than available questions");
        }

        int selectedCount = 0;
        Random rnd = new Random();

        while (selectedCount < count) {
            int value = rnd.nextInt(sum);
            QuizRoundTagList list = null;
            int locationTagWeight = 0;

            // Go to the taglist we selected
            for (LocationTag tag : tags) {
                list = tagSet.get(tag.tag);
                locationTagWeight = tag.weight;
                int newValue = value - locationTagWeight * list.getTotalWeight();
                if (newValue <= 0) {
                    break;
                }
                value = newValue;
            }

            // If value is still not under 0, move to general questions
            if (value > 0) {
                list = generalTags;
                locationTagWeight = 10;
            }

            QuizRound round = null;

            // Loop through the list we selected
            for (QuizRoundTag tag : list.getEntries()) {
                round = tag.round;
                int newValue = value - tag.weight * locationTagWeight;

                if (newValue <= 0) {
                    break;
                }
                value = newValue;
            }

            // If we didn't select this question before, select it
            if (Utils.arrayContains(selectedRounds, round) == false) {
                selectedRounds[selectedCount++] = round;
            }

        }

        return selectedRounds;
    }
}
