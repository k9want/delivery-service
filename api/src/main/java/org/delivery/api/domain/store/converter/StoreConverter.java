package org.delivery.api.domain.store.converter;

import java.util.Optional;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

@Converter
public class StoreConverter {

    public StoreEntity toEntity(
        StoreRegisterRequest request
    ) {
        return Optional.ofNullable(request)
            .map(it -> {
                return StoreEntity.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .category(request.getCategory())
                    .minimumAmount(request.getMinimumDeliveryAmount())
                    .minimumDeliveryAmount(request.getMinimumDeliveryAmount())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            })
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

    }

    public StoreResponse toResponse(
        StoreEntity entity
    ) {
        // null 값이 넘어올 수도 있다.
        return Optional.ofNullable(entity)
            .map(it -> {
                return StoreResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .status(entity.getStatus())
                    .category(entity.getCategory())
                    .address(entity.getAddress())
                    .minimumAccount(entity.getMinimumAmount())
                    .minimumDeliveryAmount(entity.getMinimumDeliveryAmount())
                    .thumbnailUrl(entity.getThumbnailUrl())
                    .phoneNumber(entity.getPhoneNumber())
                    .star(entity.getStar())
                    .build();
            })
            .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


}
