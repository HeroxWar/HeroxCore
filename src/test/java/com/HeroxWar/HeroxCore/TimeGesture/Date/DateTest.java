package com.HeroxWar.HeroxCore.TimeGesture.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateTest {

    Date date;

    @BeforeEach
    public void setUp() {
        date = new Date();
    }

    @Test
    public void setUpTest() {
        String today = date.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Date date = new Date('-', System.currentTimeMillis());
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateFormatter() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Date date = new Date('-');
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Date date = new Date('-', dateToday);
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateWithDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Date date = new Date('-', new java.util.Date());
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustom() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Date date = new Date("yyyy-MM-dd");
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustomMilliseconds() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        Date date = new Date("yyyy-MM-dd", System.currentTimeMillis());
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustomString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date("yyyy-MM-dd", dateToday);
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustomWithDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date("yyyy-MM-dd", new java.util.Date());
        String today = date.getDate();
        Assertions.assertEquals(dateToday, today);
    }

    @RepeatedTest(10)
    public void getMilliseconds() {
        long milliseconds = System.currentTimeMillis() + 1000L;
        date.setMilliseconds(milliseconds);
        Assertions.assertEquals(milliseconds, date.getMilliseconds());
    }

    @RepeatedTest(10)
    public void setMilliseconds() {
        long milliseconds = System.currentTimeMillis();
        date.setMilliseconds(milliseconds);
        Assertions.assertEquals(milliseconds, date.getMilliseconds());
        Assertions.assertEquals(new Date('-', milliseconds), date);
    }

    @Test
    public void setDate() {
        long milliseconds = date.getMilliseconds();
        date.setDate(date.getDate().replaceAll("\\.", "-"));
        long newMilliseconds = date.getMilliseconds();
        Assertions.assertEquals(milliseconds, newMilliseconds);
    }

    @Test
    public void setPattern() {
        long milliseconds = date.getMilliseconds();
        date.setMilliseconds(milliseconds);
        String oldDate = date.getDate().replaceAll("\\.", "-").substring(0, 10);
        date.setPattern("yyyy-MM-dd");
        Assertions.assertEquals(oldDate, date.getDate());
    }

    @Test
    public void getPattern() {
        date.setPattern("yyyy-MM-dd");
        Assertions.assertEquals("yyyy-MM-dd", date.getPattern());
    }

    @Test
    public void cloneDate() {
        Date newDate = date.cloneDate();
        Assertions.assertEquals(date, newDate);
    }

    @Test
    public void equals() {
        boolean equals = date.equals(date.cloneDate());
        Assertions.assertTrue(equals);
    }

    @Test
    public void notEquals() {
        Date newDate = new Date('-', System.currentTimeMillis() + 1000L);
        boolean equals = date.equals(newDate);
        Assertions.assertFalse(equals);
    }

    @Test
    public void notEqualsObject() {
        boolean equals = date.equals(new Object());
        Assertions.assertFalse(equals);
    }

    @Test
    public void isBiggerThen() {
        Date newDate = new Date('-', System.currentTimeMillis() + 1000L);
        boolean isBigger = date.isBiggerThen(newDate);
        Assertions.assertTrue(isBigger);
        isBigger = newDate.isBiggerThen(date);
        Assertions.assertFalse(isBigger);
    }

    @RepeatedTest(10)
    public void differenceBetween() {
        long milliseconds = System.currentTimeMillis();
        Date date = new Date('-', milliseconds);
        Date newDate = new Date('-', milliseconds + 1000L);
        Date subtract = date.differenceBetween(newDate);
        Assertions.assertEquals(new Date('-', 1000L), subtract);
        subtract = newDate.differenceBetween(date);
        Assertions.assertEquals(new Date('-', 1000L), subtract);
    }

    @RepeatedTest(10)
    public void difference() {
        long milliseconds = System.currentTimeMillis();
        Date date = new Date('-', milliseconds);
        Date newDate = new Date('-', milliseconds + 1000L);
        newDate.difference(date);
        Assertions.assertEquals(new Date('-', 1000L), newDate);
    }

    @RepeatedTest(10)
    public void sumBetween() {
        long milliseconds = System.currentTimeMillis();
        Date date = new Date('-', milliseconds);
        Date newDate = new Date('-', 1000L);
        Date sum = date.sumBetween(newDate);
        Assertions.assertEquals(new Date('-', milliseconds + 1000L), sum);
    }

    @RepeatedTest(10)
    public void sum() {
        long milliseconds = System.currentTimeMillis();
        Date date = new Date('-', milliseconds);
        Date newDate = new Date('-', 1000L);
        newDate.sum(date);
        Assertions.assertEquals(new Date('-', milliseconds + 1000L), newDate);
    }

    @RepeatedTest(10)
    public void toStringCompare() {
        long milliseconds = System.currentTimeMillis();
        Date date = new Date('-', milliseconds);
        Date newDate = new Date('-', milliseconds);
        Assertions.assertEquals(newDate.toString(), date.toString());
    }

    @Test
    public void getUtilDate() {
        java.util.Date date1 = date.getUtilDate();
        Assertions.assertNotNull(date1);
    }

    @Test
    public void getUtilDateError() {
        Date date = new Date('-', "2025.11.07.23.20.25");
        Assertions.assertNull(date.getUtilDate());
    }

    @Test
    public void getDateDividedTest() {
        LocalDateTime now = LocalDateTime.now();
        Assertions.assertEquals(now.getYear(), date.getYear());
        Assertions.assertEquals(now.getMonthValue(), date.getMonth());
        Assertions.assertEquals(now.getDayOfMonth(), date.getDay());
        Assertions.assertEquals(now.getHour(), date.getHours());
        Assertions.assertEquals(now.getMinute(), date.getMinutes());
        Assertions.assertEquals(now.getSecond(), date.getSeconds());
    }

    @Test
    public void setDateWithInvalidStringDoesNotUpdate() {
        long original = date.getMilliseconds();
        date.setDate("not-a-valid-date");
        Assertions.assertEquals(original, date.getMilliseconds());
    }

}
