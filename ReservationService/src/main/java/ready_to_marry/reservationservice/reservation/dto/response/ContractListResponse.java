package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ready_to_marry.reservationservice.reservation.entity.Contract;
import ready_to_marry.reservationservice.reservation.enums.ContractStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ContractListResponse {

    private Long contractId;
    private Long userId;
    private Long partnerId;
    private Long itemId;
    private int amount;
    private String contractContent;
    private ContractStatus status;
    private LocalDateTime createdAt;

    public static ContractListResponse from(Contract contract) {
        return ContractListResponse.builder()
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
