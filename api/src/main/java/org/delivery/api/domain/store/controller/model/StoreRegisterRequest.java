package org.delivery.api.domain.store.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.store.enums.StoreCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private StoreCategory category;

    @NotBlank
    private String thumbnailUrl;

    @NotNull
    private BigDecimal minimumAccount;

    @NotNull
    private BigDecimal minimumDeliveryAmount;

    @NotBlank
    private String phoneNumber;
}
