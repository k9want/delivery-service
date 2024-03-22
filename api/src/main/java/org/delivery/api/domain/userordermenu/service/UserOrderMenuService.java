package org.delivery.api.domain.userordermenu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    private List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId) { // <<< userOrderId!!
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId,
            UserOrderMenuStatus.REGISTERED);
    }
}
