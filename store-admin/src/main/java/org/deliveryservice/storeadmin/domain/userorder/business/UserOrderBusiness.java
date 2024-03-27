package org.deliveryservice.storeadmin.domain.userorder.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.deliveryservice.storeadmin.domain.sse.connection.SseConnectionPool;
import org.deliveryservice.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.deliveryservice.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.deliveryservice.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.deliveryservice.storeadmin.domain.storemenu.service.StoreMenuService;
import org.deliveryservice.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.deliveryservice.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import org.deliveryservice.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.deliveryservice.storeadmin.domain.userorder.service.UserOrderService;
import org.deliveryservice.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    /*
     * 주문 들어오면
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아서
     * push
     *
     * */
    public void pushUserOrder(
        UserOrderMessage userOrderMessage
    ) {
        UserOrderEntity userOrderEntity = userOrderService.getUserOrder(
            userOrderMessage.getUserOrderId()).orElseThrow(
            () -> new RuntimeException("사용자 주문내역 없음")
        );

        // user order menu
        List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenuList(
            userOrderEntity.getId());

        // user order menu -> store menu
        List<StoreMenuResponse> storeMenuResponseList = userOrderMenuList.stream()
            .map(userOrdermenuEntity -> {
                return storeMenuService.getStoreMenuWithThrow(userOrdermenuEntity.getStoreMenuId());
            })
            .map(storeMenuEntity -> {
                return storeMenuConverter.toResponse(storeMenuEntity);
            })
            .collect(Collectors.toList());

        UserOrderResponse userOrderResponse = userOrderConverter.toResponse(userOrderEntity);
        // response
        UserOrderDetailResponse push = UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderResponse)
            .storeMenuResponseList(storeMenuResponseList)
            .build();

        UserSseConnection userConnection = sseConnectionPool.getSession(
            userOrderEntity.getStoreId().toString());

        // push
        userConnection.sendMessage(push);

    }
}
