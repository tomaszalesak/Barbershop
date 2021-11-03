package cz.muni.fi.PA165.barbershop.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimeFrameDTO {

    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    public TimeFrameDTO (){}

    public TimeFrameDTO (LocalDateTime from, LocalDateTime to) {
        setFromTime(from);
        setToTime(to);
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
        if (!(o instanceof TimeFrameDTO)) return false;
        TimeFrameDTO that = (TimeFrameDTO) o;
        return Objects.equals(getFromTime(), that.getFromTime()) && Objects.equals(getToTime(), that.getToTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromTime(), getToTime());
    }
}
