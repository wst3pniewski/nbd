package org.example.model.clients;

public abstract class ClientType {
    public abstract int getMaxVehicles();
    public abstract String getTypeInfo();
    public abstract double getDiscount(double price);
}
