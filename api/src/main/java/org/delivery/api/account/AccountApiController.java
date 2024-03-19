package org.delivery.api.account;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<AccountMeResponse> me() {
        AccountMeResponse response = AccountMeResponse.builder()
            .name("홍길동")
            .email("a@gmail.com")
            .registeredAt(LocalDateTime.now())
            .build();

        return Api.OK(response);
    }
    
    @GetMapping("/me2")
    public Api<Object> m2e() {
        AccountMeResponse response = AccountMeResponse.builder()
            .name("홍길동")
            .email("a@gmail.com")
            .registeredAt(LocalDateTime.now())
            .build();

        return Api.ERROR(UserErrorCode.USER_NOT_FOUND, response.getName() + "이라는 사용자는 없음");
    }


}
