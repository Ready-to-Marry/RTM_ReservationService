package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import ready_to_marry.reservationservice.reservation.entity.Contract;
import ready_to_marry.reservationservice.reservation.enums.ContractStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractResponse {
    private Long contractId;
    private Long userId;
    private Long partnerId;
    private Long itemId;
    private int amount;
    private String contractContent;
    private ContractStatus status;
    private LocalDateTime createdAt;

    public static ContractResponse from(Contract contract) {
        return ContractResponse.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUserId())
                .partnerId(contract.getPartnerId())
                .itemId(contract.getItemId())
                .amount(contract.getAmount())
                .contractContent(contract.getContractContent())
                .status(contract.getStatus())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}
