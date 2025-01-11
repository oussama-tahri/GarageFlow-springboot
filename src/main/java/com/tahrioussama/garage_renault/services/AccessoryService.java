package com.tahrioussama.garage_renault.services;

import com.tahrioussama.garage_renault.entities.Accessory;
import com.tahrioussama.garage_renault.repositories.AccessoryRepository;
import com.tahrioussama.garage_renault.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import com.tahrioussama.garage_renault.exceptions.AccessoryNotFoundException;
import com.tahrioussama.garage_renault.exceptions.VehicleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Accessory Service
 * Cahier des charges - Section 3: Gestion des accessoires
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessoryService {
    private final AccessoryRepository accessoryRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * Section 3: Lister les accessoires d'un véhicule
     */
    public Page<Accessory> getAccessoriesByVehicle(Long vehicleId, Pageable pageable) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new VehicleNotFoundException(vehicleId);
        }
        return accessoryRepository.findByVehicleId(vehicleId, pageable);
    }

    /**
     * Section 3: Ajout d'un accessoire à un véhicule
     */
    @Transactional
    public Accessory addAccessoryToVehicle(Long vehicleId, Accessory accessory) {
        var vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        accessory.setVehicle(vehicle);
        return accessoryRepository.save(accessory);
    }

    /**
     * Section 3: Modification d'un accessoire
     */
    @Transactional
    public Accessory updateAccessory(Long id, Accessory updatedAccessory) {
        var existingAccessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new AccessoryNotFoundException(id));

        existingAccessory.setName(updatedAccessory.getName());
        existingAccessory.setDescription(updatedAccessory.getDescription());
        existingAccessory.setPrice(updatedAccessory.getPrice());
        existingAccessory.setType(updatedAccessory.getType());

        return accessoryRepository.save(existingAccessory);
    }

    /**
     * Section 3: Suppression d'un accessoire
     */
    @Transactional
    public void deleteAccessory(Long id) {
        if (!accessoryRepository.existsById(id)) {
            throw new AccessoryNotFoundException(id);
        }
        accessoryRepository.deleteById(id);
    }
}

