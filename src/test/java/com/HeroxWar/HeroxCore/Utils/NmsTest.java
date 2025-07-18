package com.HeroxWar.HeroxCore.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.*;

public class NmsTest {

    private ServerMock serverMock;

    // Fake Instance
    Nms nms;

    @BeforeEach
    public void setUp() {
        // Inizialization server and plugin
        serverMock = MockBukkit.mock();
        nms = new Nms();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server and Plugin
        MockBukkit.unmock();
    }

    @Test
    public void testCreation() {
        assertEquals("Nms{packageName='org.mockbukkit.mockbukkit', nms='.', nmsVersion=true, brigadierIsActive=false, minecraftServerClass=null}",nms.toString());
    }

    @Test
    public void testSetAndGetNms() {
        String expected = ".v1_20_R1.";
        nms.setNms(expected);
        assertEquals(expected, nms.getNms());
    }

    @Test
    public void testSetAndGetNmsVersion() {
        nms.setNmsVersion(true);
        assertTrue(nms.isNmsVersion());

        nms.setNmsVersion(false);
        assertFalse(nms.isNmsVersion());
    }

    @Test
    public void testSetAndGetBrigadierIsActive() {
        nms.setBrigadierIsActive(true);
        assertTrue(nms.isBrigadierIsActive());

        nms.setBrigadierIsActive(false);
        assertFalse(nms.isBrigadierIsActive());
    }

    @Test
    public void testSetAndGetMinecraftServerClass() {
        Class<?> clazz = String.class;
        nms.setMinecraftServerClass(clazz);
        assertEquals(clazz, nms.getMinecraftServerClass());
    }

    @Test
    public void testSetAndGetPackageName() {
        String pkg = "org.bukkit.craftbukkit.v1_20_R1";
        nms.setPackageName(pkg);
        assertEquals(pkg, nms.getPackageName());
    }

    @Test
    public void testToStringNotNull() {
        assertNotNull(nms.toString());
    }

}
