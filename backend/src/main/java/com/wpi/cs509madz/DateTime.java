package com.wpi.cs509madz;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;

    DateTime(int year, int month, int day, int hour, int min, int sec) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    DateTime(String date) {
        String[] split = date.split(" ");

        String[] newDate = split[0].split("-");
        year = Integer.parseInt(newDate[0]);
        month = Integer.parseInt(newDate[1]);
        day = Integer.parseInt(newDate[2]);

        if (split.length == 2) {
            String[] newTime = split[1].split(":");
            hour = Integer.parseInt(newTime[0]);
            min = Integer.parseInt(newTime[1]);
            sec = Integer.parseInt(newTime[2]);
        }
    }

    public boolean isBefore(DateTime date) {
        return year <= date.year && month <= date.month && day <= date.day &&
                hour <= date.hour && min <= date.min && sec <= date.sec;
    }

    @Override
    public String toString() {
        return year +
                "-" + month +
                "-" + day +
                " " + hour +
                ":" + min +
                ":" + sec;
    }
}
