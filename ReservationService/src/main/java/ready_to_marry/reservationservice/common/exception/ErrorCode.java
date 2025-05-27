package ready_to_marry.reservationservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 17xx: 비즈니스 오류
    UNAUTHORIZED_ACCESS(1700, "You do not have permission to perform this action"),
    // 27xx: 인프라(시스템) 오류
    DB_WRITE_FAILURE(2700, "Failed to write data to the database"),
    DB_READ_FAILURE(2701, "Failed to read data from the database");

    private final int code;
    private final String message;
}