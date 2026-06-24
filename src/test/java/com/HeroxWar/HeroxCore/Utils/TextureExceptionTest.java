package com.HeroxWar.HeroxCore.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextureExceptionTest {

    @Test
    public void constructor() {
        TextureException e = new TextureException("test message");
        Assertions.assertEquals("test message", e.getMessage());
    }

}
