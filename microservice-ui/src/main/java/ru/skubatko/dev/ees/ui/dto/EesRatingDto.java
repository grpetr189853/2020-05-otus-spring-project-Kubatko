package ru.skubatko.dev.ees.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EesRatingDto {

    private String employerName = "";
    private Integer employerRating = 0;
}
