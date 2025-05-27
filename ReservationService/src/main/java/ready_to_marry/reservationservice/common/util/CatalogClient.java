package ready_to_marry.reservationservice.common.util;

import org.springframework.stereotype.Component;

@Component
public class CatalogClient {

    /**
     * TODO: 추후 WebClient 연동 예정
     * 현재는 itemId에 따라 partnerId를 하드코딩으로 반환 (테스트용)
     */
    public Long getPartnerIdByItemId(Long itemId) {
        // 임시 하드코딩 로직
        if (itemId == 1L) return 1001L;
        if (itemId == 2L) return 1002L;
        return 9999L; // unknown
    }
}
