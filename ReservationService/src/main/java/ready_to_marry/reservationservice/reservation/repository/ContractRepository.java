package ready_to_marry.reservationservice.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ready_to_marry.reservationservice.reservation.entity.Contract;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByUserId(Long userId);

    List<Contract> findByPartnerId(Long partnerId);

    Contract findByReservation_ReservationId(Long reservationId);

}
