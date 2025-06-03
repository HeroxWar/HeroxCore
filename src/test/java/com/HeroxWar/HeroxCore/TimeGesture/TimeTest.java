package com.HeroxWar.HeroxCore.TimeGesture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class TimeTest {

    Time time;

    @BeforeEach
    public void setUp() {
        time = new Time();
    }

    @Test
    public void testSetUp() {
        Assertions.assertEquals("0:0:0:0", time.getTime());
    }

    @Test
    public void testGetTimeFormatter() {
        Assertions.assertEquals("0.0.0.0", time.getTime('.'));
    }

    @Test
    public void createTimeMilliseconds() {
        // Preconditions
        time = new Time(10000L, '.');

        // Test
        Assertions.assertEquals("0.0.0.10", time.getTime());
    }

    @Test
    public void createTimeTicks() {
        time = new Time(2420,'.');

        // Test
        Assertions.assertEquals("0.0.2.1", time.getTime());
    }

    @Test
    public void createTimeLongs() {
        // Preconditions
        time = new Time(60L, 23L, 59L, 59L, '.');

        // Test
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeLongsWrong() {
        // Preconditions
        time = new Time(60L, 60L, 60L, 60L, '.');

        // Test
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeInts() {
        // Preconditions
        time = new Time(60, 23, 59, 59, '.');

        // Test
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeIntsWrong() {
        // Preconditions
        time = new Time(60, 60, 60, 60, '.');

        // Test
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void getAndSetSeconds() {
        time.setSeconds(1);
        Assertions.assertEquals(1, time.getSeconds());
    }

    @Test
    public void getAndSetMinutes() {
        time.setMinutes(1);
        Assertions.assertEquals(1, time.getMinutes());
    }

    @Test
    public void getAndSetHours() {
        time.setHours(1);
        Assertions.assertEquals(1, time.getHours());
    }

    @Test
    public void getAndSetDays() {
        time.setDays(1);
        Assertions.assertEquals(1, time.getDays());
    }

    @Test
    public void getMilliseconds() {
        // Precondition
        time = new Time(1, 1, 1, 1, '.');

        // Test
        Assertions.assertEquals(90061000L, time.getMilliseconds());
    }

    @Test
    public void getTimeWithoutZeros() {
        // Precondition
        time = new Time(0, 23, 0, 10, '.');

        // Tests
        Assertions.assertEquals("23.0.10", time.getTimeWithoutZeros());
        Assertions.assertEquals("23-0-10", time.getTimeWithoutZeros('-'));
    }

    @Test
    public void getTimeArray() {
        // Precondition
        time = new Time(0, 23, 10, 0, '.');
        List<Long> expected = new ArrayList<>();
        expected.add(0L);
        expected.add(23L);
        expected.add(10L);
        expected.add(0L);

        // Tests
        Assertions.assertEquals(expected, time.getArrayTime());
        expected.remove(0);
        Assertions.assertEquals(expected, time.getArrayTimeWithoutZero());
    }

    @Test
    public void cloneTime() {
        // Precondition
        time = new Time(1000L, '.');
        Time clone = time.cloneTime();

        // Test
        Assertions.assertEquals(time, clone);
    }

    @Test
    public void equals() {
        // Precondition
        Time clone = time.cloneTime();

        // Test
        boolean equals = time.equals(clone);
        Assertions.assertTrue(equals);
    }

    @Test
    public void notEquals() {
        // Precondition
        Time newTime = new Time(1000L, '.');

        // Test
        boolean equals = time.equals(newTime);
        Assertions.assertFalse(equals);
    }

    @Test
    public void notEqualsInstances() {
        // Test
        boolean equals = time.equals(new Object());
        Assertions.assertFalse(equals);
    }

    @Test
    public void isBiggerThen() {
        // Precondition
        Time newTime = new Time(1000L, '.');

        // Tests
        boolean isBigger = time.isBiggerThen(newTime);
        Assertions.assertTrue(isBigger);
        isBigger = newTime.isBiggerThen(time);
        Assertions.assertFalse(isBigger);
    }

    @Test
    public void difference() {
        // Precondition
        Time newTime = new Time(1000L, '.');
        time = new Time(10000L, '.');

        // Tests
        newTime.difference(time);
        Assertions.assertEquals(new Time(9000L, '.'), newTime);

        newTime = new Time(1000L, '.');
        time.difference(newTime);
        Assertions.assertEquals(new Time(9000L, '.'), time);
    }

    @Test
    public void differenceBetween() {
        // Precondition
        Time newTime = new Time(1000L, '.');
        time = new Time(10000L, '.');

        // Tests
        Time expected = new Time(9000L, '.');
        Assertions.assertEquals(expected, newTime.differenceBetween(time));
        Assertions.assertEquals(expected, time.differenceBetween(newTime));
    }

    @Test
    public void sumBetween() {
        // Precondition
        Time newTime = new Time(5000L, '.');
        time = new Time(5000L, '.');

        // Test
        Time expected = new Time(10000L, '.');
        Assertions.assertEquals(expected, newTime.sumBetween(time));
    }

    @Test
    public void sum() {
        // Precondition
        Time newTime = new Time(5000L, '.');
        time = new Time(5000L, '.');

        // Test
        Time expected = new Time(10000L, '.');
        newTime.sum(time);
        Assertions.assertEquals(expected, newTime);
    }

    @Test
    public void toStringTime() {
        // Test
        String expected = "Time{seconds=0, minutes=0, hours=0, days=0, formatter=':'}";
        Assertions.assertEquals(expected, time.toString());
    }

}
