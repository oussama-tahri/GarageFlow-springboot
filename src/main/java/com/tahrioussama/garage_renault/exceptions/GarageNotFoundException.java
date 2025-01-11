package com.tahrioussama.garage_renault.exceptions;

public class GarageNotFoundException extends GarageManagementException {
    public GarageNotFoundException(Long id) {
        super(String.format("Garage with id %d not found", id));
    }
}
