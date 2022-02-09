package kata;

import java.time.LocalDateTime;

public record DeliveryEvent(long id, LocalDateTime timeOfDelivery, Location location) {

}
