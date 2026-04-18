package com.HeroxWar.HeroxCore;

import com.HeroxWar.HeroxCore.Utils.ColoredLogger;
import com.test.utils.PrintMessage;
import com.test.utils.TestLogHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

public class MessageGesturePaperTest {

    private static ServerMock serverMock;

    // Fake instances
    MessageGesturePaper messageGesturePaper;
    PlayerMock playerMock;
    PrintMessage printMessage;
    
    // Test logger handler to capture console messages
    private TestLogHandler testLogHandler;
    private List<String> consoleMessages;

    @BeforeEach
    public void setUp() {
        // Inizialization server
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
        printMessage = new PrintMessage(serverMock);
        messageGesturePaper = new MessageGesturePaper("&c&lHeroxCore&r", false, false, null);
        
        // Setup console message capture
        consoleMessages = new ArrayList<>();
        testLogHandler = new TestLogHandler(consoleMessages);
        // ColoredLogger uses Bukkit.getLogger() internally, so we add handler to the global logger
        Bukkit.getLogger().addHandler(testLogHandler);
    }

    @AfterEach
    public void tearDown() {
        // Remove test handler from the global logger
        if (testLogHandler != null) {
            Bukkit.getLogger().removeHandler(testLogHandler);
        }
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void testInternalLogger() {
        messageGesturePaper.sendBroadcast("&7&lTest");
        Assertions.assertEquals("§c§lHeroxCore§7§lTest", printMessage.getMessage(playerMock));
    }

    // ========== Constructor Tests ==========
    
    @Test
    public void testConstructorWithPlugin() {
        PluginMock plugin = MockBukkit.createMockPlugin();
        MessageGesturePaper msg = new MessageGesturePaper(true, false, plugin);
        
        Assertions.assertTrue(msg.isPrintDebug());
        Assertions.assertFalse(msg.isPlaceholderAPIEnabled());
        Assertions.assertNotNull(msg.getAdventure());
        Assertions.assertNotNull(msg.getInternalLogger());
        Assertions.assertTrue(msg.getDebugPrefixSuffix().contains(plugin.getName()));
    }
    
    @Test
    public void testConstructorWithPrefix() {
        MessageGesturePaper msg = new MessageGesturePaper("&aTest", true, true, null);
        
        Assertions.assertEquals("&aTest", msg.getPrefix());
        Assertions.assertTrue(msg.isPrintDebug());
        Assertions.assertTrue(msg.isPlaceholderAPIEnabled());
        Assertions.assertTrue(msg.getDebugPrefixSuffix().contains("Test"));
    }
    
    @Test
    public void testConstructorWithNullPlugin() {
        MessageGesturePaper msg = new MessageGesturePaper(false, true, null);
        
        Assertions.assertFalse(msg.isPrintDebug());
        Assertions.assertTrue(msg.isPlaceholderAPIEnabled());
        Assertions.assertNull(msg.getAdventure());
        Assertions.assertTrue(msg.getDebugPrefixSuffix().contains("HeroxPlugin"));
    }
    
    // ========== Getter and Setter Tests ==========
    
    @Test
    public void testGetSetPrefix() {
        messageGesturePaper.setPrefix("&bNewPrefix");
        Assertions.assertEquals("&bNewPrefix", messageGesturePaper.getPrefix());
    }
    
    @Test
    public void testGetSetPrintDebug() {
        messageGesturePaper.setPrintDebug(true);
        Assertions.assertTrue(messageGesturePaper.isPrintDebug());
        
        messageGesturePaper.setPrintDebug(false);
        Assertions.assertFalse(messageGesturePaper.isPrintDebug());
    }
    
    @Test
    public void testGetSetPlaceholderAPIEnabled() {
        messageGesturePaper.setPlaceholderAPIEnabled(true);
        Assertions.assertTrue(messageGesturePaper.isPlaceholderAPIEnabled());
        
        messageGesturePaper.setPlaceholderAPIEnabled(false);
        Assertions.assertFalse(messageGesturePaper.isPlaceholderAPIEnabled());
    }
    
    @Test
    public void testGetSetDebugPrefixSuffix() {
        String original = messageGesturePaper.getDebugPrefixSuffix();
        messageGesturePaper.setDebugPrefixSuffix("TestPrefix");
        String updated = messageGesturePaper.getDebugPrefixSuffix();
        
        // Il metodo setDebugPrefixSuffix non usa il parametro ma ricostruisce con il prefix corrente
        // Quindi il valore non cambia se il prefix non cambia
        Assertions.assertEquals(original, updated);
        Assertions.assertTrue(updated.contains(messageGesturePaper.getPrefix()));
    }
    
    @Test
    public void testGetSetInternalLogger() {
        ColoredLogger newLogger = new ColoredLogger("[Test] ");
        messageGesturePaper.setInternalLogger(newLogger);
        Assertions.assertEquals(newLogger, messageGesturePaper.getInternalLogger());
    }
    
    @Test
    public void testSetInternalLoggerWithString() {
        messageGesturePaper.setInternalLogger("TestLogger");
        ColoredLogger logger = messageGesturePaper.getInternalLogger();
        Assertions.assertNotNull(logger);
    }
    
    @Test
    public void testGetSetAdventure() {
        messageGesturePaper.setAdventure(null);
        Assertions.assertNull(messageGesturePaper.getAdventure());
    }
    
    // ========== Message Sending Tests ==========
    
    @Test
    public void testSendMessageToPlayerWithoutPrefix() {
        messageGesturePaper.sendMessage(playerMock, "&eHello Player!");
        Assertions.assertEquals("§eHello Player!", printMessage.getMessage(playerMock));
    }
    
    @Test
    public void testSendMessageToConsole() {
        messageGesturePaper.sendMessage("&eHello Console!");
        // Verifica che il messaggio venga inviato alla console
        // Il test passa se non ci sono eccezioni
    }
    
    @Test
    public void testSendBroadcastWithMultiplePlayers() {
        PlayerMock player2 = serverMock.addPlayer();
        messageGesturePaper.sendBroadcast("&6Broadcast Message");
        
        Assertions.assertEquals("§c§lHeroxCore§6Broadcast Message", printMessage.getMessage(playerMock));
        Assertions.assertEquals("§c§lHeroxCore§6Broadcast Message", printMessage.getMessage(player2));
    }
    
    @Test
    public void testSendBroadcastWithNoPlayers() {
        playerMock.kick();

        // Non dovrebbe lanciare eccezioni
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.sendBroadcast("&6Test"));
    }
    
    // ========== Color Application Tests ==========
    
    @Test
    public void testApplyColor() {
        Component result = messageGesturePaper.applyColor("&aGreen &bBlue");
        Assertions.assertNotNull(result);
    }
    
    @Test
    public void testApplyColorWithHex() {
        Component result = messageGesturePaper.applyColor("&#FF0000Red");
        Assertions.assertNotNull(result);
    }
    
    @Test
    public void testApplyColorWithEscapedChars() {
        Component result = messageGesturePaper.applyColor("Test \\<escaped\\>");
        Assertions.assertNotNull(result);
    }
    
    @Test
    public void testApplyColorLegacy() {
        String result = messageGesturePaper.applyColorLegacy("&aGreen &bBlue");
        Assertions.assertEquals("§aGreen §bBlue", result);
    }
    
    @Test
    public void testApplyColorLegacyWithHex() {
        String result = messageGesturePaper.applyColorLegacy("&#FF0000Red &aGreen");
        Assertions.assertTrue(result.contains("Red") && result.contains("Green"));
    }
    
    @Test
    public void testApplyColorLegacyWithNoColors() {
        String result = messageGesturePaper.applyColorLegacy("Plain text");
        Assertions.assertEquals("Plain text", result);
    }
    
    // ========== Translation Tests ==========
    
    @Test
    public void testTranslateWithPlayer() {
        String result = messageGesturePaper.translate(playerMock, "Test message");
        Assertions.assertEquals("Test message", result);
    }
    
    @Test
    public void testTranslateWithCommandSender() {
        String result = messageGesturePaper.translate((CommandSender) playerMock, "Test message");
        Assertions.assertEquals("Test message", result);
    }
    
    @Test
    public void testTranslateWithConsole() {
        String result = messageGesturePaper.translate(Bukkit.getConsoleSender(), "Test message");
        Assertions.assertEquals("Test message", result);
    }
    
    @Test
    public void testTranslateWithOfflinePlayer() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerMock.getName());
        String result = messageGesturePaper.translate(offlinePlayer, "Test message");
        Assertions.assertEquals("Test message", result);
    }
    
    @Test
    public void testTranslateWithPlaceholderAPIDisabled() {
        messageGesturePaper.setPlaceholderAPIEnabled(false);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerMock.getName());
        
        String result = messageGesturePaper.translate(offlinePlayer, "Test %player_name% message");
        Assertions.assertEquals("Test %player_name% message", result);
    }

    @Test
    public void testTranslateWithNoPlayers() {
        playerMock.kick();

        String result = messageGesturePaper.translate("Test message");
        Assertions.assertEquals("Test message", result);
    }

    @Test
    public void testTranslateWithPlayerNotPassed() {
        serverMock.addPlayer();
        String result = messageGesturePaper.translate("Test message");
        Assertions.assertEquals("Test message", result);
    }

    // ========== Logging Tests ==========
    
    @Test
    public void testLogDebugWithDebugEnabled() {
        messageGesturePaper.setPrintDebug(true);
        
        // Non dovrebbe lanciare eccezioni
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.logDebug("Debug message"));
    }
    
    @Test
    public void testLogDebugWithDebugDisabled() {
        messageGesturePaper.setPrintDebug(false);
        
        // Non dovrebbe lanciare eccezioni né loggare
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.logDebug("Debug message"));
    }
    
    @Test
    public void testLogWithDifferentLevels() {
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.log("Info message", Level.INFO));
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.log("Warning message", Level.WARNING));
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.log("Severe message", Level.SEVERE));
    }
    
    // ========== Edge Cases ==========
    
    @Test
    public void testSetPrefixWithNull() {
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.setPrefix(null));
    }
    
    @Test
    public void testSetInternalLoggerWithNull() {
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.setInternalLogger((ColoredLogger) null));
    }
    
    @Test
    public void testSetInternalLoggerStringWithNull() {
        Assertions.assertDoesNotThrow(() -> messageGesturePaper.setInternalLogger((String) null));
    }
    
    @Test
    public void testConstructorWithAllNullParameters() {
        MessageGesturePaper msg = new MessageGesturePaper(false, false, null);
        Assertions.assertNotNull(msg);
        Assertions.assertFalse(msg.isPrintDebug());
        Assertions.assertFalse(msg.isPlaceholderAPIEnabled());
    }
    
    @Test
    public void testConstructorWithNullPrefix() {
        MessageGesturePaper msg = new MessageGesturePaper(null, true, false, null);
        Assertions.assertNotNull(msg);
        Assertions.assertTrue(msg.isPrintDebug());
        Assertions.assertFalse(msg.isPlaceholderAPIEnabled());
    }
    
    @Test
    public void testSendMessageToConsoleWithPrefix() {
        messageGesturePaper.sendMessage("&6Console message with prefix");
        
        // Verifica che il messaggio venga catturato nella console
        Assertions.assertFalse(consoleMessages.isEmpty(), "Nessun messaggio catturato nella console");
        
        String capturedMessage = consoleMessages.get(consoleMessages.size() - 1);
        
        // Il ColoredLogger converte i codici colore in ANSI, quindi verifichiamo il testo senza codici colore
        Assertions.assertTrue(capturedMessage.contains("HeroxCore"), 
            "Il messaggio non contiene il prefisso atteso: " + capturedMessage);
        Assertions.assertTrue(capturedMessage.contains("Console message with prefix"), 
            "Il messaggio non contiene il contenuto atteso: " + capturedMessage);
    }
    
    @Test
    public void testSendMessageToConsoleSender() {
        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        messageGesturePaper.sendMessage(consoleSender, "&eDirect console message");
        
        // Verifica che il messaggio venga catturato nella console
        Assertions.assertFalse(consoleMessages.isEmpty(), "Nessun messaggio catturato nella console");
        
        String capturedMessage = consoleMessages.get(consoleMessages.size() - 1);
        
        // Verifica che il messaggio contenga il contenuto corretto
        Assertions.assertTrue(capturedMessage.contains("Direct console message"), 
            "Il messaggio non contiene il contenuto atteso: " + capturedMessage);
    }
    
    @Test
    public void testSendMessageToConsoleWithColors() {
        messageGesturePaper.sendMessage("&aGreen &bBlue &cRed console message");
        
        // Verifica che il messaggio venga catturato nella console
        Assertions.assertFalse(consoleMessages.isEmpty(), "Nessun messaggio catturato nella console");

        String capturedMessage = consoleMessages.get(consoleMessages.size() - 1);
        
        // Debug: stampa il messaggio catturato
        System.out.println("Captured message: [" + capturedMessage + "]");

        // Verifica che il messaggio contenga il prefisso e il contenuto corretto
        Assertions.assertTrue(capturedMessage.contains("HeroxCore"),
            "Il messaggio non contiene il prefisso atteso: " + capturedMessage);
        Assertions.assertTrue(capturedMessage.contains("Green"),
            "Il messaggio non contiene il contenuto atteso: " + capturedMessage);
        Assertions.assertTrue(capturedMessage.contains("Red"),
                "Il messaggio non contiene il contenuto atteso: " + capturedMessage);
        Assertions.assertTrue(capturedMessage.contains("Blue"),
                "Il messaggio non contiene il contenuto atteso: " + capturedMessage);
    }
    
    @Test
    public void testSendMessageToConsoleWithHexColors() {
        messageGesturePaper.sendMessage("&#FF0000Red &#00FF00Green &#0000FFBlue console message");

        // Verifica che il messaggio venga catturato nella console
        Assertions.assertFalse(consoleMessages.isEmpty(), "Nessun messaggio catturato nella console");
        
        String capturedMessage = consoleMessages.get(consoleMessages.size() - 1);
        
        // Verifica che il messaggio contenga il prefisso e il contenuto corretto
        Assertions.assertTrue(capturedMessage.contains("HeroxCore"), 
            "Il messaggio non contiene il prefisso atteso: " + capturedMessage);
        Assertions.assertTrue(capturedMessage.contains("Red &#00FF00Green &#0000FFBlue console message"), 
            "Il messaggio non contiene il contenuto atteso: " + capturedMessage);
    }

}
