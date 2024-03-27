package org.deliveryservice.storeadmin.domain.userorder.controller.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.deliveryservice.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse;
    private List<StoreMenuResponse> storeMenuResponseList;

}
