package ready_to_marry.reservationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ErrorDetail {
    private String field;
    private String message;
}
