package ready_to_marry.reservationservice.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import ready_to_marry.reservationservice.reservation.enums.ReservationStatus;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private Long userId;
    private Long partnerId;
    private Long itemId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(columnDefinition = "TEXT")
    private String answerMessage;

    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ReservationStatus.PENDING;
        }
    }
}