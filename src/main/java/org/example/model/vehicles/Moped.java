package org.example.model.vehicles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class Moped extends MotorVehicle{
    public Moped() {
    }

    public Moped(String plateNumber, int basePrice, int engineDisplacement) {
        super(plateNumber, basePrice, engineDisplacement);
    }
}
