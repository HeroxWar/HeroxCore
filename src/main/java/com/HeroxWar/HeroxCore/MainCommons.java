package com.HeroxWar.HeroxCore;

import com.HeroxWar.HeroxCore.MessageGesture.MessageGesturePaper;
import com.HeroxWar.HeroxCore.Utils.Library;
import com.HeroxWar.HeroxCore.Utils.Metrics;
import com.HeroxWar.HeroxCore.Utils.Version;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class MainCommons extends  JavaPlugin {

    private boolean mockTest = false;
    private boolean debug = false;
    private boolean papi = false;
    private List<String> libraryLegacyMessages = new ArrayList<>();
    private Version version;
    private MessageGesturePaper messageGesturePaper;

    public void onLoadInit(JavaPlugin javaPlugin) {
        if (javaPlugin.getClass().getClassLoader().getClass().getName().startsWith("org.mockbukkit.mockbukkit")) {
            mockTest = true;
        }
        version = new Version();
        // Load libraries where Spigot does not do this automatically
        libraryLegacyMessages = loadLibraries(javaPlugin);
    }

    public void onEnableInit(JavaPlugin javaPlugin, String banner, int bStatsId) {
        if (mockTest) {
            new Metrics(javaPlugin, bStatsId);
        }

        messageGesturePaper = new MessageGesturePaper(debug, papi, javaPlugin, version);

        for (String message : libraryLegacyMessages) {
            messageGesturePaper.sendMessage(message);
        }
        libraryLegacyMessages.clear();

        messageGesturePaper.sendMessage(banner
                + "&a  \r\n" + "&a  \r\n" + "&e  Version " + javaPlugin.getDescription().getVersion() + " \r\n"
                + "&e© Developed by &feliotesta98 & xSavior_of_God &ewith &4<3 \r\n \r\n \r\n");

        if (version.isInRange(8, 12)) {
            messageGesturePaper.sendMessage("&6Server version registered < 1.13");
        } else {
            messageGesturePaper.sendMessage("&6Server version registered > 1.12");
        }
        messageGesturePaper.sendMessage("Version Detected: &c" + version.getFormattedServerVersion());
    }

    public void onDisableInit(JavaPlugin javaPlugin) {
        messageGesturePaper.sendMessage("&a" + javaPlugin.getName() + " has been disabled, &cBye bye! &e:(");
    }

    public abstract void loadConfigs();

    public abstract void unload();

    private List<String> loadLibraries(JavaPlugin javaPlugin) {
        final List<Library> libraries = new ArrayList<>();

        boolean oldVersion = version.isInRange(8, 16);

        List<String> messagesToSend = new ArrayList<>();

        if (oldVersion) {
            messagesToSend.add("Loading legacy libraries...");
            Reader targetReader = new InputStreamReader(javaPlugin.getResource("plugin.yml"));

            YamlConfiguration pluginFile = YamlConfiguration.loadConfiguration(targetReader);
            for (final String libraryPath : pluginFile.getStringList("legacy-libraries")) {
                final Library library = Library.fromMavenRepo(libraryPath);
                messagesToSend.add("Loading library " + libraryPath);
                libraries.add(library);
            }

            for (final Library library : libraries)
                library.load(javaPlugin.getClass().getClassLoader());
            messagesToSend.add("Legacy libraries loaded!");
        }
        return messagesToSend;
    }

    public boolean isMockTest() {
        return mockTest;
    }

    public void setMockTest(boolean mockTest) {
        this.mockTest = mockTest;
    }

    public List<String> getLibraryLegacyMessages() {
        return libraryLegacyMessages;
    }

    public void setLibraryLegacyMessages(List<String> libraryLegacyMessages) {
        this.libraryLegacyMessages = libraryLegacyMessages;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public MessageGesturePaper getMessageGesturePaper() {
        return messageGesturePaper;
    }

    public void setMessageGesturePaper(MessageGesturePaper messageGesturePaper) {
        this.messageGesturePaper = messageGesturePaper;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        messageGesturePaper.setPrintDebug(debug);
    }

    public boolean isPapi() {
        return papi;
    }

    public void setPapi(boolean papi) {
        this.papi = papi;
        messageGesturePaper.setPlaceholderAPIEnabled(papi);
    }
}
