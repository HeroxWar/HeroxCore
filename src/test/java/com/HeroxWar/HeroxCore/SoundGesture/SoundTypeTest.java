package com.HeroxWar.HeroxCore.SoundGesture;

import org.bukkit.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

public class SoundTypeTest {

    private static ServerMock serverMock;

    // Fake Instances
    private PlayerMock playerMock;
    private SoundType soundType = new SoundType("AMBIENT_CAVE", 0, 2);

    @BeforeEach
    public void setUp() {
        // Inizialization server
        serverMock = MockBukkit.mock();
        playerMock = serverMock.addPlayer();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
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
    public void createSoundErrorVolumeMin() {
        soundType = new SoundType("AMBIENT_CAVE", -1, 2);
        Assertions.assertEquals(soundType.getVolume(), 100);
    }

    @Test
    public void createSoundErrorVolumeMax() {
        soundType = new SoundType("AMBIENT_CAVE", 101, 2);
        Assertions.assertEquals(soundType.getVolume(), 100);
    }

    @Test
    public void createSoundErrorPitchMin() {
        soundType = new SoundType("AMBIENT_CAVE", 0, -0.1);
        Assertions.assertEquals(soundType.getPitch(), 1);
    }

    @Test
    public void createSoundErrorPitchMax() {
        soundType = new SoundType("AMBIENT_CAVE", 0, 2.1);
        Assertions.assertEquals(soundType.getPitch(), 1);
    }

    @Test
    public void playSoundPlayer() {
        // Preconditions
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());

        // Tests
        soundType.playSound(playerMock);
    }

    @Test
    // Enabled = true && location != null
    public void playSoundLocation() {
        // Preconditions
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());

        // Tests
        soundType.playSound(playerMock.getLocation());
    }

    @Test
    // Enabled = true && location == null
    public void playSoundErrorLocation() {
        // Preconditions
        soundType = new SoundType("AMBIENT_CAVE", 0, 2);
        Assertions.assertTrue(soundType.isEnabled());

        // Tests
        Location location = playerMock.getLocation().clone();
        location.setWorld(null);
        soundType.playSound(location);
    }

    @Test
    public void playSoundPlayerButIsNotEnabled() {
        // Preconditions
        soundType = new SoundType(null, 0, 2);
        Assertions.assertFalse(soundType.isEnabled());

        // Tests
        soundType.playSound(playerMock);
    }

    @Test
    // Enabled = false && location != null
    public void playSoundLocationButIsNotEnabled() {
        // Preconditions
        soundType = new SoundType(null, 0, 2);
        Assertions.assertFalse(soundType.isEnabled());

        // Tests
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
