package ru.skubatko.dev.ees.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EesEmployerDto {

    private String name = "";
    private List<EesCriterionDto> criteria = new ArrayList<>();
}
