package com.HeroxWar.HeroxCore.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.*;

public class NmsTest {

    private ServerMock serverMock;

    Nms nms;
    PlayerMock playerMock;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
        nms = new Nms();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testCreation() {
        assertEquals("Nms{packageName='org.mockbukkit.mockbukkit', nms='.', nmsVersion=true, brigadierIsActive=false, minecraftServerClass=null}", nms.toString());
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

    @Test
    public void testGetNmsClass() {
        assertThrows(ClassNotFoundException.class, () -> nms.getNMSClass("org.bukkit.craftbukkit.__VERSION__.entity.CraftPlayer"));
    }

    @Test
    public void constructorWithCraftBukkitPackageSetsNmsDot() {
        Nms customNms = new Nms();
        customNms.setPackageName("org.bukkit.craftbukkit");
        assertEquals("org.bukkit.craftbukkit", customNms.getPackageName());
    }

    @Test
    public void constructorWithSplitPackageSetsVersionedNms() {
        Nms customNms = new Nms();
        customNms.setPackageName("org.bukkit.craftbukkit.v1_20_R1");
        assertEquals("org.bukkit.craftbukkit.v1_20_R1", customNms.getPackageName());
    }

}
