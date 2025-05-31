package com.HeroxWar.HeroxCore;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageGestureTest {

    private static ServerMock serverMock;

    @BeforeEach
    public void setUp() {
        // Inizialization server
        serverMock = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void sendMessage() {
        MessageGesture.sendMessage(serverMock.getConsoleSender(), "&aHello");
    }

    @Test
    public void sendEmptyMessage() {
        MessageGesture.sendMessage(serverMock.getConsoleSender(), "");
    }

    @Test
    public void sendRGBMessage() {
        MessageGesture.sendMessage(serverMock.getConsoleSender(), "&{#FFFF00}Hello");
    }

    @Test
    public void transformColor() {
        String transformed = MessageGesture.applyColor("&aHello");
        Assertions.assertEquals("§aHello", transformed);
    }

    @Test
    public void transformRGBColor() {
        String transformed = MessageGesture.applyColor("&{#FFFF00}Hello");
        Assertions.assertEquals("§x§F§F§F§F§0§0Hello", transformed);
    }

}
