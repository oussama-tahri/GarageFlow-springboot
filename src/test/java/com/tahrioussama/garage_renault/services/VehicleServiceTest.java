package com.tahrioussama.garage_renault.services;

import com.tahrioussama.garage_renault.entities.Garage;
import com.tahrioussama.garage_renault.entities.Vehicle;
import com.tahrioussama.garage_renault.enums.FuelType;
import com.tahrioussama.garage_renault.repositories.GarageRepository;
import com.tahrioussama.garage_renault.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private Garage garage;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrand("Renault");
        vehicle.setManufacturingYear(2023);
        vehicle.setFuelType(FuelType.ELECTRIC);

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrand("Renault");
        vehicle.setManufacturingYear(2023);
        vehicle.setFuelType(FuelType.ELECTRIC);

        garage = new Garage();
        garage.setId(1L);
        garage.setName("Test Garage");
        garage.setVehicles(new ArrayList<>()); // To initialize the vehicles list
    }

    @Test
    void getVehiclesByGarage_ShouldReturnVehicles() {
        // Arrange
        Page<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicle));
        when(garageRepository.existsById(1L)).thenReturn(true);
        when(vehicleRepository.findByGarageId(eq(1L), any(Pageable.class))).thenReturn(vehiclePage);
        // Act
        Page<Vehicle> result = vehicleService.getVehiclesByGarage(1L, Pageable.unpaged());

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("Renault");
        verify(vehicleRepository).findByGarageId(eq(1L), any(Pageable.class));
    }

    @Test
    void getVehiclesByBrand_ShouldReturnVehicles() {
        // Arrange
        Page<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicle));
        when(vehicleRepository.findByBrand(eq("Renault"), any(Pageable.class))).thenReturn(vehiclePage);

        // Act
        Page<Vehicle> result = vehicleService.getVehiclesByBrand("Renault", Pageable.unpaged());

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("Renault");
        verify(vehicleRepository).findByBrand(eq("Renault"), any(Pageable.class));
    }

    @Test
    void addVehicleToGarage_ShouldReturnAddedVehicle() {
        // Arrange
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Act
        Vehicle result = vehicleService.addVehicleToGarage(1L, vehicle);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo("Renault");
        verify(vehicleRepository).save(any(Vehicle.class));
    }

    @Test
    void addVehicleToGarage_WhenGarageIsFull_ShouldThrowException() {
        // Arrange
        for (int i = 0; i < 50; i++) {
            garage.getVehicles().add(new Vehicle());
        }
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));

        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
                vehicleService.addVehicleToGarage(1L, vehicle)
        );
    }

    @Test
    void updateVehicle_ShouldReturnUpdatedVehicle() {
        // Arrange
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Act
        Vehicle result = vehicleService.updateVehicle(1L, vehicle);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo("Renault");
        verify(vehicleRepository).save(any(Vehicle.class));
    }
}
