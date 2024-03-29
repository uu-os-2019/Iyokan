package com.iyokan.geocapserver;

import com.iyokan.geocapserver.testutils.DummyDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionVaultTest {

    @Test
    void testInsertion() {

        SessionVault vault = new SessionVault(new DummyDatabase(), null);

        UserGuid rndGuid = Utils.generateUserGuid();

        vault.insert("OsthyvelOsthyvelOsthyvelOsthyvel", new User(rndGuid, "galne-steffe"));

        User vaultedUser = vault.getUser("OsthyvelOsthyvelOsthyvelOsthyvel");
        assertNotNull(vaultedUser);

        assertNull(vault.getUser("lalalalala"));

        rndGuid = Utils.generateUserGuid();
        vault.insert("BrödsmörBrödsmörBrödsmörBrödsmör", new User(rndGuid, "normale-steffe"));

        assertNotNull(vault.getUser("BrödsmörBrödsmörBrödsmörBrödsmör"));
    }
}