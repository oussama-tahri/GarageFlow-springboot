package com.tahrioussama.garage_renault.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tahrioussama.garage_renault.enums.AccessoryType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private AccessoryType type;

    @ManyToOne
    @JsonIgnore
    private Vehicle vehicle;
}
