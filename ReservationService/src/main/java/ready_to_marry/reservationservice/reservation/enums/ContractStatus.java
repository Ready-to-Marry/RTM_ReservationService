package ready_to_marry.reservationservice.reservation.enums;

public enum ContractStatus {
    REQUESTED,             // 결제 요청됨
    COMPLETED,             // 결제 완료됨
    CANCELLED_BY_USER,     // 사용자가 계약 취소함
    CANCELLED_BY_PARTNER,  // 파트너가 계약 취소함
    REFUNDED               // 결제 환불됨
}