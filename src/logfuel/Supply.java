package logfuel;

import java.util.Date;

public class Supply {

    private Date supplyDate = null;

    private Integer currentMileage = null;

    private Float gasolineLiters = null;

    private Float costPerLiter = null;

    public Supply() {
        super();
    }

    public Supply(Date supplyDate, Integer currentMileage, Float gasolineLiters, Float costPerLiter) {
        super();
        this.supplyDate = supplyDate;
        this.currentMileage = currentMileage;
        this.gasolineLiters = gasolineLiters;
        this.costPerLiter = costPerLiter;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public Integer getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(Integer currentMileage) {
        this.currentMileage = currentMileage;
    }

    public Float getGasolineLiters() {
        return gasolineLiters;
    }

    public void setGasolineLiters(Float gasolineLiters) {
        this.gasolineLiters = gasolineLiters;
    }

    public Float getCostPerLiter() {
        return costPerLiter;
    }

    public void setCostPerLiter(Float costPerLiter) {
        this.costPerLiter = costPerLiter;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((costPerLiter == null) ? 0 : costPerLiter.hashCode());
        result = prime * result + ((currentMileage == null) ? 0 : currentMileage.hashCode());
        result = prime * result + ((gasolineLiters == null) ? 0 : gasolineLiters.hashCode());
        result = prime * result + ((supplyDate == null) ? 0 : supplyDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Supply other = (Supply) obj;
        if (costPerLiter == null) {
            if (other.costPerLiter != null)
                return false;
        } else if (!costPerLiter.equals(other.costPerLiter))
            return false;
        if (currentMileage == null) {
            if (other.currentMileage != null)
                return false;
        } else if (!currentMileage.equals(other.currentMileage))
            return false;
        if (gasolineLiters == null) {
            if (other.gasolineLiters != null)
                return false;
        } else if (!gasolineLiters.equals(other.gasolineLiters))
            return false;
        if (supplyDate == null) {
            if (other.supplyDate != null)
                return false;
        } else if (!supplyDate.equals(other.supplyDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Supply [supplyDate=" + supplyDate + ", currentMileage=" + currentMileage + ", gasolineLiters="
                + gasolineLiters + ", costPerLiter=" + costPerLiter + "]";
    }

}
