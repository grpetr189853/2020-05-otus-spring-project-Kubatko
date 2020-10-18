package ru.skubatko.dev.ees.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EesCriterionDto {

    private String name="";
    private Integer weight = 0;
    private Integer rating = 0;

    public EesCriterionDto(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }
}
