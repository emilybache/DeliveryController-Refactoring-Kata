package kata

import java.time.LocalDateTime

class DeliveryEvent(
    val id: String,
    val timeOfDelivery: LocalDateTime,
    val location: Location
)