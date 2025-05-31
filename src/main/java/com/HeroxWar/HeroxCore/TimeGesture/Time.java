package com.HeroxWar.HeroxCore.TimeGesture;

import java.util.ArrayList;
import java.util.List;

public class Time {

    private static final int millisecondsInOneSecond = 1000;
    private static final int secondsInOneMinute = 60;
    private static final int minutesInOneHour = 60;
    private static final int hoursInADay = 24;
    private long seconds = 0;
    private long minutes = 0;
    private long hours = 0;
    private long days = 0;
    private char formatter = ':';

    public Time() {
    }

    public Time(long milliseconds, char formatter) {
        seconds = getSecondsFromMilliseconds(milliseconds);
        minutes = getMinutesFromSeconds(seconds);
        hours = getHoursFromMinutes(minutes);
        days = getDaysFromHours(hours);

        seconds = seconds - getSecondsFromMinutes(minutes);
        minutes = minutes - getMinutesFromHours(hours);
        hours = hours - getHoursFromDays(days);
        this.formatter = formatter;
    }

    public Time(long days, long hours, long minutes, long seconds, char formatter) {
        if (seconds >= secondsInOneMinute) {
            this.seconds = secondsInOneMinute - 1;
        } else {
            this.seconds = seconds;
        }
        if (minutes >= minutesInOneHour) {
            this.minutes = minutesInOneHour - 1;
        } else {
            this.minutes = minutes;
        }
        if (hours >= hoursInADay) {
            this.hours = hoursInADay - 1;
        } else {
            this.hours = hours;
        }
        this.days = days;
        this.formatter = formatter;
    }

    public Time(int days, int hours, int minutes, int seconds, char formatter) {
        if (seconds >= secondsInOneMinute) {
            this.seconds = secondsInOneMinute - 1;
        } else {
            this.seconds = seconds;
        }
        if (minutes >= minutesInOneHour) {
            this.minutes = minutesInOneHour - 1;
        } else {
            this.minutes = minutes;
        }
        if (hours >= hoursInADay) {
            this.hours = hoursInADay - 1;
        } else {
            this.hours = hours;
        }
        this.days = days;
        this.formatter = formatter;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    /**
     * This method calculates the milliseconds of the Time instance
     *
     * @return the milliseconds
     */
    public long getMilliseconds() {
        long totalHours = getHoursFromDays(days) + hours;
        long totalMinutes = getMinutesFromHours(totalHours) + minutes;
        long totalSeconds = getSecondsFromMinutes(totalMinutes) + seconds;
        return totalSeconds * 1000;
    }

    /**
     * This method convert milliseconds into seconds
     *
     * @param milliseconds milliseconds such this: 10,000
     * @return the seconds such this: (10 seconds)
     */
    public long getSecondsFromMilliseconds(long milliseconds) {
        return milliseconds / millisecondsInOneSecond;
    }

    /**
     * This method convert minutes into seconds
     *
     * @param minutes minutes such this: 12
     * @return the seconds 12 * 60 -> 720
     */
    public long getSecondsFromMinutes(long minutes) {
        return minutes * secondsInOneMinute;
    }

    /**
     * This method convert seconds into minutes
     *
     * @param seconds seconds such this: 61
     * @return the minutes 61 / 60 -> 1
     */
    public long getMinutesFromSeconds(long seconds) {
        return seconds / secondsInOneMinute;
    }

    /**
     * This method convert hours into minutes
     *
     * @param hours seconds such this: 2
     * @return the minutes 2 * 24 -> 48
     */
    public long getMinutesFromHours(long hours) {
        return hours * minutesInOneHour;
    }

    /**
     * This method convert minutes into hours
     *
     * @param minutes minutes such this: 61
     * @return the minutes 61 / 60 -> 1
     */
    public long getHoursFromMinutes(long minutes) {
        return minutes / minutesInOneHour;
    }

    /**
     * This method convert days into hours
     *
     * @param days days such this: 2
     * @return the minutes 2 * 24 -> 48
     */
    public long getHoursFromDays(long days) {
        return days * hoursInADay;
    }

    /**
     * This method convert hours into days
     *
     * @param hours hours such this: 25
     * @return the minutes 25 / 24 -> 1
     */
    public long getDaysFromHours(long hours) {
        return hours / hoursInADay;
    }

    /**
     * This method formats the informations in a String
     *
     * @return the formatted informations in this way: 0:0:0:0
     */
    public String getTime() {
        return getTime(formatter);
    }

    /**
     * This method formats the informations in a String
     *
     * @param formatter the formatter you want to use, for example: "-"
     * @return the formatted informations in this way: 0-0-0-0
     */
    public String getTime(char formatter) {
        return days + "" + formatter + hours + formatter + minutes + formatter + seconds;
    }

    /**
     * This method formats the informations in a String
     *
     * @return the formatted informations in this way: 20:10
     */
    public String getTimeWithoutZeros() {
        return getTimeWithoutZeros(formatter);
    }

    /**
     * This method formats the informations in a String
     *
     * @param formatter the formatter you want to use, for example: "-"
     * @return the formatted informations in this way: 20:10
     */
    public String getTimeWithoutZeros(char formatter) {
        StringBuilder time = new StringBuilder();
        boolean found = true;
        for (long partTime : getArrayTime()) {
            if (partTime == 0 && found) {
                continue;
            }
            found = false;
            time.append(partTime).append(formatter);
        }
        time = new StringBuilder(time.substring(0, time.length() - 1));
        return time.toString();
    }

    /**
     * This method returns the informations in an Array of Longs
     *
     * @return the informations in this way: [0,0,0,0]
     */
    public List<Long> getArrayTime() {
        List<Long> informations = new ArrayList<>();
        informations.add(days);
        informations.add(hours);
        informations.add(minutes);
        informations.add(seconds);
        return informations;
    }

    /**
     * This method returns the informations in an Array of Longs
     *
     * @return the informations in this way: [20,40]
     */
    public List<Long> getArrayTimeWithoutZero() {
        List<Long> informations = getArrayTime();
        boolean found = true;
        while (found) {
            if (informations.get(0) == 0) {
                informations.remove(0);
                continue;
            }
            found = false;
        }
        return informations;
    }

    /**
     * This method compare two time Strings
     *
     * @param time the other Time object
     * @return true if is equals false otherwise
     */
    @Override
    public boolean equals(Object time) {
        if (time instanceof Time) {
            return ((Time) time).getTime().equalsIgnoreCase(getTime());
        } else {
            return false;
        }
    }

    /**
     * This method clones the current informations
     *
     * @return a new Time instance
     */
    public Time cloneTime() {
        return new Time(days, hours, minutes, seconds, formatter);
    }

    /**
     * This method check if the Time passed is bigger than the current instance
     *
     * @param time the other object to compare
     * @return true if is bigger false otherwise (also if is equals)
     */
    public boolean isBiggerThen(Time time) {
        return time.getMilliseconds() > getMilliseconds();
    }

    /**
     * This method returns the difference between instances
     * Ex. currentTime = [0,0,0,60], times = [0,0,0,59] -> new instance [0,0,0,1]
     *
     * @param time the other object to subtract
     * @return a time instance with the difference
     */
    public Time differenceBetween(Time time) {
        Time timeToReturn;
        boolean isBigger = isBiggerThen(time);
        if (isBigger) {
            timeToReturn = new Time(time.getMilliseconds() - getMilliseconds(), formatter);
        } else {
            timeToReturn = new Time(getMilliseconds() - time.getMilliseconds(), formatter);
        }
        return timeToReturn;
    }

    /**
     * This method returns the difference between the current instance and an instance passed
     * Ex. currentTime = [0,0,0,60], times = [0,0,0,59] -> currentTime [0,0,0,1]
     *
     * @param time the other object to subtract
     */
    public void difference(Time time) {
        Time timeToReturn = differenceBetween(time);
        setDays(timeToReturn.getDays());
        setHours(timeToReturn.getHours());
        setMinutes(timeToReturn.getMinutes());
        setSeconds(timeToReturn.getSeconds());
    }

    /**
     * This method returns the sum between instances
     * Ex. currentTime = [0,0,0,60], times = [0,0,0,60] -> new instance [0,0,2,0]
     *
     * @param time the other object to compare
     * @return a time instance with the sum
     */
    public Time sumBetween(Time time) {
        return new Time(getMilliseconds() + time.getMilliseconds(), formatter);
    }

    /**
     * This method returns the sum between the current instance and an instance passed
     * Ex. currentTime = [0,0,0,60], times = [0,0,0,60] -> currentTime [0,0,2,0]
     *
     * @param time the other object to compare
     */
    public void sum(Time time) {
        Time timeSum = sumBetween(time);
        setDays(timeSum.getDays());
        setHours(timeSum.getHours());
        setMinutes(timeSum.getMinutes());
        setSeconds(timeSum.getSeconds());
    }

    @Override
    public String toString() {
        return "Time{" +
                "seconds=" + seconds +
                ", minutes=" + minutes +
                ", hours=" + hours +
                ", days=" + days +
                ", formatter='" + formatter + '\'' +
                '}';
    }
}