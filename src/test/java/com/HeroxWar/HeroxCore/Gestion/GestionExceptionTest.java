package com.HeroxWar.HeroxCore.Gestion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public class GestionExceptionTest {

    private static ServerMock serverMock;

    @BeforeEach
    public void setUp() {
        // Initialization server
        serverMock = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown() {
        // Unmock Server
        MockBukkit.unmock();
    }

    @Test
    public void testException() {
        Assertions.assertThrows(GestionException.class, () -> {
            throw new GestionException("Gestion Exception");
        });
    }

}
