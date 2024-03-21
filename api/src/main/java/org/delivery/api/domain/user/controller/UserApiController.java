package org.delivery.api.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
        @UserSession User user
    ) {

/*        RequestAttributes requestContext = Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes());
        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        UserResponse response = userBusiness.me(Long.parseLong(userId.toString()));*/
        UserResponse response = userBusiness.me(user.getId());
        return Api.OK(response);
    }


}
