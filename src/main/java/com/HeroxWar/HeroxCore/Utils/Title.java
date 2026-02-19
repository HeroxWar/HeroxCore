package com.HeroxWar.HeroxCore.Utils;

import com.HeroxWar.HeroxCore.MessageGesturePaper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.logging.Level;

public class Title {
    /* Title text and color */
    private String title = "";
    /* Subtitle text and color */
    private String subtitle = "";
    /* Title timings */
    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;
    private boolean ticks = true;
    private MessageGesturePaper messageGesturePaper;


    /**
     * Create a new title
     *
     */
    public Title(MessageGesturePaper messageGesturePaper) {
        this.messageGesturePaper = messageGesturePaper;
    }

    /**
     * Create a new title
     *
     * @param title Title
     */
    public Title(MessageGesturePaper messageGesturePaper, String title) {
        this.messageGesturePaper = messageGesturePaper;
        this.title = title;
    }

    /**
     * Create a new title
     *
     * @param title    Title text
     * @param subtitle Subtitle text
     */
    public Title(MessageGesturePaper messageGesturePaper, String title, String subtitle) {
        this.messageGesturePaper = messageGesturePaper;
        this.title = title;
        this.subtitle = subtitle;
    }

    /**
     * Copy title
     *
     * @param title Title
     */
    public Title(MessageGesturePaper messageGesturePaper, Title title) {
        this.messageGesturePaper = messageGesturePaper;
        // Copy title
        this.title = title.title;
        this.subtitle = title.subtitle;
        this.fadeInTime = title.fadeInTime;
        this.fadeOutTime = title.fadeOutTime;
        this.stayTime = title.stayTime;
        this.ticks = title.ticks;
    }

    /**
     * Create a new title
     *
     * @param title       Title text
     * @param subtitle    Subtitle text
     * @param fadeInTime  Fade in time
     * @param stayTime    Stay on screen time
     * @param fadeOutTime Fade out time
     */
    public Title(MessageGesturePaper messageGesturePaper, String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
        this.messageGesturePaper = messageGesturePaper;
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
    }

    /**
     * Set title text
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get title text
     *
     * @return Title text
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set subtitle text
     *
     * @param subtitle Subtitle text
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Get subtitle text
     *
     * @return Subtitle text
     */
    public String getSubtitle() {
        return this.subtitle;
    }

    /**
     * Set title fade in time
     *
     * @param time Time
     */
    public void setFadeInTime(int time) {
        this.fadeInTime = time;
    }

    /**
     * Set title fade out time
     *
     * @param time Time
     */
    public void setFadeOutTime(int time) {
        this.fadeOutTime = time;
    }

    /**
     * Set title stay time
     *
     * @param time Time
     */
    public void setStayTime(int time) {
        this.stayTime = time;
    }


    /**
     * Send the title to a player
     *
     * @param player Player
     */
    public void send(Player player) {
        title = messageGesturePaper.applyColorLegacy(messageGesturePaper.translate(player, title));
        subtitle = messageGesturePaper.applyColorLegacy(messageGesturePaper.translate(player, subtitle));
        try {
            player.sendTitle(title, subtitle, fadeInTime, stayTime, fadeOutTime);
        } catch (Exception e) {
            messageGesturePaper.getInternalLogger().log(Level.SEVERE, "Something went wrong while sending title to player " + player.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Send the title to all player
     */
    public void sendAll() {
        Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
            try {
                send(player);
            } catch (Exception e) {
                messageGesturePaper.getInternalLogger().log(Level.SEVERE, "Something went wrong while sending title to player " + player.getName() + ": " + e.getMessage());
            }
        });
    }

    /**
     * Send the title to a player using Adventure API
     *
     * @param player Player
     */
    public void sendRich(Player player) {
        try {
            Component _mainTitle = messageGesturePaper.applyColor(messageGesturePaper.translate(player, title));
            Component _subtitle = messageGesturePaper.applyColor(messageGesturePaper.translate(player, subtitle));
            final net.kyori.adventure.title.Title.Times times = net.kyori.adventure.title.Title.Times.times(Duration.ofMillis(fadeInTime), Duration.ofMillis(stayTime), Duration.ofMillis(fadeOutTime));

            final net.kyori.adventure.title.Title title = net.kyori.adventure.title.Title.title(_mainTitle, _subtitle, times);

            messageGesturePaper.getAdventure().player(player).showTitle(title);
        } catch (Exception e) {
            messageGesturePaper.getInternalLogger().log(Level.SEVERE, "Something went wrong while sending title to player " + player.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Send the title to all player using Adventure API
     */
    public void sendAllRich() {
        Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
            try {
                sendRich(player);
            } catch (Exception e) {
                messageGesturePaper.getInternalLogger().log(Level.SEVERE, "Something went wrong while sending title to player " + player.getName() + ": " + e.getMessage());
            }
        });
    }
}