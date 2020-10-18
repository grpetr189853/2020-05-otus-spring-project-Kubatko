package ru.skubatko.dev.ees.employers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmployerDto {

    @NotBlank(message = "Employers's name is mandatory")
    private String name;
}
