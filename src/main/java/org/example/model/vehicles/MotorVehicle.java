package org.example.model.vehicles;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class MotorVehicle extends Vehicle {
    private int engineDisplacement;

    public MotorVehicle() {}

    @Override
    public float getActualRentalPrice() {
        float factor;
        if (this.engineDisplacement < 1000){
            factor = 1.0f;
        } else if (this.engineDisplacement < 2000){
            factor = 1.5f;
        } else {
            factor = this.engineDisplacement * 0.0005f + 0.5f;
        }
        return factor * super.getBasePrice();
    }

    public MotorVehicle(String plateNumber, int basePrice, int engineDisplacement) {
        super(plateNumber, basePrice);
        this.engineDisplacement = engineDisplacement;
    }

    public int getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("plateNumber", getPlateNumber())
                .append("basePrice", getBasePrice())
                .append("actualRentalPrice", getActualRentalPrice())
                .append("engineDisplacement", engineDisplacement)
                .append("archive", isArchive())
                .toString();
    }
}
