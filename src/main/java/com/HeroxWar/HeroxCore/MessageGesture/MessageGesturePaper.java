package com.HeroxWar.HeroxCore.MessageGesture;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageGesturePaper {

    private static final Pattern hexPattern = Pattern.compile("&#[a-fA-F0-9]{6}");

    private String debugPrefixSuffix;
    private String prefix;
    private boolean printDebug;
    private boolean isPlaceholderAPIEnabled;
    private ColoredLogger internalLogger;
    private BukkitAudiences adventure = null;
    private boolean paper = false;
    private JavaPlugin plugin;

    public MessageGesturePaper(boolean printDebug, boolean isPlaceholderAPIEnabled, JavaPlugin plugin) {
        System.out.println("[HeroxCore] MessageGesturePaper Initializing");
        this.printDebug = printDebug;
        this.isPlaceholderAPIEnabled = isPlaceholderAPIEnabled;
        // Initialize an audiences instance for the plugin
        this.plugin = plugin;
        if (this.plugin != null) {
            this.debugPrefixSuffix = "\n\n&7<&8< &4DEBUG &e" + this.plugin.getName() + " &4DEBUG &8>&7>\n\n";
            this.internalLogger = new ColoredLogger("[" + this.plugin.getName() + "] ");
            existBukkitAudiences();
        } else {
            this.debugPrefixSuffix = "\n\n&7<&8< &4DEBUG &eHeroxPlugin &4DEBUG &8>&7>\n\n";
            this.internalLogger = new ColoredLogger("[HeroxPlugin] ");
        }
    }

    public MessageGesturePaper(String prefix, boolean printDebug, boolean isPlaceholderAPIEnabled, JavaPlugin plugin) {
        System.out.println("[HeroxCore] MessageGesturePaper Initializing With Prefix");
        this.prefix = prefix;
        this.debugPrefixSuffix = "\n\n&7<&8< &4DEBUG &e" + prefix + " &4DEBUG &8>&7>\n\n";
        this.printDebug = printDebug;
        this.isPlaceholderAPIEnabled = isPlaceholderAPIEnabled;
        internalLogger = new ColoredLogger("[" + prefix + "] ");
        this.plugin = plugin;
        // Initialize an audiences instance for the plugin
        if (this.plugin != null) {
            existBukkitAudiences();
        }
    }

    /**
     * Check if the server has BukkitAudiences
     * If not set the plugin instance to null for correct error messages
     */
    public void existBukkitAudiences() {
        System.out.println("Sto cercando BukkitAudiences");
        try {
            Class<?> aClass = Class.forName("net.kyori.adventure.platform.bukkit.BukkitAudiences");
            this.adventure = BukkitAudiences.create(plugin);
            System.out.println("Ho trovato BukkitAudiences");
        } catch (ClassNotFoundException ignored) {
            System.out.println("[HeroxCore] BukkitAudiences not found check for PaperRichMessage");
            existPaperRichMessage();
        }
    }

    public void existPaperRichMessage() {
        System.out.println("Sto cercando richMessage");
        try {
            Class<?> aClass = Class.forName("org.bukkit.entity.Player");
            aClass.getMethod("sendRichMessage", Player.class, String.class);
            System.out.println("Ho trovato richMessage");
            paper = true;
        } catch (Exception ignored) {
            System.out.println("[HeroxCore] PaperRichMessage not found using sendMessage");
            this.plugin = null;
            paper = false;
        }
    }

    public String getDebugPrefixSuffix() {
        return debugPrefixSuffix;
    }

    public void setDebugPrefixSuffix(String debugPrefixSuffix) {
        this.debugPrefixSuffix = "\n\n&7<&8< &4DEBUG &e" + prefix + " &4DEBUG &8>&7>\n\n";
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        setDebugPrefixSuffix(prefix);
        setInternalLogger(prefix);
    }

    public boolean isPrintDebug() {
        return printDebug;
    }

    public void setPrintDebug(boolean printDebug) {
        this.printDebug = printDebug;
    }

    public boolean isPlaceholderAPIEnabled() {
        return isPlaceholderAPIEnabled;
    }

    public void setPlaceholderAPIEnabled(boolean placeholderAPIEnabled) {
        isPlaceholderAPIEnabled = placeholderAPIEnabled;
    }

    public void setInternalLogger(ColoredLogger internalLogger) {
        this.internalLogger = internalLogger;
    }

    public void setInternalLogger(String prefix) {
        this.internalLogger = new ColoredLogger("[" + prefix + "] ");
    }

    public void setAdventure(BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    public ColoredLogger getInternalLogger() {
        return internalLogger;
    }

    public void sendBroadcast(String text) {
        if (!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                sendMessage(p, text, true);
            }
        }
    }

    public Component applyColor(String message) {
        TextComponent content = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        String serializedContent = MiniMessage.miniMessage().serialize(content);
        serializedContent = serializedContent.replace("\\<", "<");

        return MiniMessage.miniMessage().deserialize(serializedContent);
    }

    public String applyColorLegacy(String message) {
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start() + 1, matcher.end());
            message = message.replace("&" + color, "" + net.md_5.bungee.api.ChatColor.of(color));
            matcher = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void logDebug(String MESSAGE, boolean printDebug) {
        if (printDebug)
            this.internalLogger.log(Level.INFO, debugPrefixSuffix + MESSAGE + debugPrefixSuffix);
    }

    public void logDebug(String MESSAGE) {
        logDebug(MESSAGE, this.printDebug);
    }

    public void log(String MESSAGE, Level level) {
        this.internalLogger.log(level, MESSAGE);
    }

    public void sendMessage(String MESSAGE) {
        sendMessage(Bukkit.getConsoleSender(), MESSAGE, true);
    }

    public void sendMessage(CommandSender sender, String MESSAGE) {
        sendMessage(sender, MESSAGE, false);
    }

    public void sendMessage(CommandSender sender, String MESSAGE, boolean usePrefix) {
        if (sender instanceof ConsoleCommandSender) {
            this.internalLogger.log(Level.INFO, MESSAGE, usePrefix);
        } else {
            sendMessage((Player) sender, MESSAGE, usePrefix);
        }
    }

    public void sendMessage(Player player, String MESSAGE, boolean usePrefix) {
        if (adventure != null) {
            Component componentMessage = applyColor(translate(player, (usePrefix ? this.prefix : "") + MESSAGE));
            sendMessage(player, componentMessage);
        } else if (paper) {
            try {
                Class<?> aClass = Class.forName("org.bukkit.entity.Player");
                java.lang.reflect.Method sendRichMessage = aClass.getMethod("sendRichMessage", Player.class, String.class);
                sendRichMessage.invoke(null, player, applyColorLegacy(translate(player, (usePrefix ? this.prefix : "") + MESSAGE)));
            } catch (Exception e) {
                log(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()), Level.SEVERE);
                player.sendMessage(applyColorLegacy(translate(player, (usePrefix ? this.prefix : "") + MESSAGE)));
            }
        } else {
            player.sendMessage(applyColorLegacy(translate(player, (usePrefix ? this.prefix : "") + MESSAGE)));
        }
    }

    public void sendMessage(Player player, Component MESSAGE) {
        adventure.player(player).sendMessage(MESSAGE);
    }

    public String translate(String text) {
        if (!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
            return translate(new ArrayList<>(Bukkit.getServer().getOnlinePlayers()).get(0), text);
        }
        return text;
    }

    public String translate(CommandSender sender, String text) {
        if (sender instanceof ConsoleCommandSender)
            return text;
        return translate((Player) sender, text);
    }

    public String translate(Player p, String text) {
        return translate(Bukkit.getOfflinePlayer(p.getName()), text);
    }

    public String translate(OfflinePlayer p, String text) {
        if (!isPlaceholderAPIEnabled) {
            return text;
        }
        try {
            Class<?> placeholderApiClass = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            java.lang.reflect.Method setPlaceholdersMethod = placeholderApiClass.getMethod("setPlaceholders", OfflinePlayer.class, String.class);
            return (String) setPlaceholdersMethod.invoke(null, p, text);
        } catch (Exception ignored) {
            return text;
        }
    }
}
