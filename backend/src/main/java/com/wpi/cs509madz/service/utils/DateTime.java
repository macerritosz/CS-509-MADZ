package com.wpi.cs509madz.service.utils;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;

//    public DateTime(int year, int month, int day, int hour, int min, int sec) {
//        this.year = year;
//        this.month = month;
//        this.day = day;
//        this.hour = hour;
//        this.min = min;
//        this.sec = sec;
//    }
//
//    public DateTime(int year, int month, int day) {
//        this.year = year;
//        this.month = month;
//        this.day = day;
//    }

    public DateTime(String date) {
        String[] split = date.split(" ");

        String[] newDate = split[0].split("-");
        year = Integer.parseInt(newDate[0]);
        month = Integer.parseInt(newDate[1]);
        day = Integer.parseInt(newDate[2]);

        String[] newTime = split[1].split(":");
        hour = Integer.parseInt(newTime[0]);
        min = Integer.parseInt(newTime[1]);
        sec = Integer.parseInt(newTime[2]);
    }

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

    public int getDifference(DateTime date) {
        return (Math.abs(year - date.year) * 525600) +
                (Math.abs(month - date.month) * 43800) +
                (Math.abs(day - date.day) * 1440) +
                (Math.abs(hour - date.hour) * 60) +
                (Math.abs(min - date.min));
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
