package com.HeroxWar.HeroxCore;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CommentedConfigurationTest {

    private ServerMock serverMock;
    private CommentedConfiguration cfg;
    private File file = new File(getClass().getClassLoader().getResource("configExample.yml").getFile());

    @BeforeEach
    public void setUp() {
        // Inizialization server and plugin
        serverMock = MockBukkit.mock();
        cfg = CommentedConfiguration.loadConfiguration(file);
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server and Plugin
        MockBukkit.unmock();
    }

    @Test
    public void testConstructor() {
        cfg = new CommentedConfiguration();
    }

    @Test
    public void testCreation() {
        Assertions.assertFalse(cfg.hasFailed());
    }

    @Test
    public void testCreationWithFileNotFound() {
        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(new File("asd.yml"));
        Assertions.assertTrue(cfg.hasFailed());
    }

    @Test
    public void testSync() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tempConfig.yml");
        try {
            cfg.syncWithConfig(file, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
