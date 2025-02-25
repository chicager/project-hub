package org.example.work_with_properties_spring_boot.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Features {
    @NotNull
    private Boolean enabled;

    @Min(1)
    private int maxItems;
}
