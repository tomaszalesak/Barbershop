package cz.muni.fi.PA165.barbershop.api.dto;

import java.time.LocalDateTime;

public class WorkingHoursCreateDTO {

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

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
        if (!(o instanceof WorkingHoursCreateDTO)) return false;

        WorkingHoursCreateDTO that = (WorkingHoursCreateDTO) o;

        if (fromTime != null ? !fromTime.equals(that.fromTime) : that.fromTime != null) return false;
        return toTime != null ? toTime.equals(that.toTime) : that.toTime == null;
    }

    @Override
    public int hashCode() {
        int result = fromTime != null ? fromTime.hashCode() : 0;
        result = 31 * result + (toTime != null ? toTime.hashCode() : 0);
        return result;
    }
}
