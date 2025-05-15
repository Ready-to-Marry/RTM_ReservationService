package ready_to_marry.reservationservice.reservation.dto.request;

import lombok.Getter;

// 1. USER - 예약 문의 등록 요청 DTO
@Getter
public class ReservationCreateRequest {
    private Long itemId;
    private String title;
    private String message;
}