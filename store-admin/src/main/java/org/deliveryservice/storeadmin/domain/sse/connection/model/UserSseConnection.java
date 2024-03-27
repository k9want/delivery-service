package org.deliveryservice.storeadmin.domain.sse.connection.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.deliveryservice.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;

    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    // 기본생성자로 객체 생성을 막기 위해 따로 기본 생성자는 만들지 않는다.
    private UserSseConnection(
        String uniqueKey,
        ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
        ObjectMapper objectMapper
    ) {
        // key 초기화
        this.uniqueKey = uniqueKey;

        // sse 초기화
        this.sseEmitter = new SseEmitter(60 * 1000L);

        // call back 초기화
        this.connectionPoolIfs = connectionPoolIfs;

        // object Mapper 초기화
        this.objectMapper = objectMapper;

        // on completion
        this.sseEmitter.onCompletion(() -> {
            // connection pool remove
            connectionPoolIfs.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        // onopen 메시지
        this.sendMessage("onopen", "connect");
    }

    // 기본생성자로는 해당 메소드가 connect 되는 걸 명시적으로 알기 힘들기에 connect라는 static 메소드를 만들고 기본생성자를 부르도록 하여
    // 좀 더 명시적으로 메소드가 connect되는걸 알 수 잇게끔 해주었다.
    public static UserSseConnection connect(
        String uniqueKey,
        ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
        ObjectMapper objectMapper
    ) {
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }

    public void sendMessage(String eventName, Object data) {
        try {
            String json = this.objectMapper.writeValueAsString(data);
            SseEventBuilder event = SseEmitter.event()
                .name(eventName)
                .data(json);

            sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            String json = this.objectMapper.writeValueAsString(data);
            SseEventBuilder event = SseEmitter.event()
                .data(data);

            sseEmitter.send(json);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
