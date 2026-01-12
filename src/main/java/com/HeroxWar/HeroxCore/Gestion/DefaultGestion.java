package com.HeroxWar.HeroxCore.Gestion;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class DefaultGestion extends Gestion {

    private String path;
    private String fileName;
    private String pluginName;
    private String[] ignoredSections;

    private FileConfiguration fc;

    private Map<String, Boolean> debug = new HashMap<>();

    public DefaultGestion(String path, String fileName, String pluginName, String... ignoredSections) {
        super(path, fileName, pluginName, ignoredSections);
        fc = this.getFileConfiguration();
    }

    public void defaultInformations() {
        ConfigurationSection section = fc.getConfigurationSection("Debug");
        if (section != null) {
            for (String event : section.getKeys(false)) {
                debug.put(event, fc.getBoolean("Debug." + event));
            }
        }
        section = fc.getConfigurationSection("Messages");
        if(section != null) {
            for (String message : section.getKeys(false)) {

            }
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

    public DefaultGestion reloadDefaultConfiguration() {
        return new DefaultGestion(path, fileName, pluginName, ignoredSections);
    }
}
