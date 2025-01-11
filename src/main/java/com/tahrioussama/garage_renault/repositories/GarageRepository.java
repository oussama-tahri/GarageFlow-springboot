package com.tahrioussama.garage_renault.repositories;

import com.tahrioussama.garage_renault.entities.Garage;
import com.tahrioussama.garage_renault.enums.AccessoryType;
import com.tahrioussama.garage_renault.enums.FuelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

/**
 * Garage Repository
 * Cahier des charges - Section 1 & 4: Gestion des garages et Requêtes de recherche
 */
public interface GarageRepository extends JpaRepository<Garage, Long> {
    /**
     * Section 1: Liste paginée de tous les garages
     * La pagination et le tri sont gérés par le Pageable
     * Exemple d'utilisation:
     * - Sort par nom: Sort.by("name")
     * - Sort par ville: Sort.by("address")
     * - Multiple sorts: Sort.by("name").and(Sort.by("address"))
     */
    @Override
    @NonNull
    Page<Garage> findAll(@NonNull Pageable pageable);

    /**
     * Section 4: Rechercher des garages en fonction du type de véhicule
     */
    @Query("SELECT DISTINCT g FROM Garage g JOIN g.vehicles v WHERE v.brand = :brand")
    Page<Garage> findByVehicleBrand(@Param("brand") String brand, Pageable pageable);

    /**
     * Section 4: Rechercher des garages en fonction du type de carburant
     */
    @Query("SELECT DISTINCT g FROM Garage g JOIN g.vehicles v WHERE v.fuelType = :fuelType")
    Page<Garage> findByVehicleFuelType(@Param("fuelType") FuelType fuelType, Pageable pageable);

    /**
     * Section 4: Rechercher des garages ayant un accessoire particulier
     */
    @Query("SELECT DISTINCT g FROM Garage g JOIN g.vehicles v JOIN v.accessories a WHERE a.type = :accessoryType")
    Page<Garage> findByAccessoryType(@Param("accessoryType") AccessoryType accessoryType, Pageable pageable);
}
