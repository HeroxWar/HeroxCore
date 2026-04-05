package com.HeroxWar.HeroxCore;

import com.test.utils.PrintMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.Arrays;

public class MessageGesturePaperTest {

    private static ServerMock serverMock;

    // Fake instances
    MessageGesturePaper messageGesturePaper;
    PlayerMock playerMock;
    PrintMessage printMessage;

    @BeforeEach
    public void setUp() {
        // Inizialization server
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
        printMessage = new PrintMessage(serverMock);
        messageGesturePaper = new MessageGesturePaper("&c&lHeroxCore", false, false, null);
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void testInternalLogger() {
        messageGesturePaper.sendBroadcast("&7&lTest");
        System.out.println(Arrays.toString(printMessage.getAllMessages(playerMock).toArray()));
    }

    @Test
    public void testConsoleMessage() {
        messageGesturePaper.sendMessage("&7&lTest");
        System.out.println(Arrays.toString(printMessage.getAllMessages(serverMock.getConsoleSender()).toArray()));
    }

}
