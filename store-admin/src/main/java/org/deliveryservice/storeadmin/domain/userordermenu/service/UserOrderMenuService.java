package org.deliveryservice.storeadmin.domain.userordermenu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenuList(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId,
            UserOrderMenuStatus.REGISTERED);
    }

}
