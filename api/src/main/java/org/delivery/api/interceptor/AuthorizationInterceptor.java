package org.delivery.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB, chrome 의 경우 GET, POST 전에 options = pass 이라는 API를 통해 해당 메서드를 지원하는 체크하는 API가 있다.
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // js, html, png, resource를 요청하는 경우 pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // todo: 헤더 검증

        return true;
    }
}
