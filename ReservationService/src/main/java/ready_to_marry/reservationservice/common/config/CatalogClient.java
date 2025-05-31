package ready_to_marry.reservationservice.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ready_to_marry.reservationservice.common.dto.ApiResponse;
import ready_to_marry.reservationservice.common.exception.BusinessException;
import ready_to_marry.reservationservice.common.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class CatalogClient {

    private final WebClient.Builder webClientBuilder;

    public Long getPartnerIdByItemId(Long itemId) {
        ApiResponse<Long> response = webClientBuilder.build()
                .get()
                .uri("http://catalog-service/items/{itemId}/partner-id", itemId)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        if (response == null || response.getData() == null) {
            throw new BusinessException(ErrorCode.INTERNAL_REQUEST_FAIL);
        }

        return Long.valueOf(response.getData().toString());
    }
}
