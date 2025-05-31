package com.HeroxWar.HeroxCore.TimeGesture.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateTest {

    Date date;

    @BeforeEach
    public void setUp() {
        date = new Date();
    }

    @Test
    public void setUpTest() {
        // Preconditions
        String today = date.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDate() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date('-', System.currentTimeMillis());
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateFormatter() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date('-');
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateString() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date('-', dateToday);
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateWithDate() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date('-', new java.util.Date());
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustom() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date("yyyy-MM-dd");
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustomMilliseconds() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date("yyyy-MM-dd", System.currentTimeMillis());
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustomString() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date("yyyy-MM-dd", dateToday);
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @Test
    public void createDateCustomWithDate() {
        // Preconditions
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliseconds = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(milliseconds);
        String dateToday = sdf.format(timestamp);
        date = new Date("yyyy-MM-dd", new java.util.Date());
        String today = date.getDate();

        // Test
        Assertions.assertEquals(dateToday, today);
    }

    @RepeatedTest(10)
    public void getMilliseconds() {
        // Test
        long milliseconds = System.currentTimeMillis() + 1000L;
        date.setMilliseconds(milliseconds);
        Assertions.assertEquals(milliseconds, date.getMilliseconds());
    }

    @RepeatedTest(10)
    public void setMilliseconds() {
        // Precondition
        long milliseconds = System.currentTimeMillis();
        date.setMilliseconds(milliseconds);
        Assertions.assertEquals(milliseconds, date.getMilliseconds());

        // Test
        Assertions.assertEquals(new Date('-', milliseconds), date);
    }

    @Test
    public void setDate() {
        // Test
        long milliseconds = date.getMilliseconds();
        date.setDate(date.getDate().replaceAll("\\.", "-"));
        long newMilliseconds = date.getMilliseconds();
        Assertions.assertEquals(milliseconds, newMilliseconds);
    }

    @Test
    public void setPattern() {
        // Preconditions
        long milliseconds = date.getMilliseconds();
        date.setMilliseconds(milliseconds);

        // Tests
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
        // Preconditions
        Date newDate = date.cloneDate();

        // Test
        Assertions.assertEquals(date, newDate);
    }

    @Test
    public void equals() {
        // Test
        boolean equals = date.equals(date.cloneDate());
        Assertions.assertTrue(equals);
    }

    @Test
    public void notEquals() {
        // Preconditions
        Date newDate = new Date('-', System.currentTimeMillis() + 1000L);

        // Test
        boolean equals = date.equals(newDate);
        Assertions.assertFalse(equals);
    }

    @Test
    public void notEqualsObject() {
        // Test
        boolean equals = date.equals(new Object());
        Assertions.assertFalse(equals);
    }

    @Test
    public void isBiggerThen() {
        // Precondition
        Date newDate = new Date('-', System.currentTimeMillis() + 1000L);

        // Tests
        boolean isBigger = date.isBiggerThen(newDate);
        Assertions.assertTrue(isBigger);
        isBigger = newDate.isBiggerThen(date);
        Assertions.assertFalse(isBigger);
    }

    @RepeatedTest(10)
    public void differenceBetween() {
        // Precondition
        long milliseconds = System.currentTimeMillis();
        date = new Date('-', milliseconds);
        Date newDate = new Date('-', milliseconds + 1000L);

        // Tests
        Date subtract = date.differenceBetween(newDate);
        Assertions.assertEquals(new Date('-', 1000L), subtract);
        subtract = newDate.differenceBetween(date);
        Assertions.assertEquals(new Date('-', 1000L), subtract);
    }

    @RepeatedTest(10)
    public void difference() {
        // Precondition
        long milliseconds = System.currentTimeMillis();
        date = new Date('-', milliseconds);
        Date newDate = new Date('-', milliseconds + 1000L);

        // Test
        newDate.difference(date);
        Assertions.assertEquals(new Date('-', 1000L), newDate);
    }

    @RepeatedTest(10)
    public void sumBetween() {
        // Precondition
        long milliseconds = System.currentTimeMillis();
        date = new Date('-', milliseconds);
        Date newDate = new Date('-', 1000L);

        // Test
        Date sum = date.sumBetween(newDate);
        Assertions.assertEquals(new Date('-', milliseconds + 1000L), sum);
    }

    @RepeatedTest(10)
    public void sum() {
        // Precondition
        long milliseconds = System.currentTimeMillis();
        date = new Date('-', milliseconds);
        Date newDate = new Date('-', 1000L);

        // Test
        newDate.sum(date);
        Assertions.assertEquals(new Date('-', milliseconds + 1000L), newDate);
    }

    @RepeatedTest(10)
    public void toStringCompare() {
        // Precondition
        long milliseconds = System.currentTimeMillis();
        date = new Date('-', milliseconds);
        Date newDate = new Date('-', milliseconds);

        // Test
        Assertions.assertEquals(newDate.toString(), date.toString());
    }

}
