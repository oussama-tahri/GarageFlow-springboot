package com.tahrioussama.garage_renault.controllers;

import com.tahrioussama.garage_renault.entities.Garage;
import com.tahrioussama.garage_renault.enums.AccessoryType;
import com.tahrioussama.garage_renault.enums.FuelType;
import com.tahrioussama.garage_renault.services.GarageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Garage Controller
 * Implements all garage-related endpoints from the cahier des charges
 */
@RestController
@RequestMapping("/api/garages")
@RequiredArgsConstructor
public class GarageController {
    private final GarageService garageService;

    /**
     * Section 1: Liste paginée de tous les garages
     * GET /api/garages?page=0&size=10&sort=name,asc
     */
    @GetMapping
    public ResponseEntity<Page<Garage>> getAllGarages(Pageable pageable) {
        return ResponseEntity.ok(garageService.getAllGarages(pageable));
    }

    /**
     * Section 1: Récupération d'un garage spécifique
     * GET /api/garages/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Garage> getGarageById(@PathVariable Long id) {
        return ResponseEntity.ok(garageService.getGarageById(id));
    }

    /**
     * Section 1: Création d'un garage
     * POST /api/garages
     */
    @PostMapping
    public ResponseEntity<Garage> createGarage(@Valid @RequestBody Garage garage) {
        return ResponseEntity.ok(garageService.createGarage(garage));
    }

    /**
     * Section 1: Modification d'un garage
     * PUT /api/garages/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Garage> updateGarage(
            @PathVariable Long id,
            @Valid @RequestBody Garage garage) {
        return ResponseEntity.ok(garageService.updateGarage(id, garage));
    }

    /**
     * Section 1: Suppression d'un garage
     * DELETE /api/garages/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Section 4: Recherche de garages par marque de véhicule
     * GET /api/garages/search/by-brand?brand=Renault
     */
    @GetMapping("/search/by-brand")
    public ResponseEntity<Page<Garage>> findGaragesByVehicleBrand(
            @RequestParam String brand,
            Pageable pageable) {
        return ResponseEntity.ok(garageService.findGaragesByVehicleBrand(brand, pageable));
    }

    /**
     * Section 4: Recherche de garages par type de carburant
     * GET /api/garages/search/by-fuel-type?fuelType=ELECTRIC
     */
    @GetMapping("/search/by-fuel-type")
    public ResponseEntity<Page<Garage>> findGaragesByFuelType(
            @RequestParam FuelType fuelType,
            Pageable pageable) {
        return ResponseEntity.ok(garageService.findGaragesByFuelType(fuelType, pageable));
    }

    /**
     * Section 4: Recherche de garages par type d'accessoire
     * GET /api/garages/search/by-accessory-type?accessoryType=SAFETY
     */
    @GetMapping("/search/by-accessory-type")
    public ResponseEntity<Page<Garage>> findGaragesByAccessoryType(
            @RequestParam AccessoryType accessoryType,
            Pageable pageable) {
        return ResponseEntity.ok(garageService.findGaragesByAccessoryType(accessoryType, pageable));
    }
}