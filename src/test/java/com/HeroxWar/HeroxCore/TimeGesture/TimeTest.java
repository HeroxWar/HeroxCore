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

    // ========== Conversion method direct tests ==========

    @Test
    public void getSecondsFromMilliseconds() {
        Assertions.assertEquals(10, time.getSecondsFromMilliseconds(10000));
        Assertions.assertEquals(0, time.getSecondsFromMilliseconds(0));
        Assertions.assertEquals(0, time.getSecondsFromMilliseconds(999));
    }

    @Test
    public void getSecondsFromMinutes() {
        Assertions.assertEquals(120, time.getSecondsFromMinutes(2));
        Assertions.assertEquals(0, time.getSecondsFromMinutes(0));
    }

    @Test
    public void getMinutesFromSeconds() {
        Assertions.assertEquals(2, time.getMinutesFromSeconds(120));
        Assertions.assertEquals(1, time.getMinutesFromSeconds(61));
        Assertions.assertEquals(0, time.getMinutesFromSeconds(59));
        Assertions.assertEquals(0, time.getMinutesFromSeconds(0));
    }

    @Test
    public void getMinutesFromHours() {
        Assertions.assertEquals(120, time.getMinutesFromHours(2));
        Assertions.assertEquals(0, time.getMinutesFromHours(0));
    }

    @Test
    public void getHoursFromMinutes() {
        Assertions.assertEquals(2, time.getHoursFromMinutes(120));
        Assertions.assertEquals(1, time.getHoursFromMinutes(61));
        Assertions.assertEquals(0, time.getHoursFromMinutes(59));
        Assertions.assertEquals(0, time.getHoursFromMinutes(0));
    }

    @Test
    public void getHoursFromDays() {
        Assertions.assertEquals(48, time.getHoursFromDays(2));
        Assertions.assertEquals(0, time.getHoursFromDays(0));
    }

    @Test
    public void getDaysFromHours() {
        Assertions.assertEquals(2, time.getDaysFromHours(48));
        Assertions.assertEquals(1, time.getDaysFromHours(25));
        Assertions.assertEquals(0, time.getDaysFromHours(23));
        Assertions.assertEquals(0, time.getDaysFromHours(0));
    }

    // ========== Constructor edge cases ==========

    @Test
    public void createTimeMilliseconds() {
        time = new Time(10000L, '.');
        Assertions.assertEquals("0.0.0.10", time.getTime());
    }

    @Test
    public void createTimeMillisecondsZero() {
        time = new Time(0L, '.');
        Assertions.assertEquals("0.0.0.0", time.getTime());
    }

    @Test
    public void createTimeMillisecondsSubSecond() {
        time = new Time(999L, '.');
        Assertions.assertEquals("0.0.0.0", time.getTime());
    }

    @Test
    public void createTimeMillisecondsProducesDays() {
        time = new Time(90061000L, '.');
        Assertions.assertEquals("1.1.1.1", time.getTime());
    }

    @Test
    public void createTimeTicks() {
        time = new Time(2420,'.');
        Assertions.assertEquals("0.0.2.1", time.getTime());
    }

    @Test
    public void createTimeTicksZero() {
        time = new Time(0,'.');
        Assertions.assertEquals("0.0.0.0", time.getTime());
    }

    @Test
    public void createTimeTicksSubSecond() {
        time = new Time(19,'.');
        Assertions.assertEquals("0.0.0.0", time.getTime());
    }

    @Test
    public void createTimeLongs() {
        time = new Time(60L, 23L, 59L, 59L, '.');
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeLongsWrong() {
        time = new Time(60L, 60L, 60L, 60L, '.');
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeInts() {
        time = new Time(60, 23, 59, 59, '.');
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeIntsWrong() {
        time = new Time(60, 60, 60, 60, '.');
        Assertions.assertEquals("60.23.59.59", time.getTime());
    }

    @Test
    public void createTimeLongsClampSecondsOnly() {
        time = new Time(0L, 0L, 0L, 60L, '.');
        Assertions.assertEquals("0.0.0.59", time.getTime());
    }

    @Test
    public void createTimeLongsClampMinutesOnly() {
        time = new Time(0L, 0L, 60L, 0L, '.');
        Assertions.assertEquals("0.0.59.0", time.getTime());
    }

    @Test
    public void createTimeLongsClampHoursOnly() {
        time = new Time(0L, 24L, 0L, 0L, '.');
        Assertions.assertEquals("0.23.0.0", time.getTime());
    }

    // ========== Getter/setter ==========

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

    // ========== Business methods ==========

    @Test
    public void getMilliseconds() {
        time = new Time(1, 1, 1, 1, '.');
        Assertions.assertEquals(90061000L, time.getMilliseconds());
    }

    @Test
    public void getMillisecondsZero() {
        time = new Time(0, 0, 0, 0, '.');
        Assertions.assertEquals(0L, time.getMilliseconds());
    }

    @Test
    public void getMillisecondsOnlyDays() {
        time = new Time(2, 0, 0, 0, '.');
        Assertions.assertEquals(172800000L, time.getMilliseconds());
    }

    @Test
    public void getTimeWithoutZeros() {
        time = new Time(0, 23, 0, 10, '.');
        Assertions.assertEquals("23.0.10", time.getTimeWithoutZeros());
        Assertions.assertEquals("23-0-10", time.getTimeWithoutZeros('-'));
    }

    @Test
    public void getTimeWithoutZerosAllZeros() {
        time = new Time(0, 0, 0, 0, '.');
        Assertions.assertEquals("0", time.getTimeWithoutZeros());
    }

    @Test
    public void getTimeArray() {
        time = new Time(0, 23, 10, 0, '.');
        List<Long> expected = new ArrayList<>();
        expected.add(0L);
        expected.add(23L);
        expected.add(10L);
        expected.add(0L);
        Assertions.assertEquals(expected, time.getArrayTime());
        expected.remove(0);
        Assertions.assertEquals(expected, time.getArrayTimeWithoutZero());
    }

    @Test
    public void cloneTime() {
        time = new Time(1000L, '.');
        Time clone = time.cloneTime();
        Assertions.assertEquals(time, clone);
    }

    @Test
    public void equals() {
        Time clone = time.cloneTime();
        Assertions.assertEquals(time, clone);
    }

    @Test
    public void notEquals() {
        Time newTime = new Time(1000L, '.');
        Assertions.assertNotEquals(time, newTime);
    }

    @Test
    public void notEqualsInstances() {
        Assertions.assertNotEquals(time, new Object());
    }

    @Test
    public void isBiggerThen() {
        Time newTime = new Time(1000L, '.');
        Assertions.assertTrue(time.isBiggerThen(newTime));
        Assertions.assertFalse(newTime.isBiggerThen(time));
    }

    @Test
    public void difference() {
        Time newTime = new Time(1000L, '.');
        time = new Time(10000L, '.');
        newTime.difference(time);
        Assertions.assertEquals(new Time(9000L, '.'), newTime);
        newTime = new Time(1000L, '.');
        time.difference(newTime);
        Assertions.assertEquals(new Time(9000L, '.'), time);
    }

    @Test
    public void differenceBetween() {
        Time newTime = new Time(1000L, '.');
        time = new Time(10000L, '.');
        Time expected = new Time(9000L, '.');
        Assertions.assertEquals(expected, newTime.differenceBetween(time));
        Assertions.assertEquals(expected, time.differenceBetween(newTime));
    }

    @Test
    public void differenceBetweenAllowNegativeDirect() {
        time = new Time(1000L, '.');
        Time result = time.differenceBetweenAllowNegative(new Time(10000L, '.'));
        Assertions.assertTrue(result.getMilliseconds() < 0);
    }

    @Test
    public void sumBetween() {
        Time newTime = new Time(5000L, '.');
        time = new Time(5000L, '.');
        Time expected = new Time(10000L, '.');
        Assertions.assertEquals(expected, newTime.sumBetween(time));
    }

    @Test
    public void sum() {
        Time newTime = new Time(5000L, '.');
        time = new Time(5000L, '.');
        Time expected = new Time(10000L, '.');
        newTime.sum(time);
        Assertions.assertEquals(expected, newTime);
    }

    @Test
    public void toStringTime() {
        String expected = "Time{seconds=0, minutes=0, hours=0, days=0, formatter=':'}";
        Assertions.assertEquals(expected, time.toString());
    }

    @Test
    public void testDifferenceAllowNegative() {
        time.differenceAllowNegative(new Time(1000000L, '.'));
        Assertions.assertTrue(time.getMilliseconds() < 0);
    }

    @Test
    public void testGetTimeFormatted() {
        time = new Time(20, ':');
        Assertions.assertEquals("0d:0h:0m:1s", time.getTimeFormatted());
    }

    @Test
    public void testGetTimeFormattedFormatter() {
        time = new Time(20, ':');
        Assertions.assertEquals("0d 0h 0m 1s", time.getTimeFormatted(" "));
    }

    @Test
    public void testGetArrayTimeString() {
        time = new Time(20, ':');
        List<String> times = time.getArrayTimeString();
        Assertions.assertEquals("0d", times.get(0));
        Assertions.assertEquals("0h", times.get(1));
        Assertions.assertEquals("0m", times.get(2));
        Assertions.assertEquals("1s", times.get(3));
    }

    @Test
    public void testGetArrayTimeWithoutZeroString() {
        time = new Time(20, ':');
        List<String> times = time.getArrayTimeWithoutZeroString();
        Assertions.assertEquals("1s", times.get(0));
    }

    @Test
    public void testGetTimeWithoutZerosString() {
        time = new Time(100000,':');
        Assertions.assertEquals("1h:23m:20s", time.getTimeWithoutZerosString());
    }

    @Test
    public void testGetTimeWithoutZerosStringFormatted() {
        time = new Time(100000,' ');
        Assertions.assertEquals("1h 23m 20s", time.getTimeWithoutZerosString());
    }

    @Test
    public void testGetTimeWithoutZerosStringFormattedEmpty() {
        time = new Time(0,' ');
        Assertions.assertEquals("0s", time.getTimeWithoutZerosString());
    }

    @Test
    public void testGetTimeWithoutZerosStringEmpty() {
        time = new Time(0,':');
        Assertions.assertEquals("0", time.getTimeWithoutZeros());
    }

}
