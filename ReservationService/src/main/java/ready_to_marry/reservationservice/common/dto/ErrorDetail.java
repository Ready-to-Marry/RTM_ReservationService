package ready_to_marry.reservationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetail {
    private String field;
    private String message;
}
