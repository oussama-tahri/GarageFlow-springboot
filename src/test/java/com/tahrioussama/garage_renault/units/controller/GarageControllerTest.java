package com.tahrioussama.garage_renault.units.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahrioussama.garage_renault.entities.Garage;
import com.tahrioussama.garage_renault.controllers.GarageController;
import com.tahrioussama.garage_renault.services.GarageService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GarageController.class)
class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageService garageService;

    @Autowired
    private ObjectMapper objectMapper;

    private Garage garage;

    @BeforeEach
    void setUp() {
        garage = new Garage();
        garage.setId(1L);
        garage.setName("Test Garage");
    }

    @Test
    void getAllGarages_ShouldReturnGarages() throws Exception {
        // Arrange
        Page<Garage> garagePage = new PageImpl<>(List.of(garage));
        when(garageService.getAllGarages(any())).thenReturn(garagePage);

        // Act & Assert
        mockMvc.perform(get("/api/garages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value("Test Garage"));
    }

    @Test
    void getGarageById_ShouldReturnGarage() throws Exception {
        // Arrange
        when(garageService.getGarageById(1L)).thenReturn(garage);

        // Act & Assert
        mockMvc.perform(get("/api/garages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Garage"));
    }

    @Test
    void createGarage_ShouldReturnCreatedGarage() throws Exception {
        // Arrange
        when(garageService.createGarage(any())).thenReturn(garage);

        // Act & Assert
        mockMvc.perform(post("/api/garages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Garage"));
    }
}
