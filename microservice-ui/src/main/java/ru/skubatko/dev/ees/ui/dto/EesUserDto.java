package ru.skubatko.dev.ees.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EesUserDto {

    private String name = "";
    private String password = "";
    private Boolean isActive = Boolean.TRUE;
    private String roles = "";
}
