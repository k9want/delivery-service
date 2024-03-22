package org.delivery.api.domain.storemenu.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.db.storemenu.StoreMenuEntity;

@RequiredArgsConstructor
@Business
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;


    public StoreMenuResponse register(
        StoreMenuRegisterRequest request
    ) {
        // req -> entity -> save -> response
        StoreMenuEntity entity = storeMenuConverter.toEntity(request);
        StoreMenuEntity newEntity = storeMenuService.register(entity);
        StoreMenuResponse response = storeMenuConverter.toResponse(newEntity);
        return response;
    }

    // 특정 가게에 있는 모든 메뉴
    public List<StoreMenuResponse> search(
        Long storeId
    ) {
        List<StoreMenuEntity> list = storeMenuService.getStoreMenuByStoreId(storeId);
        return list.stream()
            .map(storeMenuConverter::toResponse)
            .collect(Collectors.toList());
    }
    
}
