package com.iyokan.geocapserver;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizRoundTagList {
    private int totalWeight;
    private ArrayList<QuizRoundTag> tags;

    public QuizRoundTagList() {
        tags = new ArrayList<>();
        totalWeight = 0;
    }

    public void addEntry(QuizRoundTag tag) {
        tags.add(tag);
        totalWeight += tag.weight;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getCount() {
        return tags.size();
    }

    public String getTag() {
        return tags.get(0).tag;
    }

    public ArrayList<QuizRoundTag> getEntries() {
        return tags;
    }
}
