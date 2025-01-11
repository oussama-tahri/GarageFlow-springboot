package com.tahrioussama.garage_renault.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tahrioussama.garage_renault.enums.FuelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle Entity
 * Cahier des charges - Section 2: Gestion des véhicules
 * - Required fields: brand, manufacturingYear, fuelType
 * - Can be associated with multiple accessories
 * - Must be linked to a garage
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Brand is required")
    private String brand;

    @NotNull(message = "Manufacturing year is required")
    private Integer manufacturingYear;

    @NotNull(message = "Fuel type is required")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    @JsonBackReference
    private Garage garage;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accessory> accessories = new ArrayList<>();
}
