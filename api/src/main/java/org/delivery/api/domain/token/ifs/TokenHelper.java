package org.delivery.api.domain.token.ifs;

import java.util.Map;
import org.delivery.api.domain.token.model.TokenDto;

public interface TokenHelper {

    TokenDto issueAccessToken(Map<String, Object> data);

    TokenDto issueRefreshToken(Map<String, Object> data);

    Map<String, Object> validationTokenWithThrow(String token);

}
