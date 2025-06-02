package ready_to_marry.reservationservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 17xx: 비즈니스 오류
    NOT_FOUND_RESERVATION(1701, "Reservation not found"),
    NOT_FOUND_CONTRACT(1702, "Contract not found"),
    RESERVATION_STATUS_NOT_ANSWERED(1703, "Reservation status must be ANSWERED"),
    UNAUTHORIZED_RESERVATION_ACCESS(1704, "You do not have permission to access this reservation"),
    UNAUTHORIZED_CONTRACT_ACCESS(1705, "You do not have permission to access this contract"),
    INVALID_CONTRACT_STATE(1706, "This operation is only allowed in a specific contract state"),
    UNAUTHORIZED_ACCESS(1707, "You do not have permission to perform this action"),

    // 27xx: Infrastructure (system) errors
    DB_WRITE_FAILURE(2700, "Failed to write data to the database"),
    DB_READ_FAILURE(2701, "Failed to read data from the database"),
    INTERNAL_REQUEST_FAIL(2702, "Internal service request failed");

    private final int code;
    private final String message;
}
