package com.HeroxWar.HeroxCore.Gestion;

import nonapi.io.github.classgraph.utils.Assert;
import org.bukkit.Bukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestionTest {

    private static ServerMock serverMock;

    private static final Path resourcesPathTest = Paths.get(".", "src", "test", "resources").normalize().toAbsolutePath();

    Gestion gestion;

    @BeforeEach
    public void setUp() {
        // Initialization server
        serverMock = MockBukkit.mock();

        gestion = new Gestion(resourcesPathTest.toString(), "config.yml");
        Assertions.assertEquals("config.yml", gestion.getConfigFile().getName());
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void newGestion() {
        Assertions.assertThrows(NullPointerException.class, () -> new Gestion("", "config.yml", Bukkit.getPluginManager().getPlugin("Tombs")));
    }

    @Test
    public void newGestionPluginString() {
        Assertions.assertThrows(NullPointerException.class, () -> new Gestion("", "config.yml", "Tombs"));
    }

    @Test
    public void saveSection() {
        gestion.saveSection("Messages.Prefix", "&a&lPrefix");
        Object prefix = gestion.getSection("Messages.Prefix");
        Assertions.assertEquals("&a&lPrefix", prefix);
        gestion.saveSection("Messages", null);
    }

    @Test
    public void setterAndGetter() {
        gestion.setConfigFile(gestion.getConfigFile());
        gestion.setFileConfiguration(gestion.getFileConfiguration());
        gestion.toString();
    }

    @Test
    public void testCreateFileCustomPath() {
        gestion = new Gestion(resourcesPathTest.toString(),File.separator + "SubFolder" + "log.yml");
        Assertions.assertThrows(NullPointerException.class, () -> gestion.createFile("SubFolder/log.yml", Bukkit.getPluginManager().getPlugin("Tombs"), ""));
    }

}