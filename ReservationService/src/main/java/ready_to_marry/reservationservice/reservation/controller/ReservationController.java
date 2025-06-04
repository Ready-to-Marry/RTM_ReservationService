package ready_to_marry.reservationservice.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ready_to_marry.reservationservice.common.config.CatalogClient;
import ready_to_marry.reservationservice.common.dto.*;
import ready_to_marry.reservationservice.reservation.dto.request.ReservationAnswerRequest;
import ready_to_marry.reservationservice.reservation.dto.request.ReservationCreateRequest;
import ready_to_marry.reservationservice.reservation.dto.response.ReservationDTO;
import ready_to_marry.reservationservice.reservation.dto.response.ReservationDetailResponse;
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
                                    @RequestHeader("X-User-Id") Long userId) {

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
    public ApiResponse<List<ReservationDTO>> getMyReservations(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Reservation> list = service.getUserReservations(userId);
        List<ReservationDTO> summaryList = list.stream()
                .map(ReservationDTO::from)
                .toList();

        Meta meta = new Meta(page, size, summaryList.size(), (int) Math.ceil((double) summaryList.size() / size));
        return ApiResponse.success(summaryList, meta);
    }

    // 3. PARTNER - 받은 문의 목록 조회
    @GetMapping("/partner")
    public ApiResponse<List<ReservationDTO>> getPartnerReservations(
            @RequestHeader("X-Partner-Id") Long partnerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Reservation> list = service.getPartnerReservations(partnerId);
        List<ReservationDTO> summaryList = list.stream()
                .map(ReservationDTO::from)
                .toList();

        Meta meta = new Meta(page, size, summaryList.size(), (int) Math.ceil((double) summaryList.size() / size));
        return ApiResponse.success(summaryList, meta);
    }

    // 4. ALL - 단건 조회
    @GetMapping("/{reservationId}")
    public ApiResponse<ReservationDetailResponse> getById(@PathVariable Long reservationId,
                                                          @RequestHeader(value = "X-USER-ID", required = false) Long userId,
                                                          @RequestHeader(value = "X-PARTNER-ID", required = false) Long partnerId) {
        Reservation entity = service.getByIdWithAuth(reservationId, userId, partnerId);
        return ApiResponse.success(ReservationDetailResponse.from(entity));
    }

    // 5. USER - 예약 취소
    @DeleteMapping("/{reservationId}")
    public ApiResponse<Void> cancel(@PathVariable Long reservationId,
                                    @RequestHeader("X-User-Id") Long userId) {
        service.cancel(reservationId, userId);
        return ApiResponse.success(null);
    }

    // 6. PARTNER - 예약 응답
    @PatchMapping("/{reservationId}/answer")
    public ApiResponse<Void> answer(@PathVariable Long reservationId,
                                    @RequestHeader("X-Partner-Id") Long partnerId,
                                    @RequestBody ReservationAnswerRequest request) {
        service.answer(reservationId, partnerId, request.getAnswerMessage());
        return ApiResponse.success(null);
    }

}