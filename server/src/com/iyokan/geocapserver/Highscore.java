package com.iyokan.geocapserver;

import java.util.*;

public class Highscore {
    private HashMap<UserGuid, Integer> highscore;

    public Highscore() {
        this.highscore = new HashMap<>();
    }

    public void updateHighscore(UserGuid guid, Integer score) {
        this.highscore.put(guid, score);
    }

    public Integer getUserHighscore(UserGuid guid) {
        if(this.highscore.containsKey(guid)) {
            return this.highscore.get(guid);
        } else {
            return 0;
        }
    }

    public TreeMap<Integer, UserGuid> getHighscore(int amount) {
        /*TopsCollection<UserGuid> tc = new TopsCollection<>();
        tc.add(12, new UserGuid());
        tc.add(1, new UserGuid());
        tc.add(7, new UserGuid());
        tc.add(4, new UserGuid());

        List<UserGuid> tops = tc.getTops(amount);*/
        SortedMap<Integer, UserGuid> myMap = new TreeMap<Integer, UserGuid>();

        myMap.put(30, new UserGuid("0000000000000001"));
        myMap.put(30, new UserGuid("0000000000000002"));

        TreeMap<Integer, UserGuid> myNewMap = myMap.entrySet().stream()
                .limit(amount)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        return myNewMap;


    }
}
