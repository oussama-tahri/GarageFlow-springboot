package com.tahrioussama.garage_renault.services;


import com.tahrioussama.garage_renault.entities.Vehicle;
import lombok.RequiredArgsConstructor;
import com.tahrioussama.garage_renault.exceptions.GarageNotFoundException;
import com.tahrioussama.garage_renault.exceptions.VehicleNotFoundException;
import com.tahrioussama.garage_renault.repositories.GarageRepository;
import com.tahrioussama.garage_renault.repositories.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Vehicle Service
 * Cahier des charges - Section 2: Gestion des véhicules
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;

    /**
     * Section 2: Lister les véhicules d'un garage spécifique
     */
    public Page<Vehicle> getVehiclesByGarage(Long garageId, Pageable pageable) {
        if (!garageRepository.existsById(garageId)) {
            throw new GarageNotFoundException(garageId);
        }
        return vehicleRepository.findByGarageId(garageId, pageable);
    }

    /**
     * Section 2: Lister tous les véhicules d'un modèle donné
     */
    public Page<Vehicle> getVehiclesByBrand(String brand, Pageable pageable) {
        return vehicleRepository.findByBrand(brand, pageable);
    }

    /**
     * Section 2: Ajout d'un véhicule à un garage
     */
    @Transactional
    public Vehicle addVehicleToGarage(Long garageId, Vehicle vehicle) {
        var garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new GarageNotFoundException(garageId));

        if (garage.getVehicles().size() >= 50) {
            throw new IllegalStateException("Garage has reached its limit of 50 vehicles");
        }

        vehicle.setGarage(garage);
        return vehicleRepository.save(vehicle);
    }

    /**
     * Section 2: Modification d'un véhicule
     */
    @Transactional
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        var existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));

        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setManufacturingYear(updatedVehicle.getManufacturingYear());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());

        return vehicleRepository.save(existingVehicle);
    }

    /**
     * Section 2: Suppression d'un véhicule
     */
    @Transactional
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException(id);
        }
        vehicleRepository.deleteById(id);
    }
}

