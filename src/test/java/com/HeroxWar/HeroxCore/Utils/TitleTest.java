package com.HeroxWar.HeroxCore.Utils;

import com.HeroxWar.HeroxCore.MessageGesture.MessageGesturePaper;
import com.test.utils.TestLogHandler;
import org.bukkit.Bukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TitleTest {

    private ServerMock serverMock;
    private PlayerMock playerMock;
    private MessageGesturePaper messageGesturePaper;
    private MessageGesturePaper msgWithPlugin;
    private TestLogHandler logHandler;
    private List<String> logMessages;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
        messageGesturePaper = new MessageGesturePaper("&c&lTest", false, false, null);
        PluginMock plugin = MockBukkit.createMockPlugin();
        msgWithPlugin = new MessageGesturePaper(true, false, plugin);

        logMessages = new ArrayList<>();
        logHandler = new TestLogHandler(logMessages);
        Bukkit.getLogger().addHandler(logHandler);
        logMessages.clear();
    }

    @AfterEach
    public void tearDown() {
        if (logHandler != null) {
            Bukkit.getLogger().removeHandler(logHandler);
        }
        MockBukkit.unmock();
    }

    @Test
    public void constructorDefault() {
        Title title = new Title(messageGesturePaper);
        Assertions.assertEquals("", title.getTitle());
        Assertions.assertEquals("", title.getSubtitle());
    }

    @Test
    public void constructorWithTitle() {
        Title title = new Title(messageGesturePaper, "&aHello");
        Assertions.assertEquals("&aHello", title.getTitle());
        Assertions.assertEquals("", title.getSubtitle());
    }

    @Test
    public void constructorWithTitleAndSubtitle() {
        Title title = new Title(messageGesturePaper, "&aTitle", "&bSub");
        Assertions.assertEquals("&aTitle", title.getTitle());
        Assertions.assertEquals("&bSub", title.getSubtitle());
    }

    @Test
    public void constructorCopy() {
        Title original = new Title(messageGesturePaper, "&cOrig", "&dSub", 10, 20, 30);
        Title copy = new Title(messageGesturePaper, original);
        Assertions.assertEquals("&cOrig", copy.getTitle());
        Assertions.assertEquals("&dSub", copy.getSubtitle());
    }

    @Test
    public void constructorFull() {
        Title title = new Title(messageGesturePaper, "&eFull", "&fSub", 5, 100, 15);
        Assertions.assertEquals("&eFull", title.getTitle());
        Assertions.assertEquals("&fSub", title.getSubtitle());
    }

    @Test
    public void setterAndGetter() {
        Title title = new Title(messageGesturePaper);
        title.setTitle("&aNewTitle");
        title.setSubtitle("&bNewSub");
        Assertions.assertEquals("&aNewTitle", title.getTitle());
        Assertions.assertEquals("&bNewSub", title.getSubtitle());
    }

    @Test
    public void setFadeTimes() {
        Title title = new Title(messageGesturePaper);
        title.setFadeInTime(5);
        title.setStayTime(100);
        title.setFadeOutTime(15);
    }

    @Test
    public void send() {
        Title title = new Title(messageGesturePaper, "&aHello", "&bWorld");
        title.send(playerMock);
    }

    @Test
    public void sendAll() {
        Title title = new Title(messageGesturePaper, "&aAll", "&bSub");
        title.sendAll();
    }

    @Test
    public void sendRich() {
        Title title = new Title(msgWithPlugin, "&aRich", "&bSub");
        title.sendRich(playerMock);
    }

    @Test
    public void sendAllRich() {
        Title title = new Title(msgWithPlugin, "&aRichAll", "&bSub");
        title.sendAllRich();
    }

    @Test
    public void sendRichWithNullAdventure() {
        Title title = new Title(messageGesturePaper, "&aFail", "&bSub");
        title.sendRich(playerMock);
    }

    @Test
    public void sendWithFadeTimes() {
        Title title = new Title(messageGesturePaper, "&aTimed", "&bSub", 10, 50, 20);
        title.send(playerMock);
    }

}
