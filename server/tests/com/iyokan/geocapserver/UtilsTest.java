package com.iyokan.geocapserver;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void generateUserGuid() {
        // Generate a fuckton of guids, and make sure they're all unique

        HashSet<UserGuid> guids = new HashSet<>();

        for (int i=0;i < 100000; i++) {
            UserGuid guid = Utils.generateUserGuid();
            if (guids.contains(guid)) {
                assertTrue(false);
            }
            guids.add(guid);
        }

        assertTrue(true);
    }
}