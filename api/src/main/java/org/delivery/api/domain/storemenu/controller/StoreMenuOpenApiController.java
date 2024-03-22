package org.delivery.api.domain.storemenu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    @PostMapping("/register")
    public Api<StoreMenuResponse> register(
        @Valid
        @RequestBody Api<StoreMenuRegisterRequest> request
    ) {
        StoreMenuResponse response = storeMenuBusiness.register(request.getBody());
        return Api.OK(response);
    }

}
