package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ready_to_marry.reservationservice.reservation.entity.Reservation;
import ready_to_marry.reservationservice.reservation.enums.ReservationStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationDTO{
    private Long reservationId;
    private String title;
    private ReservationStatus status;
    private LocalDateTime createdAt;

    public static ReservationDTO from(Reservation r) {
        return new ReservationDTO(
                r.getReservationId(),
                r.getTitle(),
                r.getStatus(),
                r.getCreatedAt()
        );
    }
}
