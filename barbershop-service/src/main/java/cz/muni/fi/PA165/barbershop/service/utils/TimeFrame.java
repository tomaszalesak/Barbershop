package cz.muni.fi.PA165.barbershop.service.utils;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Konstantin Yarovoy
 */
public class TimeFrame implements Comparable<TimeFrame> {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    public boolean fitsCompletely(TimeFrame other) {
        return (fromTime.isAfter(other.getFromTime()) || fromTime.isEqual(other.getFromTime()))  && (toTime.isBefore(other.getToTime()) || toTime.isEqual(other.getToTime()));
    }

    public boolean haveIntersection(TimeFrame other) {
        return (fromTime.isBefore(other.getFromTime()) && toTime.isAfter(other.getFromTime())) ||
                (fromTime.isBefore(other.getToTime()) && toTime.isAfter(other.getToTime())) ||
                this.fitsCompletely(other) ||
                other.fitsCompletely(this);
    }

    public static TimeFrame of (LocalDateTime from, LocalDateTime to) {
        return new TimeFrame(from, to);
    }

    public TimeFrame(LocalDateTime from, LocalDateTime to) {
        this.fromTime = from;
        this.toTime = to;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeFrame)) return false;
        TimeFrame timeFrame = (TimeFrame) o;
        return Objects.equals(getFromTime(), timeFrame.getFromTime()) && Objects.equals(getToTime(), timeFrame.getToTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromTime(), getToTime());
    }

    @Override
    public int compareTo(@NonNull TimeFrame o) {
        if (this.equals(o)) {
            return 0;
        }
        if (this.getFromTime().isBefore(o.getFromTime())) {
            return -1;
        }
        return 1;
    }
}
