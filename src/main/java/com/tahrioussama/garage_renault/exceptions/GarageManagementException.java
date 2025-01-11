package com.tahrioussama.garage_renault.exceptions;

/**
 * Base exception class for all garage management exceptions
 */
public abstract class GarageManagementException extends RuntimeException {
    public GarageManagementException(String message) {
        super(message);
    }
}
