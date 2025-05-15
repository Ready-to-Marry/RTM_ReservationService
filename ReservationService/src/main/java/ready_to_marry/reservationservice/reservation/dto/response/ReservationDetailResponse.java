package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ready_to_marry.reservationservice.reservation.entity.Reservation;
import ready_to_marry.reservationservice.reservation.enums.ReservationStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationDetailResponse {
    private Long reservationId;
    private String title;
    private String message;
    private ReservationStatus status;
    private String answerMessage;
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    public static ReservationDetailResponse from(Reservation r) {
        return new ReservationDetailResponse(
                r.getReservationId(),
                r.getTitle(),
                r.getMessage(),
                r.getStatus(),
                r.getAnswerMessage(),
                r.getCreatedAt(),
                r.getAnsweredAt()
        );
    }
}