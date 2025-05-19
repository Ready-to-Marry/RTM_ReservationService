package ready_to_marry.reservationservice.reservation.service;

import ready_to_marry.reservationservice.reservation.entity.Reservation;
import java.util.List;

public interface ReservationService {
    Long create(Reservation reservation);
    List<Reservation> getUserReservations(Long userId);
    List<Reservation> getPartnerReservations(Long partnerId);
    Reservation getById(Long reservationId);

    // 4-1. 권한 검증 포함 상세 조회
    Reservation getByIdWithAuth(Long reservationId, Long userId, Long partnerId);

    void cancel(Long reservationId, Long userId);
    void answer(Long reservationId, Long partnerId, String answer);
    void updateStatus(Long reservationId, Long partnerId, String status);
}