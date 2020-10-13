package ru.skubatko.dev.ees.criteria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CriterionDto {

    @NotBlank(message = "Employers's name is mandatory")
    private String name;
}
