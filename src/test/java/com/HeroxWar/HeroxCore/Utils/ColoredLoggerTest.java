package com.HeroxWar.HeroxCore.Utils;

import com.test.utils.TestLogHandler;
import org.bukkit.Bukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;

public class ColoredLoggerTest {

    private ServerMock serverMock;
    private ColoredLogger coloredLogger;
    private ColoredLogger customLogger;
    private TestLogHandler logHandler;
    private List<String> logMessages;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
        coloredLogger = new ColoredLogger("[Test]");
        customLogger = new ColoredLogger("[Custom]", '$');
        
        logMessages = new ArrayList<>();
        logHandler = new TestLogHandler(logMessages);
        Bukkit.getLogger().addHandler(logHandler);
        
        // Clear any existing messages from MockBukkit setup
        logMessages.clear();
    }

    @AfterEach
    public void tearDown() {
        // Remove test handler from the global logger before unmocking
        if (logHandler != null) {
            Bukkit.getLogger().removeHandler(logHandler);
        }
        MockBukkit.unmock();
    }

    @Test
    public void testDefaultConstructor() {
        ColoredLogger logger = new ColoredLogger("[Default]");
        Assertions.assertNotNull(logger);
    }

    @Test
    public void testCustomColorFormatter() {
        ColoredLogger logger = new ColoredLogger("[Test]", '$');
        Assertions.assertNotNull(logger);
    }

    @Test
    public void testBasicLog() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "Test message");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("[Test]"));
        Assertions.assertTrue(loggedMessage.contains("Test message"));
    }

    @Test
    public void testLogWithoutPrefix() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "Test message", false);
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertFalse(loggedMessage.contains("[Test]"));
        Assertions.assertTrue(loggedMessage.contains("Test message"));
    }

    @Test
    public void testLogWithPrefix() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "Test message", true);
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("[Test]"));
        Assertions.assertTrue(loggedMessage.contains("Test message"));
    }

    @Test
    public void testLogWithException() {
        int initialCount = logMessages.size();
        Exception testException = new RuntimeException("Test exception");
        coloredLogger.log(Level.WARNING, "Error message", testException);
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("[Test]"));
        Assertions.assertTrue(loggedMessage.contains("Error message"));
    }

    @Test
    public void testLogWithSupplier() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, (Supplier<String>) () -> "Supplier message");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("[Test]"));
        Assertions.assertTrue(loggedMessage.contains("Supplier message"));
    }

    @Test
    public void testColorCodeConversion() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "&aGreen message &cRed message");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green ANSI code
        Assertions.assertTrue(loggedMessage.contains("\u001B[91m")); // Red ANSI code
        Assertions.assertTrue(loggedMessage.contains("\u001B[0m")); // Reset code
    }

    @Test
    public void testCustomColorFormatterUsage() {
        int initialCount = logMessages.size();
        customLogger.log(Level.INFO, "$aGreen message $cRed message");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green ANSI code
        Assertions.assertTrue(loggedMessage.contains("\u001B[91m")); // Red ANSI code
    }

    @Test
    public void testAllColorCodes() {
        String testMessage = "&0Black &1DarkBlue &2DarkGreen &3DarkAqua &4DarkRed &5DarkPurple &6Gold &7Gray " +
                           "&8DarkGray &9Blue &aGreen &bAqua &cRed &dLightPurple &eYellow &fWhite " +
                           "&lBold &oItalic &nUnderline &mStrikethrough &rReset";
        
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, testMessage);
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        
        // Verify all ANSI codes are present
        Assertions.assertTrue(loggedMessage.contains("\u001B[30m")); // Black
        Assertions.assertTrue(loggedMessage.contains("\u001B[34m")); // Dark Blue
        Assertions.assertTrue(loggedMessage.contains("\u001B[32m")); // Dark Green
        Assertions.assertTrue(loggedMessage.contains("\u001B[36m")); // Dark Aqua
        Assertions.assertTrue(loggedMessage.contains("\u001B[31m")); // Dark Red
        Assertions.assertTrue(loggedMessage.contains("\u001B[35m")); // Dark Purple
        Assertions.assertTrue(loggedMessage.contains("\u001B[33m")); // Gold
        Assertions.assertTrue(loggedMessage.contains("\u001B[37m")); // Gray
        Assertions.assertTrue(loggedMessage.contains("\u001B[90m")); // Dark Gray
        Assertions.assertTrue(loggedMessage.contains("\u001B[94m")); // Blue
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green
        Assertions.assertTrue(loggedMessage.contains("\u001B[96m")); // Aqua
        Assertions.assertTrue(loggedMessage.contains("\u001B[91m")); // Red
        Assertions.assertTrue(loggedMessage.contains("\u001B[95m")); // Light Purple
        Assertions.assertTrue(loggedMessage.contains("\u001B[93m")); // Yellow
        Assertions.assertTrue(loggedMessage.contains("\u001B[97m")); // White
        Assertions.assertTrue(loggedMessage.contains("\u001B[1m"));  // Bold
        Assertions.assertTrue(loggedMessage.contains("\u001B[3m"));  // Italic
        Assertions.assertTrue(loggedMessage.contains("\u001B[4m"));  // Underline
        Assertions.assertTrue(loggedMessage.contains("\u001B[9m"));  // Strikethrough
        Assertions.assertTrue(loggedMessage.contains("\u001B[0m"));  // Reset
    }

    @Test
    public void testInvalidColorCodes() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "&xInvalid &zUnknown &123Invalid");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        
        // Invalid codes (&x, &z) should be treated as regular text
        // In &123, only &1 is processed as color code, 2 and 3 remain as text
        Assertions.assertTrue(loggedMessage.contains("&x"));
        Assertions.assertTrue(loggedMessage.contains("&z"));
        Assertions.assertTrue(loggedMessage.contains("23Invalid"));
        // Should contain ANSI code only for &1 (dark blue)
        Assertions.assertTrue(loggedMessage.contains("\u001B[34m")); // Dark Blue (&1)
        // Should NOT contain ANSI codes for 2 and 3 (they're treated as regular text)
        Assertions.assertFalse(loggedMessage.contains("\u001B[32m")); // Dark Green (&2)
        Assertions.assertFalse(loggedMessage.contains("\u001B[36m")); // Dark Aqua (&3)
    }

    @Test
    public void testNullMessage() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, (String) null);
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("[Test]"));
    }

    @Test
    public void testEmptyMessage() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("[Test]"));
    }

    @Test
    public void testMessageWithOnlyColorCodes() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "&a&c&l");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green
        Assertions.assertTrue(loggedMessage.contains("\u001B[91m")); // Red
        Assertions.assertTrue(loggedMessage.contains("\u001B[1m"));  // Bold
    }

    @Test
    public void testColorCodeAtEnd() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "Message with color at end &a");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green
    }

    @Test
    public void testMultipleLogLevels() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.SEVERE, "Severe message");
        coloredLogger.log(Level.WARNING, "Warning message");
        coloredLogger.log(Level.INFO, "Info message");
        coloredLogger.log(Level.CONFIG, "Config message");
        coloredLogger.log(Level.FINE, "Fine message");
        coloredLogger.log(Level.FINER, "Finer message");
        coloredLogger.log(Level.FINEST, "Finest message");
        
        // All should have been logged
        Assertions.assertTrue(logMessages.size() > initialCount);
    }

    @Test
    public void testCleanup() {
        Assertions.assertDoesNotThrow(() -> ColoredLogger.cleanup());
    }

    @Test
    public void testUpperCaseColorCodes() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "&AGreen &CRed &LBold");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green (case insensitive)
        Assertions.assertTrue(loggedMessage.contains("\u001B[91m")); // Red (case insensitive)
        Assertions.assertTrue(loggedMessage.contains("\u001B[1m"));  // Bold (case insensitive)
    }

    @Test
    public void testMixedCaseColorCodes() {
        int initialCount = logMessages.size();
        coloredLogger.log(Level.INFO, "&aGreen &CRed &Lbold");
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green
        Assertions.assertTrue(loggedMessage.contains("\u001B[91m")); // Red
        Assertions.assertTrue(loggedMessage.contains("\u001B[1m"));  // Bold
    }

    @Test
    public void testComplexMessage() {
        int initialCount = logMessages.size();
        String complexMessage = "&6[&eSystem&6] &aPlayer &f" + System.getProperty("user.name") + " &ahas connected!";
        coloredLogger.log(Level.INFO, complexMessage);
        Assertions.assertTrue(logMessages.size() > initialCount);
        String loggedMessage = logMessages.get(logMessages.size() - 1);
        Assertions.assertTrue(loggedMessage.contains("\u001B[33m")); // Gold
        Assertions.assertTrue(loggedMessage.contains("\u001B[93m")); // Yellow
        Assertions.assertTrue(loggedMessage.contains("\u001B[92m")); // Green
        Assertions.assertTrue(loggedMessage.contains("\u001B[97m")); // White
    }

    @Test
    public void testPerformanceWithManyColorCodes() {
        int initialCount = logMessages.size();
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longMessage.append("&aTest message ").append(i).append(" ");
        }
        
        long startTime = System.currentTimeMillis();
        coloredLogger.log(Level.INFO, longMessage.toString());
        long endTime = System.currentTimeMillis();
        
        Assertions.assertTrue(logMessages.size() > initialCount);
        Assertions.assertTrue(endTime - startTime < 1000); // Should complete within 1 second
    }

    }
