package com.HeroxWar.HeroxCore.Gestion;

import com.HeroxWar.HeroxCore.CommentedConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.*;

public class Gestion {

    private File configFile;
    private FileConfiguration fileConfiguration;

    /**
     * Creates a new {@code Gesture} instance associated with a YAML configuration file.
     * <p>
     * This constructor only initializes the file reference and loads the configuration.
     * The file is not created automatically if it does not exist.
     *
     * @param path     the directory path where the configuration file is located
     * @param fileName the name of the configuration file
     */
    public Gestion(String path, String fileName) {
        configFile = new File(path, fileName);
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Creates a new {@code Gesture} instance and initializes the configuration file.
     * <p>
     * If the file does not exist, it will be copied from the plugin's internal resources.
     * After creation, the file is synchronized with the default configuration while
     * optionally ignoring specific sections.
     *
     * @param path            the directory path where the configuration file is located
     * @param fileName        the name of the configuration file
     * @param plugin          the Bukkit plugin instance
     * @param ignoredSections configuration sections to exclude from synchronization
     */
    public Gestion(String path, String fileName, Plugin plugin, String... ignoredSections) {
        this(path, fileName);
        createFile(plugin, ignoredSections);
    }

    /**
     * Creates a new {@code Gestion} instance and initializes the configuration file
     * using the specified plugin name.
     * <p>
     * If the configuration file does not exist, it will be created from the plugin's
     * internal resources and synchronized while optionally ignoring specific sections.
     *
     * @param path            the directory path where the configuration file is located
     * @param fileName        the name of the configuration file
     * @param pluginName      the name of the plugin registered in the PluginManager
     * @param ignoredSections configuration sections to exclude from synchronization
     */
    public Gestion(String path, String fileName, String pluginName, String... ignoredSections) {
        this(path, fileName);
        createFile(pluginName, ignoredSections);
    }

    /**
     * Creates and synchronizes the configuration file using the plugin name.
     *
     * @param pluginName      the name of the plugin registered in the PluginManager
     * @param ignoredSections configuration sections to exclude from synchronization
     */
    public void createFile(String pluginName, String... ignoredSections) {
        createFile(Bukkit.getPluginManager().getPlugin(pluginName), ignoredSections);
    }

    /**
     * Creates the configuration file if it does not exist and synchronizes it
     * with the plugin's internal resource.
     *
     * This method use a custom path for the creation if you don't use the default folder location
     * Ex. config.yml is in the main folder
     * Ex. Challenges/BlockPlace.yml is in the Challenges folder
     * 
     * <p>
     * Any I/O error occurring during file creation or synchronization will result
     * in a {@link GestionException}.
     *
     * @param plugin          the Bukkit plugin instance
     * @param ignoredSections configuration sections to exclude from synchronization
     * @throws GestionException if an I/O error occurs
     */
    public void createFile(String customPath, Plugin plugin, String... ignoredSections) {
        if (!configFile.exists()) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                plugin.saveResource(customPath, false);
                inputStream = plugin.getResource(customPath);

                // write the inputStream to a FileOutputStream
                outputStream = new FileOutputStream(configFile);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create " + configFile.getName() + "!");
                throw new GestionException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new GestionException(e.getMessage());
                    }
                }
                if (outputStream != null) {
                    try {
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        throw new GestionException(e.getMessage());
                    }
                }
            }
        }

        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(configFile);

        try {
            cfg.syncWithConfig(configFile, plugin.getResource(customPath), ignoredSections);
        } catch (IOException e) {
            throw new GestionException(e.getMessage());
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Creates the configuration file if it does not exist and synchronizes it
     * with the plugin's internal resource.
     * <p>
     * Any I/O error occurring during file creation or synchronization will result
     * in a {@link GestionException}.
     *
     * @param plugin          the Bukkit plugin instance
     * @param ignoredSections configuration sections to exclude from synchronization
     * @throws GestionException if an I/O error occurs
     */
    public void createFile(Plugin plugin, String... ignoredSections) throws GestionException {
        if (!configFile.exists()) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                plugin.saveResource(configFile.getName(), false);
                inputStream = plugin.getResource(configFile.getName());

                // write the inputStream to a FileOutputStream
                outputStream = new FileOutputStream(configFile);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create " + configFile.getName() + "!");
                throw new GestionException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new GestionException(e.getMessage());
                    }
                }
                if (outputStream != null) {
                    try {
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        throw new GestionException(e.getMessage());
                    }
                }
            }
        }

        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(configFile);

        try {
            cfg.syncWithConfig(configFile, plugin.getResource(configFile.getName()), ignoredSections);
        } catch (IOException e) {
            throw new GestionException(e.getMessage());
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Saves a value to the specified configuration path and immediately
     * persists the changes to disk.
     *
     * @param path   the configuration path where the value will be stored
     * @param toSave the object to save at the specified path
     */
    public void saveSection(String path, Object toSave) {
        fileConfiguration.set(path, toSave);
        saveFile();
    }

    /**
     * Get a value to the specified configuration path
     * @param path the configuration path from which the value should be retrieved
     * @return the value associated with the given path, or {@code null} if the path
     *        does not exist or no value is set
     */
    public Object getSection(String path) {
        return fileConfiguration.get(path);
    }

    /**
     * Saves the current state of the configuration to the associated file.
     *
     * @throws GestionException if an I/O error occurs while saving the file
     */
    public void saveFile() {
        try {
            fileConfiguration.save(configFile);
        } catch (IOException e) {
            throw new GestionException(e.getMessage());
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    @Override
    public String toString() {
        return "Gesture{" +
                "configFile=" + configFile +
                ", fileConfiguration=" + fileConfiguration +
                '}';
    }
}
