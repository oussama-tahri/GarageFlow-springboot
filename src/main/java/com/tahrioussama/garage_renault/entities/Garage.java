package com.tahrioussama.garage_renault.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "garage_opening_hours", joinColumns = @JoinColumn(name = "garage_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "opening_times")
    private Map<DayOfWeek, List<OpeningTime>> openingHours;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vehicle> vehicles = new ArrayList<>();

    /**
     * Business Rule: Maximum 50 vehicles per garage
     */
    @PrePersist
    @PreUpdate
    private void validateVehicleLimit() {
        if (vehicles.size() > 50) {
            throw new IllegalStateException("Un garage ne peut pas avoir plus de 50 véhicules");
        }
    }
}
