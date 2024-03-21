package org.delivery.api.domain.store.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;

@RequiredArgsConstructor
@Business
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(
        StoreRegisterRequest request
    ) {
        // req -> entity -> response
        StoreEntity entity = storeConverter.toEntity(request);
        StoreEntity newEntity = storeService.register(entity);
        StoreResponse response = storeConverter.toResponse(newEntity);
        return response;
    }

    public List<StoreResponse> searchCategory(
        StoreCategory storeCategory
    ) {
        // entity list -> response list
        List<StoreEntity> storeList = storeService.searchByCategory(storeCategory);

        return storeList.stream()
            .map(storeConverter::toResponse)
            .collect(Collectors.toList());
    }
}
