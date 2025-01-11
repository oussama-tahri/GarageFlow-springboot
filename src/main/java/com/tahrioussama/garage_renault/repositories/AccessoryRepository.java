package com.tahrioussama.garage_renault.repositories;

import com.tahrioussama.garage_renault.entities.Accessory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    Page<Accessory> findByVehicleId(Long vehicleId, Pageable pageable);
}
