package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;

    private String resultMessage;

    private String resultDescription;

    public static Result OK() {
        return Result.builder()
            .resultCode(ErrorCode.OK.getErrorCode())
            .resultMessage(ErrorCode.OK.getDescription())
            .resultDescription("성공") // 추후 상세메시지로 사용
            .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
            .resultCode(errorCodeIfs.getErrorCode())
            .resultMessage(errorCodeIfs.getDescription())
            .resultDescription("에러 발생") // 추후 상세메시지로 사용
            .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        return Result.builder()
            .resultCode(errorCodeIfs.getErrorCode())
            .resultMessage(errorCodeIfs.getDescription())
            .resultDescription(
                tx.getLocalizedMessage()) // tx.getLocalizedMessage는 서버에 모든 stack trace가 내려가기에 비추 (일단은 넘어가자)
            .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        return Result.builder()
            .resultCode(errorCodeIfs.getErrorCode())
            .resultMessage(errorCodeIfs.getDescription())
            .resultDescription(description)
            .build();
    }
}
