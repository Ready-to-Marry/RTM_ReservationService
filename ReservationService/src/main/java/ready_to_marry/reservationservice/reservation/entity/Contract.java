package ready_to_marry.reservationservice.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import ready_to_marry.reservationservice.reservation.enums.ContractStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    private Long userId;
    private Long partnerId;
    private Long itemId;

    private int amount;
    private String contractContent;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Builder
    public Contract(Long userId, Long partnerId, Long itemId, int amount, String contractContent, ContractStatus status, Reservation reservation) {
        this.userId = userId;
        this.partnerId = partnerId;
        this.itemId = itemId;
        this.amount = amount;
        this.contractContent = contractContent;
        this.status = status;
        this.reservation = reservation;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
}
