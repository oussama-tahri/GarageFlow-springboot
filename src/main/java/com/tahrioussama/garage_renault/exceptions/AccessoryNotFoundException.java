package com.tahrioussama.garage_renault.exceptions;

public class AccessoryNotFoundException extends GarageManagementException {
    public AccessoryNotFoundException(Long id) {
        super(String.format("Accessory with id %d not found", id));
    }
}
