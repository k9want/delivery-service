package org.deliveryservice.storeadmin.domain.userorder.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    public Optional<UserOrderEntity> getUserOrder(Long id) {
        return userOrderRepository.findById(id);
    }

}
