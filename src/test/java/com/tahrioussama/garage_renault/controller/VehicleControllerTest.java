package com.tahrioussama.garage_renault.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahrioussama.garage_renault.entities.Vehicle;
import com.tahrioussama.garage_renault.enums.FuelType;
import com.tahrioussama.garage_renault.controllers.VehicleController;
import com.tahrioussama.garage_renault.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrand("Renault");
        vehicle.setManufacturingYear(2023);
        vehicle.setFuelType(FuelType.ELECTRIC);
    }

    @Test
    void getVehiclesByGarage_ShouldReturnVehicles() throws Exception {
        // Arrange
        Page<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicle));
        when(vehicleService.getVehiclesByGarage(eq(1L), any())).thenReturn(vehiclePage);

        // Act & Assert
        mockMvc.perform(get("/api/garages/1/vehicles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].brand").value("Renault"));
    }

    @Test
    void getVehiclesByBrand_ShouldReturnVehicles() throws Exception {
        // Arrange
        Page<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicle));
        when(vehicleService.getVehiclesByBrand(eq("Renault"), any())).thenReturn(vehiclePage);

        // Act & Assert
        mockMvc.perform(get("/api/vehicles/search/by-brand")
                        .param("brand", "Renault"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].brand").value("Renault"));
    }

    @Test
    void addVehicleToGarage_ShouldReturnCreatedVehicle() throws Exception {
        // Arrange
        when(vehicleService.addVehicleToGarage(eq(1L), any())).thenReturn(vehicle);

        // Act & Assert
        mockMvc.perform(post("/api/garages/1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Renault"));
    }

    @Test
    void updateVehicle_ShouldReturnUpdatedVehicle() throws Exception {
        // Arrange
        when(vehicleService.updateVehicle(eq(1L), any())).thenReturn(vehicle);

        // Act & Assert
        mockMvc.perform(put("/api/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Renault"));
    }

    @Test
    void deleteVehicle_ShouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }
}
