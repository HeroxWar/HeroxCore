package com.HeroxWar.HeroxCore.Gestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GestionExceptionTest {

    @Test
    public void testException() {
        GestionException exception = Assertions.assertThrows(GestionException.class, () -> {
            throw new GestionException("Gestion Exception");
        });
        Assertions.assertEquals("Gestion Exception", exception.getMessage());
    }

}
