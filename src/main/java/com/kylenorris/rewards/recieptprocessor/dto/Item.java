package com.kylenorris.rewards.recieptprocessor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Valid
public class Item {
    @NotBlank(message = "Short description cannot be blank")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description contains invalid characters")
    private String shortDescription;

    @NotBlank(message = "Price cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be a valid amount (e.g., 6.49)")
    private String price;

}
