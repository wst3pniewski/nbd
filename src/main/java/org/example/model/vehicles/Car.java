package org.example.model.vehicles;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@Access(AccessType.FIELD)
public class Car extends MotorVehicle {
    public enum Segment {
        A , B, C, D, E
    }
    private Segment segment;

    public Car(String plateNumber, int basePrice, int engineDisplacement, Segment segment) {
        super(plateNumber, basePrice, engineDisplacement);
        this.segment = segment;
    }

    public Car() {}

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    @Override
    public float getActualRentalPrice() {
        return super.getActualRentalPrice() * (1 + segment.ordinal() / 10.0f);
    }
}
