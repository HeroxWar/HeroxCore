package com.HeroxWar.HeroxCore;

import org.bukkit.plugin.InvalidDescriptionException;
import org.junit.jupiter.api.Assertions;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.HashMap;

public class ReloadGestureTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setUp() {
        // Inizialization server and plugin
        serverMock = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server and Plugin
        MockBukkit.unmock();
    }

    @Test
    public void load() {
        ReloadGesture.load("CubeGenerator");
    }

    public void unload() {
        ReloadGesture.unload("CubeGenerator");
    }

    @Test
    public void testReload() {
        ReloadGesture.reload("CubeGenerator");
    }

    @Test
    public void testPluginDescription() {
        Assertions.assertThrows(InvalidDescriptionException.class, () -> ReloadGesture.getPluginDescription(new File("CubeGenerator")));
    }

    @Test
    public void testPluginDescriptionWhenNull() {
        Assertions.assertThrows(InvalidDescriptionException.class, () -> ReloadGesture.getPluginDescription(null));
    }

    @Test
    public void testGetCommandsFromPlugin() {
        Assertions.assertTrue(ReloadGesture.getCommandsFromPlugin(null).isEmpty());
    }

    @Test
    public void testLoadCommands() {
        ReloadGesture.loadCommands(null);
    }

    @Test
    public void testSetKnownCommands() {
        ReloadGesture.setKnownCommands(new HashMap<>());
    }

    @Test
    public void testWrap() {
        ReloadGesture.wrap(null,"");
    }

    @Test
    public void testUnwrap() {
        ReloadGesture.unwrap("");
    }

    @Test
    public void testSync() {
        ReloadGesture.sync();
    }
}
