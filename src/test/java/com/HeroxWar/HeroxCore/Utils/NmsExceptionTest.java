package com.HeroxWar.HeroxCore.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NmsExceptionTest {

    @Test
    public void constructor() {
        NmsException e = new NmsException("test message");
        Assertions.assertEquals("test message", e.getMessage());
    }

}
