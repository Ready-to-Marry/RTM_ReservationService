package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ready_to_marry.reservationservice.reservation.entity.Contract;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractPartnerListResponse {
    private Long contractId;
    private Long userId;
    private int amount;
    private String status;
    private String contractContent;
    private LocalDateTime createdAt;

    public static ContractPartnerListResponse from(Contract contract) {
        return ContractPartnerListResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUserId())
                .amount(contract.getAmount())
                .status(contract.getStatus().name())
                .contractContent(contract.getContractContent())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}