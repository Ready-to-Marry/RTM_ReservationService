package ready_to_marry.reservationservice.reservation.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractResponse {
    private Long partnerId;
    private Long itemId;
    private int amount;
    private String contractContent;
}
