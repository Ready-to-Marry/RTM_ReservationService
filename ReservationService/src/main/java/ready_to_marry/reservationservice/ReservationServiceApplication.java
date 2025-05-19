package ready_to_marry.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ready_to_marry.reservationservice.reservation.entity")
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

}
