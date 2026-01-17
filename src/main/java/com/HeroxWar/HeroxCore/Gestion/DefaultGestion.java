package com.HeroxWar.HeroxCore.Gestion;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultGestion extends Gestion {

    private String path;
    private String fileName;
    private String pluginName;
    private String[] ignoredSections;

    private FileConfiguration fc;

    private Map<String, Boolean> debug = new HashMap<>();
    private final List<String> hooksEnabled = new ArrayList<>();
    private Map<String, Boolean> hooks = new HashMap<>();
    private Map<String, String> messages = new HashMap<>();

    public DefaultGestion(String path, String fileName) {
        super(path, fileName);
        fc = this.getFileConfiguration();
        this.path = path;
        this.fileName = fileName;
    }

    public DefaultGestion(String path, String fileName, String pluginName, String... ignoredSections) {
        super(path, fileName, pluginName, ignoredSections);
        fc = this.getFileConfiguration();
        this.path = path;
        this.fileName = fileName;
        this.pluginName = pluginName;
        this.ignoredSections = ignoredSections;
    }

    public DefaultGestion reloadDefaultConfiguration(boolean fileOnly) {
        if (fileOnly) {
            return new DefaultGestion(path, fileName);
        } else {
            return new DefaultGestion(path, fileName, pluginName, ignoredSections);
        }
    }

    public void defaultInformations() {
        ConfigurationSection section = fc.getConfigurationSection("Debug");
        if (section != null) {
            for (String event : section.getKeys(false)) {
                debug.put(event, fc.getBoolean("Debug." + event));
            }
        }
        section = fc.getConfigurationSection("Messages");
        String prefix = fc.getString("Messages.Prefix", "");
        if (section != null) {
            for (String message : section.getKeys(false)) {
                recursiveStringConfiguration("Messages.", "Messages." + message, prefix, messages);
            }
        }
        section = fc.getConfigurationSection("Configuration.Hooks");
        if (section != null) {
            for (String hook : section.getKeys(false)) {
                recursiveBooleanConfiguration("Configuration.Hooks.", "Configuration.Hooks." + hook, hooks);
            }
        }
    }

    private void recursiveBooleanConfiguration(String configurationSection, String path, Map<String, Boolean> informations) {
        if (!fc.isConfigurationSection(path)) {
            String newPath = path.replaceFirst(configurationSection, "");
            if (newPath.contains(".")) {
                if (newPath.contains("Enabled")) {
                    String[] split = newPath.split("\\.");
                    informations.put(split[0], fc.getBoolean(path, false));
                    hooksEnabled.add(split[0]);
                }
            } else {
                informations.put(newPath, fc.getBoolean(path, false));
            }
            return;
        }
        ConfigurationSection section = fc.getConfigurationSection(path);
        for (String hook : section.getKeys(false)) {
            String newPath = path + "." + hook;
            recursiveBooleanConfiguration(configurationSection, newPath, informations);
        }
    }

    private void recursiveStringConfiguration(String configurationSection, String path, String prefix, Map<String, String> informations) {
        if (!fc.isConfigurationSection(path)) {
            if (fc.isList(path)) {
                List<String> list = fc.getStringList(path);
                for (int i = 1; i <= list.size(); i++) {
                    informations.put(path.replaceFirst(configurationSection, "") + i, list.get(i - 1).replace("{prefix}", prefix));
                }
            } else {
                informations.put(path.replaceFirst(configurationSection, ""), fc.getString(path, "").replace("{prefix}", prefix));
            }
            return;
        }
        ConfigurationSection section = fc.getConfigurationSection(path);
        for (String message : section.getKeys(false)) {
            String newPath = path + "." + message;
            recursiveStringConfiguration(configurationSection, newPath, prefix, informations);
        }
    }

    public FileConfiguration getFc() {
        return fc;
    }

    public void setFc(FileConfiguration fc) {
        this.fc = fc;
    }

    public Map<String, Boolean> getDebug() {
        return debug;
    }

    public void setDebug(Map<String, Boolean> debug) {
        this.debug = debug;
        for (String event : debug.keySet()) {
            saveSection("Debug." + event, debug.get(event));
        }
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
        for (String message : messages.keySet()) {
            saveSection("Messages." + message, messages.get(message));
        }
    }

    public Map<String, Boolean> getHooks() {
        return hooks;
    }

    public void setHooks(Map<String, Boolean> hooks) {
        this.hooks = hooks;
        for (String hook : hooks.keySet()) {
            if (hooksEnabled.contains(hook)) {
                saveSection("Configuration.Hooks." + hook + ".Enabled", hooks.get(hook));
            } else {
                saveSection("Configuration.Hooks." + hook, hooks.get(hook));
            }
        }
    }
}
