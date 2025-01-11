package com.tahrioussama.garage_renault.exceptions;

public class VehicleNotFoundException extends GarageManagementException {
    public VehicleNotFoundException(Long id) {
        super(String.format("Vehicle with id %d not found", id));
    }
}
