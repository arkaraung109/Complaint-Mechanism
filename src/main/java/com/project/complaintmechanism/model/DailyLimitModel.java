package com.project.complaintmechanism.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyLimitModel {

    private long id;

    @NotNull(message = "Please enter daily limit")
    @Min(value = 1, message = "Please enter between 1 and 1000")
    @Max(value = 1000, message = "Please enter between 1 and 1000")
    private int maxLimit;

}
