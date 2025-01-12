package com.tahrioussama.garage_renault.units.services;

import com.tahrioussama.garage_renault.entities.Garage;
import com.tahrioussama.garage_renault.repositories.GarageRepository;
import com.tahrioussama.garage_renault.services.GarageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageService garageService;

    private Garage garage;

    @BeforeEach
    void setUp() {
        garage = new Garage();
        garage.setId(1L);
        garage.setName("Test Garage");
    }

    @Test
    void getAllGarages_ShouldReturnPageOfGarages() {
        // Arrange
        Page<Garage> garagePage = new PageImpl<>(List.of(garage));
        when(garageRepository.findAll(any(Pageable.class))).thenReturn(garagePage);

        // Act
        Page<Garage> result = garageService.getAllGarages(Pageable.unpaged());

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test Garage");
        verify(garageRepository).findAll(any(Pageable.class));
    }

    @Test
    void getGarageById_ShouldReturnGarage() {
        // Arrange
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));

        // Act
        Garage result = garageService.getGarageById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Garage");
        verify(garageRepository).findById(1L);
    }

    @Test
    void createGarage_ShouldReturnCreatedGarage() {
        // Arrange
        when(garageRepository.save(garage)).thenReturn(garage);

        // Act
        Garage result = garageService.createGarage(garage);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Garage");
        verify(garageRepository).save(garage);
    }
}
