package com.HeroxWar.HeroxCore.TimeGesture.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateExceptionTest {

    @Test
    public void constructor() {
        DateException e = new DateException("test message");
        Assertions.assertEquals("test message", e.getMessage());
    }

}
