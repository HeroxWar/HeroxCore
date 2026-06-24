package com.HeroxWar.HeroxCore;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.util.ArrayList;
import java.util.List;

public class EventsTest {

    private static ServerMock serverMock;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testEvent() {
        Assertions.assertThrows(NullPointerException.class, () -> new Events(null, new ArrayList<>()));
    }

    @Test
    public void testEventWithIgnore() {
        List<String> events = new ArrayList<>();
        events.add("BlockBreakEvent");
        Assertions.assertThrows(NullPointerException.class, () -> new Events(null, events));
    }

    @Test
    public void testDiscoveredEvents() {
        Events events = new Events();
        ClassGraph classGraph = events.initializeScan();
        ClassInfoList classInfos = events.getListEvents(classGraph.scan());
        events.discoveredEvents(classInfos);
    }

    @Test
    public void testEventsIsListener() {
        Events events = new Events();
        Assertions.assertInstanceOf(org.bukkit.event.Listener.class, events);
    }

    @Test
    public void testInitializeScansBukkitEvents() {
        Events events = new Events();
        ClassGraph classGraph = events.initializeScan();
        ClassInfoList classInfos = events.getListEvents(classGraph.scan());
        Assertions.assertFalse(classInfos.isEmpty());
    }

}
