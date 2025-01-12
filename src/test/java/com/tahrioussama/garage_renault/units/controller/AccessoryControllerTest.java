package com.tahrioussama.garage_renault.units.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahrioussama.garage_renault.entities.Accessory;
import com.tahrioussama.garage_renault.enums.AccessoryType;
import com.tahrioussama.garage_renault.controllers.AccessoryController;
import com.tahrioussama.garage_renault.services.AccessoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccessoryController.class)
class AccessoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessoryService accessoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Accessory accessory;

    @BeforeEach
    void setUp() {
        accessory = new Accessory();
        accessory.setId(1L);
        accessory.setName("Test Accessory");
        accessory.setType(AccessoryType.INTERIOR);
        accessory.setPrice(BigDecimal.valueOf(100));
    }

    @Test
    void getAccessoriesByVehicle_ShouldReturnAccessories() throws Exception {
        // Arrange
        Page<Accessory> accessoryPage = new PageImpl<>(List.of(accessory));
        when(accessoryService.getAccessoriesByVehicle(eq(1L), any())).thenReturn(accessoryPage);

        // Act & Assert
        mockMvc.perform(get("/api/vehicles/1/accessories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value("Test Accessory"));
    }

    @Test
    void addAccessoryToVehicle_ShouldReturnCreatedAccessory() throws Exception {
        // Arrange
        when(accessoryService.addAccessoryToVehicle(eq(1L), any())).thenReturn(accessory);

        // Act & Assert
        mockMvc.perform(post("/api/vehicles/1/accessories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Accessory"));
    }
}
