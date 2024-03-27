package org.deliveryservice.storeadmin.domain.authorization;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.deliveryservice.storeadmin.domain.authorization.model.UserSession;
import org.deliveryservice.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<StoreUserEntity> storeUserEntity = storeUserService.getRegisterUser(username);
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
            storeUserEntity.get().getStoreId(),
            StoreStatus.REGISTERED);

        return storeUserEntity.map(it -> {
                UserSession userSession = UserSession.builder()
                    .userId(it.getId())
                    .email(it.getEmail())
                    .password(it.getPassword())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .registeredAt(it.getRegisteredAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .lastLoginAt(it.getLastLoginAt())

                    .storeId(storeEntity.get().getId())
                    .storeName(storeEntity.get().getName())
                    .build();
                return userSession;
        })
            .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
