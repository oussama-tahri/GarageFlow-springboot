package com.tahrioussama.garage_renault.units.services;

import com.tahrioussama.garage_renault.entities.Accessory;
import com.tahrioussama.garage_renault.entities.Vehicle;
import com.tahrioussama.garage_renault.enums.AccessoryType;
import com.tahrioussama.garage_renault.repositories.AccessoryRepository;
import com.tahrioussama.garage_renault.repositories.VehicleRepository;
import com.tahrioussama.garage_renault.services.AccessoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessoryServiceTest {

    @Mock
    private AccessoryRepository accessoryRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private AccessoryService accessoryService;

    private Accessory accessory;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        accessory = new Accessory();
        accessory.setId(1L);
        accessory.setName("Test Accessory");
        accessory.setType(AccessoryType.INTERIOR);
        accessory.setPrice(BigDecimal.valueOf(100));

        vehicle = new Vehicle();
        vehicle.setId(1L);
    }

    @Test
    void getAccessoriesByVehicle_ShouldReturnAccessories() {
        // Arrange
        Page<Accessory> accessoryPage = new PageImpl<>(List.of(accessory));
        when(vehicleRepository.existsById(1L)).thenReturn(true);
        when(accessoryRepository.findByVehicleId(eq(1L), any(Pageable.class))).thenReturn(accessoryPage);

        // Act
        Page<Accessory> result = accessoryService.getAccessoriesByVehicle(1L, Pageable.unpaged());

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test Accessory");
        verify(accessoryRepository).findByVehicleId(eq(1L), any(Pageable.class));
    }

    @Test
    void addAccessoryToVehicle_ShouldReturnAddedAccessory() {
        // Arrange
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(accessoryRepository.save(any(Accessory.class))).thenReturn(accessory);

        // Act
        Accessory result = accessoryService.addAccessoryToVehicle(1L, accessory);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Accessory");
        verify(accessoryRepository).save(any(Accessory.class));
    }
}
