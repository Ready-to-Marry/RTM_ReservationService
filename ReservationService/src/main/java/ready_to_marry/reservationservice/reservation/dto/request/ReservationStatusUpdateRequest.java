package ready_to_marry.reservationservice.reservation.dto.request;

import lombok.Getter;

// 3. PARTNER - 상태 수동 변경 요청 DTO

@Getter
public class ReservationStatusUpdateRequest {
    private String status;
}