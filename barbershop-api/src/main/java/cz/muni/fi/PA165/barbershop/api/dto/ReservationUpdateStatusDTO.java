package cz.muni.fi.PA165.barbershop.api.dto;

import java.util.Objects;

public class ReservationUpdateStatusDTO {
    private long id;

    private boolean status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationUpdateStatusDTO)) return false;
        ReservationUpdateStatusDTO that = (ReservationUpdateStatusDTO) o;
        return getId() == that.getId() && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus());
    }
}
