package ready_to_marry.reservationservice.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ready_to_marry.reservationservice.reservation.dto.request.ContractRequest;
import ready_to_marry.reservationservice.reservation.dto.response.ContractResponse;
import ready_to_marry.reservationservice.reservation.service.ContractService;

import java.util.List;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // 1. 계약 생성 (결제 요청)
    @PostMapping("/request")
    public ContractResponse requestContract(@RequestBody ContractRequest request,
                                            @RequestHeader("X-PARTNER-ID") Long partnerId) {
        return contractService.createContract(request, partnerId);
    }

    // 2. 계약 상세 조회 (파트너가 자신의 상담 건에 대해 입력한 계약 확인)
    @GetMapping("/reservation/{reservationId}")
    public ContractResponse getContractDetail(@PathVariable Long reservationId,
                                              @RequestHeader("X-PARTNER-ID") Long partnerId) {
        return contractService.getContractDetail(reservationId, partnerId);
    }

    // 3. 사용자 계약 목록 조회
    @GetMapping("/user")
    public List<ContractResponse> getContractsForUser(@RequestHeader("X-USER-ID") Long userId) {
        return contractService.getContractsForUser(userId);
    }

    // 3-1. 사용자 계약 단건 상세 조회
    @GetMapping("/user/{contractId}")
    public ContractResponse getContractByIdForUser(@PathVariable Long contractId,
                                                   @RequestHeader("X-USER-ID") Long userId) {
        return contractService.getContractByIdForUser(contractId, userId);
    }

    // 4. 파트너 계약 목록 조회
    @GetMapping("/partner")
    public List<ContractResponse> getContractsForPartner(@RequestHeader("X-PARTNER-ID") Long partnerId) {
        return contractService.getContractsForPartner(partnerId);
    }

    // 5. 결제 완료 처리 (내부 시스템 호출용)
    @PatchMapping("/{id}/complete")
    public void markAsCompleted(@PathVariable Long id) {
        contractService.markAsCompleted(id);
    }

    // 6. 환불 처리 (내부 시스템 호출용)
    @PatchMapping("/{id}/refund")
    public void markAsRefunded(@PathVariable Long id) {
        contractService.markAsRefunded(id);
    }

    // 7. 사용자에 의한 계약 취소
    @PatchMapping("/{id}/cancel")
    public void cancelByUser(@PathVariable Long id, @RequestHeader("X-USER-ID") Long userId) {
        contractService.cancelByUser(id, userId);
    }

    // 8. 파트너에 의한 계약 취소
    @PatchMapping("/{id}/partner-cancel")
    public void cancelByPartner(@PathVariable Long id, @RequestHeader("X-PARTNER-ID") Long partnerId) {
        contractService.cancelByPartner(id, partnerId);
    }
}