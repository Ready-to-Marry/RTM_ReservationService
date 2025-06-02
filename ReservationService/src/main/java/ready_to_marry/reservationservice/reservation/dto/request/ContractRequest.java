package ready_to_marry.reservationservice.reservation.dto.request;

import lombok.Getter;

@Getter
public class ContractRequest {
    private Long reservationId;
    private int amount;
    private String contractContent;
}
