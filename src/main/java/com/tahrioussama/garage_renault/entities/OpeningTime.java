package com.tahrioussama.garage_renault.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OpeningTime {
    private LocalTime startTime;
    private LocalTime endTime;
}
