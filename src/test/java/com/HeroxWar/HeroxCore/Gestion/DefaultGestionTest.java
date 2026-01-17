package com.HeroxWar.HeroxCore.Gestion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultGestionTest {

    private static ServerMock serverMock;

    private static final Path resourcesPathTest = Paths.get(".", "src", "test", "resources").normalize().toAbsolutePath();

    DefaultGestion gestion;

    @BeforeEach
    public void setUp() {
        // Initialization server
        serverMock = MockBukkit.mock();

        gestion = new DefaultGestion(resourcesPathTest.toString(), "config.yml");
        Assertions.assertEquals("config.yml", gestion.getConfigFile().getName());
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void newDefaultGestion() {
        Assertions.assertThrows(NullPointerException.class, () -> gestion = new DefaultGestion(resourcesPathTest.toString(), "config.yml", "Tombs"));
    }

    @Test
    public void defaultInformationsEmptyConfig() {
        gestion.defaultInformations();
        Assertions.assertTrue(gestion.getMessages().isEmpty());
        Assertions.assertTrue(gestion.getDebug().isEmpty());
    }

    @Test
    public void defaultInformations() {
        gestion.saveSection("Messages.Prefix", "&a&lPrefix");
        gestion.saveSection("Messages.Success.Buy", "&a&lBuy");
        gestion.saveSection("Messages.Success.Accept", "&a&lAccept");

        gestion.saveSection("Debug.Enabled", true);
        gestion.saveSection("Debug.Disabled", false);

        gestion.saveSection("Configuration.Hooks.PlaceholderAPI", true);
        gestion.saveSection("Configuration.Hooks.WorldGuard", false);
        gestion.saveSection("Configuration.Hooks.Lands.Enabled", false);
        gestion.saveSection("Configuration.Hooks.Lands.Buy", "BuyText");

        gestion.defaultInformations();

        Map<String, String> messages = gestion.getMessages();
        System.out.println(messages);
        Assertions.assertEquals("&a&lPrefix", messages.get("Prefix"));
        Assertions.assertEquals("&a&lBuy", messages.get("Success.Buy"));
        Assertions.assertEquals("&a&lAccept", messages.get("Success.Accept"));
        messages.replace("Prefix", "Prefix");
        messages.replace("Success.Buy", "Buy");
        messages.replace("Success.Accept", "Accept");
        gestion.setMessages(messages);
        messages = gestion.getMessages();
        Assertions.assertEquals("Prefix", messages.get("Prefix"));
        Assertions.assertEquals("Buy", messages.get("Success.Buy"));
        Assertions.assertEquals("Accept", messages.get("Success.Accept"));

        Map<String, Boolean> debug = gestion.getDebug();
        System.out.println(debug);
        Assertions.assertTrue(debug.get("Enabled"));
        Assertions.assertFalse(debug.get("Disabled"));
        debug.replace("Enabled", false);
        debug.replace("Disabled", true);
        gestion.setDebug(debug);
        debug = gestion.getDebug();
        Assertions.assertFalse(debug.get("Enabled"));
        Assertions.assertTrue(debug.get("Disabled"));

        Map<String,Boolean> hooks = gestion.getHooks();
        System.out.println(hooks);
        Assertions.assertTrue(hooks.get("PlaceholderAPI"));
        Assertions.assertFalse(hooks.get("WorldGuard"));
        Assertions.assertFalse(hooks.get("Lands"));
        hooks.replace("PlaceholderAPI", false);
        hooks.replace("WorldGuard", true);
        hooks.replace("Lands", true);
        gestion.setHooks(hooks);
        hooks = gestion.getHooks();
        Assertions.assertTrue(hooks.get("WorldGuard"));
        Assertions.assertFalse(hooks.get("PlaceholderAPI"));
        Assertions.assertTrue(hooks.get("Lands"));

        gestion.saveSection("Messages", null);
        gestion.saveSection("Debug", null);
        gestion.saveSection("Configuration", null);
    }

    @Test
    public void listMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("0");
        messages.add("1");
        messages.add("2");
        gestion.saveSection("Messages.Top", messages);

        gestion.defaultInformations();

        Map<String, String> messagesMap = gestion.getMessages();
        Assertions.assertEquals("0", messagesMap.get("Top1"));
        Assertions.assertEquals("1", messagesMap.get("Top2"));
        Assertions.assertEquals("2", messagesMap.get("Top3"));

        gestion.saveSection("Messages", null);
    }

    @Test
    public void reloadConfiguration() {
        gestion.setFc(gestion.getFc());
        gestion.reloadDefaultConfiguration(true);
        Assertions.assertThrows(NullPointerException.class, () -> gestion = gestion.reloadDefaultConfiguration(false));
    }

}
