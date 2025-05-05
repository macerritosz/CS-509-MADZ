package com.wpi.cs509madz.service.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = DateTimeSerializer.class)
public class DateTime {
    private final int year;
    private final int month;
    private final int day;
    private int hour;
    private int min;
    private int sec;

    public DateTime(String date) {
        String[] split = date.split(" ");

        String[] newDate = split[0].split("-");
        year = Integer.parseInt(newDate[0]);
        month = Integer.parseInt(newDate[1]);
        day = Integer.parseInt(newDate[2]);

        if (split.length != 1) {
            String[] newTime = split[1].split(":");
            hour = Integer.parseInt(newTime[0]);
            min = Integer.parseInt(newTime[1]);
            sec = Integer.parseInt(newTime[2]);
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {return hour;}

    public int getMin() {return min;}

    public int getSec() {return sec;}

    /**
     * Calculates if one date is before another
     * @param date a DateTime
     * @return a boolean if this date is before the given date
     */
    public boolean isBefore(DateTime date) {
        if (year != date.year) {
            return year < date.year;
        }
        if (month != date.month) {
            return month < date.month;
        }
        if (day != date.day) {
            return day < date.day;
        }
        if (hour != date.hour) {
            return hour < date.hour;
        }
        if (min != date.min) {
            return min < date.min;
        }
        return sec < date.sec;
    }

    /**
     * Calculates the difference between two dates
     * @param date a DateTime
     * @return the number of minutes between this and the given date
     */
    public int getDifference(DateTime date) {
        int diff = 0;

        int yearDiff = this.year - date.year;
        diff += yearDiff * 365 * 24 * 60;

        int monthDiff = this.month - date.month;
        diff += monthDiff * 30 * 24 * 60;

        int dayDiff = this.day - date.day;
        diff += dayDiff * 24 * 60;

        int hourDiff = this.hour - date.hour;
        diff += hourDiff * 60;

        int minDiff = this.min - date.min;
        diff += minDiff;

        return Math.abs(diff);
    }

    @Override
    public String toString() {
        return year +
                "-" + convertDigit(month) +
                "-" + convertDigit(day)  +
                " " + convertDigit(hour)  +
                ":" + convertDigit(min)  +
                ":" + convertDigit(sec) ;
    }

    private String convertDigit(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return Integer.toString(num);
        }
    }
}
