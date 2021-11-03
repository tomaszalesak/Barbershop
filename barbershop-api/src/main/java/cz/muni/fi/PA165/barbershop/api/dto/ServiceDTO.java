package cz.muni.fi.PA165.barbershop.api.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jakub Zich
 */
public class ServiceDTO {
    private Long id;

    private String name;

    private Integer durationMinutes;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceDTO)) return false;

        ServiceDTO that = (ServiceDTO) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDurationMinutes() != null ? !getDurationMinutes().equals(that.getDurationMinutes()) : that.getDurationMinutes() != null)
            return false;
        return getPrice() != null ? getPrice().equals(that.getPrice()) : that.getPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDurationMinutes() != null ? getDurationMinutes().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
                "name='" + name + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", price=" + price +
                '}';
    }
}
