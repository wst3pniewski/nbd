package org.example.model.vehicles;

import jakarta.persistence.Entity;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Bicycle extends Vehicle{
    public Bicycle() {}

    public Bicycle(String plateNumber, int basePrice) {
        super(plateNumber, basePrice);
    }
}
