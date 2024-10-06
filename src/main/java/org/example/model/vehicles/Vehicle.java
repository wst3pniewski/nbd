package org.example.model.vehicles;

import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private int basePrice;

    @Column(nullable = false)
    private boolean archive;

    public Vehicle(){}

    public Vehicle(String plateNumber, int basePrice){
        this.plateNumber = plateNumber;
        if (basePrice < 0){
            throw new IllegalArgumentException("Base price cannot be negative");
        } else {
            this.basePrice = basePrice;
        }
    }

    public float getActualRentalPrice(){
        return basePrice;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        if (basePrice < 0){
            throw new IllegalArgumentException("Base price cannot be negative");
        } else {
            this.basePrice = basePrice;
        }
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
