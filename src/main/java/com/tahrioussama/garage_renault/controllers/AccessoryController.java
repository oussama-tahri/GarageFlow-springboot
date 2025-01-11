package com.tahrioussama.garage_renault.controllers;

import com.tahrioussama.garage_renault.entities.Accessory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.tahrioussama.garage_renault.services.AccessoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Accessory Controller
 * Cahier des charges - Section 3: Gestion des accessoires
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccessoryController {
    private final AccessoryService accessoryService;

    /**
     * Section 3: Lister les accessoires d'un véhicule
     * GET /api/vehicles/{vehicleId}/accessories
     */
    @GetMapping("/vehicles/{vehicleId}/accessories")
    public ResponseEntity<Page<Accessory>> getAccessoriesByVehicle(
            @PathVariable Long vehicleId,
            Pageable pageable) {
        return ResponseEntity.ok(accessoryService.getAccessoriesByVehicle(vehicleId, pageable));
    }

    /**
     * Section 3: Ajouter un accessoire à un véhicule
     * POST /api/vehicles/{vehicleId}/accessories
     */
    @PostMapping("/vehicles/{vehicleId}/accessories")
    public ResponseEntity<Accessory> addAccessoryToVehicle(
            @PathVariable Long vehicleId,
            @Valid @RequestBody Accessory accessory) {
        return ResponseEntity.ok(accessoryService.addAccessoryToVehicle(vehicleId, accessory));
    }

    /**
     * Section 3: Modifier un accessoire
     * PUT /api/accessories/{id}
     */
    @PutMapping("/accessories/{id}")
    public ResponseEntity<Accessory> updateAccessory(
            @PathVariable Long id,
            @Valid @RequestBody Accessory accessory) {
        return ResponseEntity.ok(accessoryService.updateAccessory(id, accessory));
    }

    /**
     * Section 3: Supprimer un accessoire
     * DELETE /api/accessories/{id}
     */
    @DeleteMapping("/accessories/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}