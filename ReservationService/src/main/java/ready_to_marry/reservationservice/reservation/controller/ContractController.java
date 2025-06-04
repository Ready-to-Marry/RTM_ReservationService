package ready_to_marry.reservationservice.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ready_to_marry.reservationservice.reservation.dto.request.ContractRequest;
import ready_to_marry.reservationservice.reservation.dto.response.ContractListResponse;
import ready_to_marry.reservationservice.reservation.service.ContractService;

import java.util.List;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // 1. Partner -> 계약 생성 (결제 요청)
    @PostMapping("/request")
    public ContractListResponse requestContract(@RequestBody ContractRequest request,
                                                @RequestHeader("X-Partner-Id") Long partnerId) {
        System.out.println("호출 완료");
        System.out.println("partnerId: " + partnerId);
        System.out.println(request.getReservationId());
        System.out.println(request.getAmount());
        return contractService.createContract(request, partnerId);
    }

    // 2. User -> 사용자 계약 목록 조회
    @GetMapping("/user")
    public List<ContractListResponse> getContractsForUser(@RequestHeader("X-User-Id") Long userId) {
        return contractService.getContractsForUser(userId);
    }

    // 3. Partner -> 파트너 계약 목록 조회
    @GetMapping("/partner")
    public List<ContractListResponse> getContractsForPartner(@RequestHeader("X-Partner-Id") Long partnerId) {
        return contractService.getContractsForPartner(partnerId);
    }

    // 4. Partner -> 결제 완료 처리 (Payment)
    @PatchMapping("/{id}/complete")
    public void markAsCompleted(@PathVariable Long id) {
        contractService.markAsCompleted(id);
    }

    // 5. Partner -> 환불 처리 (Payment)
    @PatchMapping("/{id}/refund")
    public void markAsRefunded(@PathVariable Long id) {
        contractService.markAsRefunded(id);
    }

    // 6. User -> 사용자에 의한 계약 취소
    @PatchMapping("/{id}/cancel")
    public void cancelByUser(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        contractService.cancelByUser(id, userId);
    }

    // 7. Partner -> 파트너에 의한 계약 취소
    @PatchMapping("/{id}/partner-cancel")
    public void cancelByPartner(@PathVariable Long id, @RequestHeader("X-Partner-Id") Long partnerId) {
        contractService.cancelByPartner(id, partnerId);
    }
}
