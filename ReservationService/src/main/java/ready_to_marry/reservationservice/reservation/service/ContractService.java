package ready_to_marry.reservationservice.reservation.service;

import ready_to_marry.reservationservice.reservation.dto.request.ContractRequest;
import ready_to_marry.reservationservice.reservation.dto.response.ContractListResponse;

import java.util.List;

public interface ContractService {
    ContractListResponse createContract(ContractRequest request, Long partnerId);
    void markAsCompleted(Long contractId);
    void markAsRefunded(Long contractId);
    void cancelByUser(Long contractId, Long userId);
    void cancelByPartner(Long contractId, Long partnerId);
    ContractListResponse getContractDetail(Long reservationId, Long partnerId);
    List<ContractListResponse> getContractsForUser(Long userId);
    List<ContractListResponse> getContractsForPartner(Long partnerId);
}
