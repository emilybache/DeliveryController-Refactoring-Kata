package kata

import java.time.Duration

class DeliveryController(val deliverySchedule: List<Delivery>) {

    private val emailGateway = EmailGateway()
    private val mapService = MapService()

    fun updateDelivery(deliveryEvent: DeliveryEvent) {
        var nextDelivery: Delivery? = null
        for ((i, delivery) in deliverySchedule.withIndex()) {
            if (deliveryEvent.id == delivery.id) {
                delivery.arrived = true
                val timeDifference = Duration.between(delivery.timeOfDelivery, deliveryEvent.timeOfDelivery)
                if (timeDifference < Duration.ofMinutes(10)) {
                    delivery.onTime = true
                }
                delivery.timeOfDelivery = deliveryEvent.timeOfDelivery
                val message =
                    "Regarding your delivery today at ${delivery.timeOfDelivery}. How likely would you be to recommend this delivery service to a friend? Click <a href='url'>here</a>"
                emailGateway.send(delivery.contactEmail, "Your feedback is important to us", message)
                if (deliverySchedule.size > i + 1) {
                    nextDelivery = deliverySchedule[i + 1]
                }

                if (!delivery.onTime && deliverySchedule.size > 1 && i > 0) {
                    val previousDelivery = deliverySchedule[i - 1]
                    val elapsedTime = Duration.between(previousDelivery.timeOfDelivery, delivery.timeOfDelivery)
                    mapService.updateAverageSpeed(previousDelivery.location, delivery.location, elapsedTime)
                }
            }
        }

        if (nextDelivery != null) {
            val nextEta = mapService.calculateETA(deliveryEvent.location, nextDelivery.location)
            val message =
                "Your delivery to ${nextDelivery.location} is next, estimated time of arrival is in ${nextEta} minutes. Be ready!"
            emailGateway.send(nextDelivery.contactEmail, "Your delivery will arrive soon", message)

        }
    }
}
