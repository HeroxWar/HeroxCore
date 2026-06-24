package com.HeroxWar.HeroxCore.SoundGesture;

import com.test.utils.TestLogHandler;
import org.bukkit.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SoundTypeTest {

    private static final Logger SOUND_LOGGER = Logger.getLogger(SoundType.class.getName());

    private static ServerMock serverMock;

    private PlayerMock playerMock;
    private SoundType soundType = new SoundType("AMBIENT_CAVE", 0, 2);
    private TestLogHandler logHandler;
    private List<String> logMessages;

    @BeforeEach
    public void setUp() {
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
        logMessages = new ArrayList<>();
        logHandler = new TestLogHandler(logMessages);
        SOUND_LOGGER.addHandler(logHandler);
    }

    @AfterEach
    public void tearDown() {
        SOUND_LOGGER.removeHandler(logHandler);
        MockBukkit.unmock();
    }

    @Test
    public void createSound() {
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());
    }

    @Test
    public void createSoundErrorNull() {
        soundType = new SoundType(null, 0, 2);
        Assertions.assertFalse(soundType.isEnabled());
    }

    @Test
    public void createSoundErrorEmpty() {
        soundType = new SoundType("", 0, 2);
        Assertions.assertFalse(soundType.isEnabled());
    }

    @Test
    public void createSoundErrorString() {
        soundType = new SoundType("AMBIENT_CAV", 0, 2);
        Assertions.assertFalse(soundType.isEnabled());
    }

    @Test
    public void createSoundInvalidLogsWarning() {
        logMessages.clear();
        soundType = new SoundType("AMBIENT_CAV", 0, 2);
        Assertions.assertFalse(logMessages.isEmpty());
        boolean containsSound = false;
        for (String msg : logMessages) {
            if (msg != null && msg.contains("AMBIENT_CAV")) {
                containsSound = true;
                break;
            }
        }
        Assertions.assertTrue(containsSound);
    }

    @Test
    public void createSoundErrorVolumeMin() {
        soundType = new SoundType("AMBIENT_CAVE", -1, 2);
        Assertions.assertEquals(100f, soundType.getVolume());
    }

    @Test
    public void createSoundErrorVolumeMax() {
        soundType = new SoundType("AMBIENT_CAVE", 101, 2);
        Assertions.assertEquals(100f, soundType.getVolume());
    }

    @Test
    public void createSoundVolumeOutOfRangeLogsWarning() {
        logMessages.clear();
        soundType = new SoundType("AMBIENT_CAVE", -1, 2);
        Assertions.assertFalse(logMessages.isEmpty());
        boolean containsVolume = false;
        for (String msg : logMessages) {
            if (msg != null && msg.contains("volume")) {
                containsVolume = true;
                break;
            }
        }
        Assertions.assertTrue(containsVolume);
    }

    @Test
    public void createSoundErrorPitchMin() {
        soundType = new SoundType("AMBIENT_CAVE", 0, -0.1);
        Assertions.assertEquals(1f, soundType.getPitch());
    }

    @Test
    public void createSoundErrorPitchMax() {
        soundType = new SoundType("AMBIENT_CAVE", 0, 2.1);
        Assertions.assertEquals(1f, soundType.getPitch());
    }

    @Test
    public void createSoundPitchOutOfRangeLogsWarning() {
        logMessages.clear();
        soundType = new SoundType("AMBIENT_CAVE", 0, -0.1);
        Assertions.assertFalse(logMessages.isEmpty());
        boolean containsPitch = false;
        for (String msg : logMessages) {
            if (msg != null && msg.contains("pitch")) {
                containsPitch = true;
                break;
            }
        }
        Assertions.assertTrue(containsPitch);
    }

    @Test
    public void playSoundPlayer() {
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());
        soundType.playSound(playerMock);
    }

    @Test
    public void playSoundLocation() {
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());
        soundType.playSound(playerMock.getLocation());
    }

    @Test
    public void playSoundErrorLocation() {
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());
        Location location = playerMock.getLocation().clone();
        location.setWorld(null);
        soundType.playSound(location);
    }

    @Test
    public void playSoundPlayerButIsNotEnabled() {
        soundType = new SoundType(null, 0, 2);
        Assertions.assertFalse(soundType.isEnabled());
        soundType.playSound(playerMock);
    }

    @Test
    public void playSoundLocationButIsNotEnabled() {
        soundType = new SoundType(null, 0, 2);
        Assertions.assertFalse(soundType.isEnabled());
        soundType.playSound(playerMock.getLocation());
    }

    @Test
    public void testToString() {
        soundType = new SoundType("AMBIENT_CAVE", 50, 1);
        String expected = "SoundType{type='AMBIENT_CAVE', volume=50.0, pitch=1.0}";
        Assertions.assertEquals(expected, soundType.toString());
    }

    @Test
    public void testGetType() {
        soundType = new SoundType("AMBIENT_CAVE", 20, 1.5);
        Assertions.assertEquals("AMBIENT_CAVE", soundType.getType());
    }

    @Test
    public void testVolumeAndPitchInRange() {
        soundType = new SoundType("AMBIENT_CAVE", 42.5, 1.2);
        Assertions.assertEquals(42.5f, soundType.getVolume());
        Assertions.assertEquals(1.2f, soundType.getPitch());
    }

    @Test
    public void testVolumePitchWhenSoundInvalidButParamsValid() {
        soundType = new SoundType("INVALID_SOUND", 50, 1.5);
        Assertions.assertFalse(soundType.isEnabled());
        Assertions.assertEquals(0f, soundType.getVolume());
        Assertions.assertEquals(0f, soundType.getPitch());
    }

}
