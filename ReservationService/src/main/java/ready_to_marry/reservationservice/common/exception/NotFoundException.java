package ready_to_marry.reservationservice.common.exception;

import lombok.Getter;

/**
 * 리소스를 찾지 못했을 때 사용하는 예외
 */
@Getter
public class NotFoundException extends RuntimeException {
    private final int code;
    private final ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.errorCode = errorCode;
    }
}