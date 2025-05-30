package com.HeroxWar.HeroxCore.SoundGesture;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class SoundType {

    private static final Logger logger = Logger.getLogger(SoundType.class.getName());

    private final boolean enabled;
    private final Sound sound;
    private final String type;
    // Allowed value between 0 and 100
    private final double volume;
    // Allowed value between 0 and 2
    private final double pitch;

    public SoundType(String type, double volume, double pitch) {
        this.type = type;
        if(this.type == null || this.type.isEmpty()) {
            // The sound is disabled!
            this.enabled = false;
            // Missing Final values
            this.sound = null;
            this.volume = 0;
            this.pitch = 0;
            return;
        }

        this.sound = getSound(type);
        if(this.sound == null) {
            // The sound is disabled!
            this.enabled = false;
            // Missing Final values
            this.volume = 0;
            this.pitch = 0;
            return;
        }

        if (volume < 0 || volume > 100) {
            try {
                throw new Exception("!!!!!!!! THIS IS A CONFIGURATION ERROR !!!!!!!!!!!! The volume of the sound '" + type + "' is not between 0 and 100, it's: '" + volume + "'");
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }
            this.volume = 100;
        } else {
            this.volume = volume;
        }

        if (pitch < 0 || pitch > 2) {
            try {
                throw new Exception("!!!!!!!! THIS IS A CONFIGURATION ERROR !!!!!!!!!!!! The pitch of the sound '" + type + "' is not between 0 and 2, it's: '" + pitch + "'");
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }

            this.pitch = 1;
        } else {
            this.pitch = pitch;
        }

        this.enabled = true;
    }

    public String getType() {
        return type;
    }

    public float getVolume() {
        return (float) volume;
    }

    public float getPitch() {
        return (float) pitch;
    }

    public boolean isEnabled() { return enabled; }

    public void playSound(Player player) {
        if(!enabled) {
            return;
        }

        player.playSound(player.getLocation(), this.sound, (float) this.volume, (float) this.pitch);
    }

    public void playSound(Location location) {
        if(!enabled || location.getWorld() == null) {
            return;
        }

        location.getWorld().playSound(location, this.sound, (float) this.volume, (float) this.pitch);
    }

    private Sound getSound(final String sound) {
        try {
            // Get the `valueOf` method from the `Sound` class with String as its parameter
            Method valueOfMethod = Sound.class.getMethod("valueOf", String.class);

            // Invoke `valueOf` on the `Sound` class, passing the string parameter
            return (Sound) valueOfMethod.invoke(null, sound);
        } catch (Exception e) {
            // Handle cases where the sound name is invalid or reflection fails
            try {
                logger.log(Level.WARNING, e.getMessage());
                throw new SoundException("Error: Unable to resolve sound " + sound + ", if your server is running 1.21.3 or newer, make sure to convert the sound names to the new format, like 'minecraft:entity.ender_dragon.flap'.");
            } catch (SoundException ex) {
                logger.log(Level.WARNING, ex.getMessage());
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "SoundType{" +
                "type='" + type + '\'' +
                ", volume=" + volume +
                ", pitch=" + pitch +
                '}';
    }

}
