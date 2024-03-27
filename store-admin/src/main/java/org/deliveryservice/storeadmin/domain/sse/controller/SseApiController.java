package org.deliveryservice.storeadmin.domain.sse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deliveryservice.storeadmin.domain.authorization.model.UserSession;
import org.deliveryservice.storeadmin.domain.sse.connection.SseConnectionPool;
import org.deliveryservice.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {


    private final SseConnectionPool sseConnectionPool;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
        @Parameter(hidden = true)
        @AuthenticationPrincipal
        UserSession userSession
    ) {
        log.info("login user = {}", userSession);

        UserSseConnection userSseConnection = UserSseConnection.connect(
            userSession.getStoreId().toString(),
            sseConnectionPool,
            objectMapper
            
        );

        sseConnectionPool.addSession(userSseConnection.getUniqueKey(), userSseConnection);

        return userSseConnection.getSseEmitter();
    }

    @GetMapping("/push-event")
    public void pushEvent(
        @Parameter(hidden = true)
        @AuthenticationPrincipal
        UserSession userSession
    ) {
        UserSseConnection userSseConnection = sseConnectionPool.getSession(
            userSession.getStoreId().toString());

        Optional.ofNullable(userSseConnection)
            .ifPresent(it -> {
                it.sendMessage("hello world");
            });
    }

}
