package com.iyokan.geocapserver;

import org.json.JSONObject;

public class QuizRoundTag {
    public final String tag;
    public final int weight;
    public final QuizRound round;

    public QuizRoundTag(String tag, int weight, QuizRound round) {
        this.tag = tag;
        this.weight = weight;
        this.round = round;
    }
}
