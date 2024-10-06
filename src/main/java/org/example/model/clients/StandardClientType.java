package org.example.model.clients;

public class StandardClientType extends ClientType{
    @Override
    public int getMaxVehicles() {
        return 0;
    }

    @Override
    public String getTypeInfo() {
        return "";
    }

    @Override
    public double getDiscount(double price) {
        return 0;
    }
}
