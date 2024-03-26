package org.deliveryservice.storeadmin.domain.user.business;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.deliveryservice.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.deliveryservice.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.deliveryservice.storeadmin.domain.user.converter.StoreUserConverter;
import org.deliveryservice.storeadmin.domain.user.service.StoreUserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreUserBusiness {

    private final StoreUserService storeUserService;
    private final StoreUserConverter storeUserConverter;
    private final StoreRepository storeRepository; // todo service 로 변경하기

    public StoreUserResponse register(
        StoreUserRegisterRequest request
    ) {
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(
            request.getStoreName(),
            StoreStatus.REGISTERED);

        StoreUserEntity entity = storeUserConverter.toEntity(request, storeEntity.get());
        StoreUserEntity newEntity = storeUserService.register(entity);

        StoreUserResponse response = storeUserConverter.toResponse(newEntity, storeEntity.get());
        return response;

    }
}
