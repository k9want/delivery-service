package org.deliveryservice.storeadmin.domain.storemenu.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.deliveryservice.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

@Service
public class StoreMenuConverter {

    public StoreMenuResponse toResponse(
        StoreMenuEntity storeMenuEntity
    ) {
        return StoreMenuResponse.builder()
            .id(storeMenuEntity.getId())
            .name(storeMenuEntity.getName())
            .status(storeMenuEntity.getStatus())
            .amount(storeMenuEntity.getAmount())
            .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
            .likeCount(storeMenuEntity.getLikeCount())
            .sequence(storeMenuEntity.getSequence())
            .build();
    }

    public List<StoreMenuResponse> toResponse(
        List<StoreMenuEntity> list
    ) {
        return list.stream()
            .map(it -> {
                return toResponse(it);
            }).collect(Collectors.toList());
    }
}
