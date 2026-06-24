package com.HeroxWar.HeroxCore.SoundGesture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SoundExceptionTest {

    @Test
    public void constructor() {
        SoundException e = new SoundException("test message");
        Assertions.assertEquals("test message", e.getMessage());
    }

}
