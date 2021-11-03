package cz.muni.fi.PA165.barbershop.api.dto;

import java.util.Objects;

public class ReservationUpdateFeedbackDTO {

    private long id;

    private String feedbackText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationUpdateFeedbackDTO)) return false;
        ReservationUpdateFeedbackDTO that = (ReservationUpdateFeedbackDTO) o;
        return getId() == that.getId() && Objects.equals(getFeedbackText(), that.getFeedbackText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFeedbackText());
    }
}
