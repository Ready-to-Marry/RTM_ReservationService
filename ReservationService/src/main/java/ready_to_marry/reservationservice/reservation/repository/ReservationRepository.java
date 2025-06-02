package ready_to_marry.reservationservice.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ready_to_marry.reservationservice.reservation.entity.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByPartnerId(Long partnerId);
}