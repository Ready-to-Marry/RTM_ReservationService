package ready_to_marry.reservationservice.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ready_to_marry.reservationservice.reservation.entity.Reservation;
import ready_to_marry.reservationservice.reservation.enums.ReservationStatus;
import ready_to_marry.reservationservice.reservation.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    // 1. USER -> 예약 문의 생성
    @Override
    public Long create(Reservation reservation) {
        return repository.save(reservation).getReservationId();
    }

    // 2. USER -> 사용자 예약 문의 목록 조회
    @Override
    public List<Reservation> getUserReservations(Long userId) {
        return repository.findByUserId(userId);
    }

    // 3. PARTNER -> 파트너가 받은 예약 문의 목록 조회
    @Override
    public List<Reservation> getPartnerReservations(Long partnerId) {
        return repository.findByPartnerId(partnerId);
    }

    // 4. ALL -> 예약 문의 상세 조회 (사용자/파트너 공통)
    @Override
    public Reservation getById(Long reservationId) {
        return repository.findById(reservationId).orElseThrow();
    }

    // 5. USER -> 예약 문의 취소
    @Override
    @Transactional
    public void cancel(Long reservationId, Long userId) {
        Reservation r = getById(reservationId);
        if (!r.getUserId().equals(userId)) throw new SecurityException("권한 없음");
        r.setStatus(ReservationStatus.CANCELLED);
    }

    // 6. PARTNER -> 예약 문의 응답
    @Override
    @Transactional
    public void answer(Long reservationId, Long partnerId, String answer) {
        Reservation r = getById(reservationId);
        if (!r.getPartnerId().equals(partnerId)) throw new SecurityException("권한 없음");
        r.setAnswerMessage(answer);
        r.setStatus(ReservationStatus.ANSWERED);
        r.setAnsweredAt(java.time.LocalDateTime.now());
    }

    // 7. PARTNER -> 예약 문의 상태 수동 변경
    @Override
    @Transactional
    public void updateStatus(Long reservationId, Long partnerId, String status) {
        Reservation r = getById(reservationId);
        if (!r.getPartnerId().equals(partnerId)) throw new SecurityException("권한 없음");
        r.setStatus(ReservationStatus.valueOf(status));
    }
}