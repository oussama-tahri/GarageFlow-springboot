package com.tahrioussama.garage_renault.services;


import com.tahrioussama.garage_renault.entities.Garage;
import com.tahrioussama.garage_renault.enums.AccessoryType;
import com.tahrioussama.garage_renault.enums.FuelType;
import lombok.RequiredArgsConstructor;
import com.tahrioussama.garage_renault.exceptions.GarageNotFoundException;
import com.tahrioussama.garage_renault.repositories.GarageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Garage Service
 * Implements all garage-related business operations from the cahier des charges
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GarageService {
    private final GarageRepository garageRepository;

    /**
     * Section 1: Liste paginée de tous les garages
     */
    public Page<Garage> getAllGarages(Pageable pageable) {
        return garageRepository.findAll(pageable);
    }

    /**
     * Section 1: Récupération d'un garage spécifique
     */
    public Garage getGarageById(Long id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new GarageNotFoundException(id));
    }

    /**
     * Section 1: Création d'un garage
     */
    @Transactional
    public Garage createGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    /**
     * Section 1: Modification d'un garage
     */
    @Transactional
    public Garage updateGarage(Long id, Garage updatedGarage) {
        var existingGarage = garageRepository.findById(id)
                .orElseThrow(() -> new GarageNotFoundException(id));

        existingGarage.setName(updatedGarage.getName());
        existingGarage.setAddress(updatedGarage.getAddress());
        existingGarage.setTelephone(updatedGarage.getTelephone());
        existingGarage.setEmail(updatedGarage.getEmail());
        existingGarage.setOpeningHours(updatedGarage.getOpeningHours());

        return garageRepository.save(existingGarage);
    }

    /**
     * Section 1: Suppression d'un garage
     */
    @Transactional
    public void deleteGarage(Long id) {
        if (!garageRepository.existsById(id)) {
            throw new GarageNotFoundException(id);
        }
        garageRepository.deleteById(id);
    }

    /**
     * Section 4: Recherche de garages par type de véhicule
     */
    public Page<Garage> findGaragesByVehicleBrand(String brand, Pageable pageable) {
        return garageRepository.findByVehicleBrand(brand, pageable);
    }

    /**
     * Section 4: Recherche de garages par type de carburant
     */
    public Page<Garage> findGaragesByFuelType(FuelType fuelType, Pageable pageable) {
        return garageRepository.findByVehicleFuelType(fuelType, pageable);
    }

    /**
     * Section 4: Recherche de garages par type d'accessoire
     */
    public Page<Garage> findGaragesByAccessoryType(AccessoryType accessoryType, Pageable pageable) {
        return garageRepository.findByAccessoryType(accessoryType, pageable);
    }
}