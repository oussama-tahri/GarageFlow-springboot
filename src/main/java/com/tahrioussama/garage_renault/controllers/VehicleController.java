package com.tahrioussama.garage_renault.controllers;

import com.tahrioussama.garage_renault.entities.Vehicle;
import com.tahrioussama.garage_renault.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Vehicle Controller
 * Cahier des charges - Section 2: Gestion des véhicules
 */
@RestController
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    /**
     * Section 2: Lister les véhicules d'un garage
     * GET /api/garages/{garageId}/vehicles
     */
    @GetMapping("/api/garages/{garageId}/vehicles")
    public ResponseEntity<Page<Vehicle>> getVehiclesByGarage(
            @PathVariable Long garageId,
            Pageable pageable) {
        return ResponseEntity.ok(vehicleService.getVehiclesByGarage(garageId, pageable));
    }

    /**
     * Section 2: Lister les véhicules par marque
     * GET /api/vehicles/search/by-brand?brand=Renault
     */
    @GetMapping("/api/vehicles/search/by-brand")
    public ResponseEntity<Page<Vehicle>> getVehiclesByBrand(
            @RequestParam String brand,
            Pageable pageable) {
        return ResponseEntity.ok(vehicleService.getVehiclesByBrand(brand, pageable));
    }

    /**
     * Section 2: Ajouter un véhicule à un garage
     * POST /api/garages/{garageId}/vehicles
     */
    @PostMapping("/api/garages/{garageId}/vehicles")
    public ResponseEntity<Vehicle> addVehicleToGarage(
            @PathVariable Long garageId,
            @Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.addVehicleToGarage(garageId, vehicle));
    }

    /**
     * Section 2: Modifier un véhicule
     * PUT /api/vehicles/{id}
     */
    @PutMapping("/api/vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicle));
    }

    /**
     * Section 2: Supprimer un véhicule
     * DELETE /api/vehicles/{id}
     */
    @DeleteMapping("/api/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}