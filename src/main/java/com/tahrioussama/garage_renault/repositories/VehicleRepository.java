package com.tahrioussama.garage_renault.repositories;

import com.tahrioussama.garage_renault.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Page<Vehicle> findByGarageId(Long garageId, Pageable pageable);
    Page<Vehicle> findByBrand(String brand, Pageable pageable);
}
