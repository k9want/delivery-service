package org.delivery.api.domain.token.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

/**
 * token에 대한 도메인 로직 처리
 * */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token) {
        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);
        Object userId = map.get("userId");

        Objects.requireNonNull(userId, () -> {
            throw new ApiException(ErrorCode.NULL_POINT);
        });

        return Long.parseLong(userId.toString());
    }
}
