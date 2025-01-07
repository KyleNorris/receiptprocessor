package com.kylenorris.rewards.recieptprocessor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptRequest {

    @NotBlank(message = "Retailer name cannot be blank")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer name contains invalid characters")
    private String retailer;

    @NotBlank(message = "Purchase date cannot be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Purchase date must be in the format YYYY-MM-DD")
    private String purchaseDate;

    @NotBlank(message = "Purchase time cannot be blank")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Purchase time must be in the format HH:mm")
    private String purchaseTime;

    @NotNull(message = "Items cannot be null")
    @Size(min = 1, message = "At least one item must be provided")
    @Valid
    private List<Item> items;

    @NotBlank(message = "Total amount cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be a valid amount (e.g., 6.49)")
    private String total;


}
