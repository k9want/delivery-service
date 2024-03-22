package org.delivery.api.domain.userorder.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;

    private final UserOrderConverter userOrderConverter;

    private final StoreMenuService storeMenuService;

    private final UserOrderMenuConverter userOrderMenuConverter;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreService storeService;

    private final StoreMenuConverter storeMenuConverter;

    private final StoreConverter storeConverter;

    // 1. 사용자, 메뉴 id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성 (매핑테이블)
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        // 메뉴가 유효한 메뉴인지 우선 체크
        List<StoreMenuEntity> storeMenuEntityList = body.getStoreMenuIdList()
            .stream()
            .map(it -> storeMenuService.getStoreMenuWithThrow(it))
            .toList();

        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        // 주문
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 매핑
        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
            .map(it -> {
                // menu +user = order
                UserOrderMenuEntity userOrderMenuEntity = userOrderMenuConverter.toEntity(
                    newUserOrderEntity,
                    it);
                return userOrderMenuEntity;
            }).collect(Collectors.toList());

        // 주문내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        // 응답 생성
        return userOrderConverter.toResponse(userOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        // 현재 사용자가 주문한 내역(리스트)를 가져와야한다.
        List<UserOrderEntity> userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리
        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            // 사용자가 주문한 메뉴
            List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            // 어떤 메뉴를 주문했는지
            List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(
                        userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                }).toList();

            // 사용자가 주문한 스토어 정보
            // todo 리팩토링 필요 : null point exception 가능성 있음
            StoreEntity storeEntity = storeService.getStoreWithThrow(
                storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(it))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();

        }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        // 현재 사용자가 주문한 내역(리스트)를 가져와야한다.
        List<UserOrderEntity> userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리
        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            // 사용자가 주문한 메뉴
            List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            // 어떤 메뉴를 주문했는지
            List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(
                        userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                }).toList();

            // 사용자가 주문한 스토어 정보
            // todo 리팩토링 필요 : null point exception 가능성 있음
            StoreEntity storeEntity = storeService.getStoreWithThrow(
                storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(it))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();

        }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId,
            user.getId());

        // 사용자가 주문한 메뉴
        List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        // 어떤 메뉴를 주문했는지
        List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
            .map(userOrderMenuEntity -> {
                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(
                    userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            }).toList();


        // 사용자가 주문한 스토어 정보
        // todo 리팩토링 필요 : null point exception 가능성 있음
        StoreEntity storeEntity = storeService.getStoreWithThrow(
            storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
            .storeResponse(storeConverter.toResponse(storeEntity))
            .build();
    }
}
