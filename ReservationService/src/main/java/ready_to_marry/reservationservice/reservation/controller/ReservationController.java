package ready_to_marry.reservationservice.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ready_to_marry.reservationservice.common.dto.*;
import ready_to_marry.reservationservice.common.util.CatalogClient;
import ready_to_marry.reservationservice.reservation.dto.ReservationAnswerRequest;
import ready_to_marry.reservationservice.reservation.dto.ReservationCreateRequest;
import ready_to_marry.reservationservice.reservation.dto.ReservationStatusUpdateRequest;
import ready_to_marry.reservationservice.reservation.entity.Reservation;
import ready_to_marry.reservationservice.reservation.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;
    private final CatalogClient catalogClient;

    // 1. USER - 예약 문의 등록
    @PostMapping
    public ApiResponse<Long> create(@RequestBody ReservationCreateRequest request,
                                    @RequestHeader("X-USER-ID") Long userId) {

        Long partnerId = catalogClient.getPartnerIdByItemId(request.getItemId());

        Reservation r = Reservation.builder()
                .userId(userId)
                .itemId(request.getItemId())
                .partnerId(partnerId)
                .title(request.getTitle())
                .message(request.getMessage())
                .build();

        return ApiResponse.success(service.create(r));
    }

    // 2. USER - 내 문의 목록 조회
    @GetMapping("/user")
    public ApiResponse<List<Reservation>> getMyReservations(@RequestHeader("X-USER-ID") Long userId) {
        return ApiResponse.success(service.getUserReservations(userId));
    }

    // 3. PARTNER - 받은 문의 목록 조회
    @GetMapping("/partner")
    public ApiResponse<List<Reservation>> getPartnerReservations(@RequestHeader("X-PARTNER-ID") Long partnerId) {
        return ApiResponse.success(service.getPartnerReservations(partnerId));
    }

    // 4. ALL - 단건 조회
    @GetMapping("/{reservationId}")
    public ApiResponse<Reservation> getById(@PathVariable Long reservationId) {
        return ApiResponse.success(service.getById(reservationId));
    }

    // 5. USER - 예약 취소
    @DeleteMapping("/{reservationId}")
    public ApiResponse<Void> cancel(@PathVariable Long reservationId,
                                    @RequestHeader("X-USER-ID") Long userId) {
        service.cancel(reservationId, userId);
        return ApiResponse.success(null);
    }

    // 6. PARTNER - 예약 응답
    @PatchMapping("/{reservationId}/answer")
    public ApiResponse<Void> answer(@PathVariable Long reservationId,
                                    @RequestHeader("X-PARTNER-ID") Long partnerId,
                                    @RequestBody ReservationAnswerRequest request) {
        service.answer(reservationId, partnerId, request.getAnswerMessage());
        return ApiResponse.success(null);
    }

}