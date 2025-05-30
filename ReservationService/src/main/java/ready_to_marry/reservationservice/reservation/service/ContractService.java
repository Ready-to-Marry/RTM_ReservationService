package ready_to_marry.reservationservice.reservation.service;

import ready_to_marry.reservationservice.reservation.dto.request.ContractRequest;
import ready_to_marry.reservationservice.reservation.dto.response.ContractResponse;

import java.util.List;

public interface ContractService {
    ContractResponse createContract(ContractRequest request, Long partnerId);
    void markAsCompleted(Long contractId);
    void markAsRefunded(Long contractId);
    void cancelByUser(Long contractId, Long userId);
    void cancelByPartner(Long contractId, Long partnerId);
    ContractResponse getContractDetail(Long reservationId, Long partnerId);
    List<ContractResponse> getContractsForUser(Long userId);
    List<ContractResponse> getContractsForPartner(Long partnerId);
    ContractResponse getContractByIdForUser(Long contractId, Long userId);
}