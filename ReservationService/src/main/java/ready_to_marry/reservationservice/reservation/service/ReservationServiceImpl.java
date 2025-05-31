package ready_to_marry.reservationservice.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ready_to_marry.reservationservice.common.config.CatalogClient;
import ready_to_marry.reservationservice.common.exception.BusinessException;
import ready_to_marry.reservationservice.common.exception.ErrorCode;
import ready_to_marry.reservationservice.common.exception.InfrastructureException;
import ready_to_marry.reservationservice.reservation.entity.Reservation;
import ready_to_marry.reservationservice.reservation.enums.ReservationStatus;
import ready_to_marry.reservationservice.reservation.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final CatalogClient catalogClient;

    // 1. USER -> 예약 문의 생성
    @Override
    public Long create(Reservation reservation) {
        try {
            return repository.save(reservation).getReservationId();
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_WRITE_FAILURE, e);
        }
    }

    // 2. USER -> 사용자 예약 문의 목록 조회
    @Override
    public List<Reservation> getUserReservations(Long userId) {
        try {
            return repository.findByUserId(userId);
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_READ_FAILURE, e);
        }
    }

    // 3. PARTNER -> 파트너가 받은 예약 문의 목록 조회
    @Override
    public List<Reservation> getPartnerReservations(Long partnerId) {
        try {
            return repository.findByPartnerId(partnerId);
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_READ_FAILURE, e);
        }
    }

    // 4. ALL -> 예약 문의 상세 조회 (사용자/파트너 공통)
    @Override
    public Reservation getById(Long reservationId) {
        try {
            return repository.findById(reservationId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RESERVATION));
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_READ_FAILURE, e);
        }
    }

    // 4-1. 권한 검증 포함 상세 조회
    @Override
    public Reservation getByIdWithAuth(Long reservationId, Long userId, Long partnerId) {
        Reservation r = getById(reservationId);

        boolean isUser = userId != null && userId.equals(r.getUserId());
        boolean isPartner = partnerId != null && partnerId.equals(r.getPartnerId());

        if (!isUser && !isPartner) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        return r;
    }

    // 5. USER -> 예약 문의 취소
    @Override
    @Transactional
    public void cancel(Long reservationId, Long userId) {
        Reservation r = getById(reservationId);
        if (!r.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        try {
            r.setStatus(ReservationStatus.CANCELLED);
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_WRITE_FAILURE, e);
        }
    }

    // 6. PARTNER -> 예약 문의 응답
    @Override
    @Transactional
    public void answer(Long reservationId, Long partnerId, String answer) {
        Reservation r = getById(reservationId);
        if (!r.getPartnerId().equals(partnerId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        try {
            r.setAnswerMessage(answer);
            r.setStatus(ReservationStatus.ANSWERED);
            r.setAnsweredAt(LocalDateTime.now());
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_WRITE_FAILURE, e);
        }
    }

    // 7. PARTNER -> 예약 문의 상태 수동 변경
    @Override
    @Transactional
    public void updateStatus(Long reservationId, Long partnerId, String status) {
        Reservation r = getById(reservationId);
        if (!r.getPartnerId().equals(partnerId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        try {
            r.setStatus(ReservationStatus.valueOf(status));
        } catch (Exception e) {
            throw new InfrastructureException(ErrorCode.DB_WRITE_FAILURE, e);
        }
    }
}
