package kata

import java.time.LocalDateTime

class Delivery(
    val id: String,
    val contactEmail: String,
    val location: Location,
    var timeOfDelivery: LocalDateTime,
    var arrived: Boolean,
    var onTime: Boolean
)
