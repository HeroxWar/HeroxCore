package com.HeroxWar.HeroxCore.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public class VersionTest {

    private ServerMock serverMock;

    //Fake Instances
    Version version;

    @BeforeEach
    public void setUp() {
        // Inizialization server and plugin
        serverMock = MockBukkit.mock();
        version = new Version();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server and Plugin
        MockBukkit.unmock();
    }

    @Test
    public void testVersion() {
        Assertions.assertEquals("MockBukkit (MC: 1.21.4)", version.getServerVersion());
        version.setServerVersion("MockBukkit (MC: 1.21)");
        Assertions.assertEquals("MockBukkit (MC: 1.21)", version.getServerVersion());
    }

    @Test
    public void testMatch() {
        Assertions.assertTrue(version.isEquals(21));
        Assertions.assertFalse(version.isEquals(20));
    }

    @Test
    public void testMatchRange() {
        Assertions.assertTrue(version.isInRange(18, 21));
        Assertions.assertFalse(version.isInRange(18, 20));
    }

}
