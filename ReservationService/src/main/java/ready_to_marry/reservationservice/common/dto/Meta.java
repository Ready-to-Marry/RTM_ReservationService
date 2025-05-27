package ready_to_marry.reservationservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Meta {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
