package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeIfs {

    OK(200, 200, "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),

    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point 에러"),
    ;
    private final Integer httpStatusCode;

    private final Integer errorCode; // internal error code

    private final String description;

// @Getter로 퉁칠 수 있다. 일부러 인터페이스에서 getxxx로 한 이유다.
//    @Override
//    public Integer getHttpStatusCode() {
//        return this.httpStatusCode;
//    }
//
//    @Override
//    public Integer getErrorCode() {
//        return this.errorCode;
//    }
//
//    @Override
//    public String getDescription() {
//        return this.description;
//    }
}
