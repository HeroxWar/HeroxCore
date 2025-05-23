package com.HeroxWar.HeroxCore.TimeGesture;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Date {

    private SimpleDateFormat sdf;
    private long milliseconds;
    private Timestamp timestamp;

    /**
     * This constructor creates a simple date format with the now date
     * Ex. yyyy.MM.dd.HH.mm.ss -> 2025.05.23.11.39.45
     */
    public Date() {
        sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        milliseconds = System.currentTimeMillis();
        timestamp = new Timestamp(milliseconds);
    }

    /**
     * This constructor creates a simple date format with the now date
     * This method uses a custom formatter such this "-"
     * Ex. yyyy-MM-dd-HH-mm-ss -> 2025-05-23-11-39-45
     */
    public Date(char formatter) {
        this(formatter, System.currentTimeMillis());
    }

    /**
     * This constructor creates a simple date format with milliseconds in input
     * This method uses a custom formatter such this "-"
     * Ex. yyyy-MM-dd-HH-mm-ss -> 2025-05-23-11-39-45
     */
    public Date(char formatter, long milliseconds) {
        sdf = new SimpleDateFormat(
                "yyyy" + formatter + "MM" + formatter + "dd" + formatter + "HH" + formatter + "mm" + formatter + "ss");
        this.milliseconds = milliseconds;
        timestamp = new Timestamp(milliseconds);
    }

    /**
     * This constructor creates a custom date format with the now date
     * Ex. yyyy.MM.dd -> 2025.05.23
     */
    public Date(String pattern) {
        this(pattern, System.currentTimeMillis());
    }

    /**
     * This constructor creates a custom date format with milliseconds in input
     * Ex. yyyy.MM.dd -> 2025.05.23
     */
    public Date(String pattern, long milliseconds) {
        sdf = new SimpleDateFormat(pattern);
        this.milliseconds = milliseconds;
        timestamp = new Timestamp(milliseconds);
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
        timestamp = new Timestamp(milliseconds);
    }

    /**
     * This method returns a string representation of a date
     *
     * @return a string
     */
    public String getDate() {
        return sdf.format(timestamp);
    }

    /**
     * This method configures the pattern of simple date format
     * Ex. 2025.05.23
     *
     * @param pattern of simple date format
     */
    public void setPattern(String pattern) {
        sdf = new SimpleDateFormat(pattern);
    }

    public String getPattern() {
        return sdf.toPattern();
    }

    /**
     * TODO
     */
    public void setTimeZone() {

    }

    /**
     * This method compare two millisecond values
     *
     * @param date the other Date object
     * @return true if is equals false otherwise
     */
    @Override
    public boolean equals(Object date) {
        if (date instanceof Date) {
            return ((Date) date).getMilliseconds() == milliseconds;
        }
        return false;
    }

    /**
     * This method clones the current informations
     *
     * @return a new Date instance
     */
    public Date cloneDate() {
        return new Date(sdf.toPattern(), milliseconds);
    }

    /**
     * This method check if the Date passed is bigger than the current instance
     *
     * @param date the other object to compare
     * @return true if is bigger false otherwise (also if is equals)
     */
    public boolean isBiggerThen(Date date) {
        return date.getMilliseconds() > milliseconds;
    }

    /**
     * This method returns the difference between instances
     * Ex. currentDate = 20,000 ms, date = 10,000 -> new instance 10,000
     *
     * @param date the other object to subtract
     * @return a date instance with the difference
     */
    public Date differenceBetween(Date date) {
        Date dateToReturn;
        boolean isBigger = isBiggerThen(date);
        if (isBigger) {
            dateToReturn = new Date(sdf.toPattern(), date.getMilliseconds() - getMilliseconds());
        } else {
            dateToReturn = new Date(sdf.toPattern(), getMilliseconds() - date.getMilliseconds());
        }
        return dateToReturn;
    }

    /**
     * This method returns the difference between the current instance and an instance passed
     * Ex. currentDate = 20,000 ms, date = 10,000 -> currentDate 10,000
     *
     * @param date the other object to subtract
     */
    public void difference(Date date) {
        Date dateToReturn = differenceBetween(date);
        setMilliseconds(dateToReturn.getMilliseconds());
        setPattern(dateToReturn.getPattern());
    }

    /**
     * This method returns the sum between instances
     * Ex. currentDate = 10,000 ms, date = 10,000 -> new instance 20,000
     *
     * @param date the other object to compare
     * @return a date instance with the sum
     */
    public Date sumBetween(Date date) {
        return new Date(sdf.toPattern(), getMilliseconds() + date.getMilliseconds());
    }

    /**
     * This method returns the sum between the current instance and an instance passed
     * Ex. currentDate = 10,000 ms, date = 10,000 -> currentDate 20,000
     *
     * @param date the other object to compare
     */
    public void sum(Date date) {
        Date dateSum = sumBetween(date);
        setMilliseconds(dateSum.getMilliseconds());
        setPattern(dateSum.getPattern());
    }

}
