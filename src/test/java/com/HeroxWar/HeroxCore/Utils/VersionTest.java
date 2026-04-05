package com.HeroxWar.HeroxCore.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.Arrays;

public class VersionTest {

    private ServerMock serverMock;

    //Fake Instances
    Version version;

    @BeforeEach
    public void setUp() {
        // Inizialization server and plugin
        serverMock = MockBukkit.mock();
        version = new Version();
        System.out.println(version.getFormattedServerVersion());
        System.out.println(Arrays.toString(version.getSplitFormattedServerVersion()));
        System.out.println(version.getServerVersion());
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server and Plugin
        MockBukkit.unmock();
    }

    @Test
    public void testLegacyLibraries() {
        version.setServerVersion("MockBukkit (MC: 1.21.11)");
        Assertions.assertFalse(version.isInRange(8, 16));
    }

    @Test
    public void testVersion() {
        version.setServerVersion("MockBukkit (MC: 1.21)");
        Assertions.assertEquals("MockBukkit (MC: 1.21)", version.getServerVersion());
        version.setFormattedServerVersion("36.12.1");
        Assertions.assertEquals("36.12.1", version.getFormattedServerVersion());
        version.setSplitFormattedServerVersion(new String[] {"36", "12", "1"});
        Assertions.assertArrayEquals(new String[] {"36", "12", "1"}, version.getSplitFormattedServerVersion());
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

    @Test
    public void testVersion26() {
        version.setServerVersion("MockBukkit (MC: 26.1)");
        Assertions.assertTrue(version.isEquals(26));
        Assertions.assertFalse(version.isEquals(25));
        Assertions.assertFalse(version.isEquals(27));
    }

    @Test
    public void testMatchRangeVersion26() {
        version.setServerVersion("MockBukkit (MC: 26.1.1)");
        Assertions.assertTrue(version.isInRange(21, 26));
        Assertions.assertFalse(version.isInRange(21, 25));
    }

}
