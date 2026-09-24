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

    Version version;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
        version = new Version();
    }

    @AfterEach
    public void tearDown() {
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
        Assertions.assertTrue(version.isEquals(26));
        Assertions.assertFalse(version.isEquals(20));
    }

    @Test
    public void testMatchRange() {
        Assertions.assertTrue(version.isInRange(18, 26));
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

    @Test
    public void isEqualsWithMinorVersionPartInModernVersion() {
        version.setServerVersion("MockBukkit (MC: 26.1)");
        Assertions.assertTrue(version.isEquals(26));
        Assertions.assertFalse(version.isEquals(1));
    }

    @Test
    public void decodeServerVersionWithoutTrailingParenthesis() {
        version.setServerVersion("MockBukkit (MC: 1.21");
        Assertions.assertNotNull(version.getFormattedServerVersion());
    }

    @Test
    public void decodeServerVersionNewFormat() {
        version.setServerVersion("MockBukkit (MC: 26)");
        Assertions.assertEquals("26", version.getFormattedServerVersion());
    }

    @Test
    public void toStringTest() {
        String a = version.toString();
        System.out.println(a);
        Assertions.assertEquals("Version{serverVersion='MockBukkit (MC: 26.2)', formattedServerVersion='26.2', splitFormattedServerVersion=[26, 2]}", a);
    }

    @Test
    public void isHigher() {
        version.setServerVersion("MockBukkit (MC: 26.2)");
        Assertions.assertFalse(version.isHigher(26));
        Assertions.assertTrue(version.isHigher(25));
        Assertions.assertFalse(version.isHigher(27));

        Assertions.assertFalse(version.isHigher(27, 1));
        Assertions.assertTrue(version.isHigher(26, 1));
        Assertions.assertFalse(version.isHigher(26, 2));

        version.setServerVersion("MockBukkit (MC: 1.21.11)");
        Assertions.assertFalse(version.isHigher(21));
        Assertions.assertTrue(version.isHigher(20));
        Assertions.assertFalse(version.isHigher(22));

        Assertions.assertTrue(version.isHigher(21, 10));
        Assertions.assertFalse(version.isHigher(21, 11));
    }

}
