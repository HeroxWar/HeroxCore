package com.HeroxWar.HeroxCore;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is deprecated and should not be used. It is recommended to use the MessageGesturePaper class instead.
 * The MessageGesturePaper class provides more features and is more efficient than this class.
 * This class will be removed in a future update.
 */
@Deprecated(since = "19-02-2026")
public class MessageGesture {

    // Simple color converter
    private final static Pattern hexPattern = Pattern.compile("&\\{#([A-Fa-f0-9]){6}\\}");

    public static void sendMessage(CommandSender commandSender, String message) {
        if (message.isEmpty()) {
            return;
        }
        commandSender.sendMessage(transformColor(message));
    }

    @Deprecated
    public static String applyColor(String message) {
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(2, matcher.group().length() - 1));
            final String before = message.substring(0, matcher.start());
            final String after = message.substring(matcher.end());
            message = before + hexColor + after;
            matcher = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String transformColor(String message) {
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(2, matcher.group().length() - 1));
            final String before = message.substring(0, matcher.start());
            final String after = message.substring(matcher.end());
            message = before + hexColor + after;
            matcher = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
