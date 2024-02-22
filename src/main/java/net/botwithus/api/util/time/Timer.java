package net.botwithus.api.util.time;

import net.botwithus.api.util.time.enums.DurationStringFormat;

public class Timer {
    private final Stopwatch stopWatch = new Stopwatch();
    private long minTime, maxTime;
    private boolean hasStarted = false, forceExpired = false;
    private long timerDuration;

    public Timer(long min, long max) {
        minTime = min;
        maxTime = max;
        timerDuration = min + (long) (Math.random() * (max - min));
        forceExpired = false;
    }

    public long getRemainingTime() {
        //Debug.log("TimerDuration: " + timerDuration + " | ElapsedTime: " + stopWatch.elapsed());
        return timerDuration - stopWatch.elapsed();
    }

    public long getElapsed() {
        return stopWatch.elapsed();
    }

    public long getRemainingTimeInSeconds() {
        return getRemainingTime() / 1000;
    }

    public long getRemainingTimeInMinutes() {
        return getRemainingTime() / 60000;
    }

    public boolean hasExpired() {
        return forceExpired || getRemainingTime() <= 0;
    }

    public void setExpired() {
        hasStarted = true;
        forceExpired = true;
    }

    public void setRemainingTime(long time) {
        timerDuration = time;
        hasStarted = true;
        stopWatch.start();
    }

    public void reset() {
        forceExpired = false;
        hasStarted = true;
        timerDuration = minTime + (long) (Math.random() * (maxTime - minTime));
        stopWatch.start();
    }

    public void restartSameTimer() {
        forceExpired = false;
        hasStarted = true;
        stopWatch.start();
    }

    public void start() {
        forceExpired = false;
        hasStarted = true;
        stopWatch.start();
    }

    public void stop() {
        hasStarted = false;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public long getTimerDuration() {
        return timerDuration;
    }

    public long getMinTime() {
        return minTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public static String secondsToFormattedString(long timeInSeconds, DurationStringFormat stringFormat) {
        long days = timeInSeconds / 86400, hours = (timeInSeconds % 86400) / 3600, minutes = ((timeInSeconds % 86400) % 3600) / 60, seconds = timeInSeconds % 60;

        if (stringFormat == DurationStringFormat.CLOCK) {
            if (timeInSeconds > 86400) {
                return getClockFormat(days) + ":" + getClockFormat(hours) + ":" + getClockFormat(minutes) + ":" + getClockFormat(seconds);
            } else if (timeInSeconds > 3600) {
                return getClockFormat(hours) + ":" + getClockFormat(minutes) + ":" + getClockFormat(seconds);
            } else {
                return getClockFormat(minutes) + ":" + getClockFormat(seconds);
            }
        } else {
            if (timeInSeconds > 86400) {
                return getClockFormat(days) + " day(s) " + getClockFormat(hours) + " hr(s) " + getClockFormat(
                        minutes) + " min(s) " + getClockFormat(seconds) + " sec(s)";
            } else if (timeInSeconds > 3600) {
                return getClockFormat(hours) + " hr(s) " + getClockFormat(minutes) + " min(s) " + getClockFormat(seconds) + " sec(s)";
            } else {
                return getClockFormat(minutes) + " min(s) " + getClockFormat(seconds) + " sec(s)";
            }
        }
    }

    private static String getClockFormat(long number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return Long.toString(number);
        }
    }
}