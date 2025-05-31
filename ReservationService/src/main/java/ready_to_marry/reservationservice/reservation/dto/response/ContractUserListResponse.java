package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ready_to_marry.reservationservice.reservation.entity.Contract;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractUserListResponse {
    private Long contractId;
    private int amount;
    private String status;
    private String contractContent;
    private LocalDateTime createdAt;

    public static ContractUserListResponse from(Contract contract) {
        return ContractUserListResponse.builder()
                .contractId(contract.getContractId())
                .amount(contract.getAmount())
                .status(contract.getStatus().name())
                .contractContent(contract.getContractContent())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}

