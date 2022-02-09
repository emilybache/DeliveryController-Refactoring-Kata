package kata;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DeliveryController {

  private final EmailGateway _emailGateway;
  private final MapService _mapService;
  public List<Delivery> deliverySchedule;

  public DeliveryController(List<Delivery> deliverySchedule) {
    this.deliverySchedule = deliverySchedule;
    this._emailGateway = new EmailGateway();
    this._mapService = new MapService();
  }

  public void updateDelivery(DeliveryEvent deliveryEvent) {
    Delivery nextDelivery = null;
    for (int i = 0; i < deliverySchedule.size(); i++) {
      Delivery delivery = deliverySchedule.get(i);
      if (deliveryEvent.id() == delivery.id()) {
        delivery.setArrived(true);
        Duration d = Duration.between(delivery.timeOfDelivery(), deliveryEvent.timeOfDelivery());

        if (d.toMinutes() < 10) {
          delivery.setOnTime(true);
        }
        delivery.setTimeOfDelivery(deliveryEvent.timeOfDelivery());
        String message = "Regarding your delivery today at %s. How likely would you be to recommend this delivery service to a friend? Click <a href='url'>here</a>".formatted(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(delivery.timeOfDelivery()));
        _emailGateway.send(delivery.contactEmail(), "Your feedback is important to us", message);
        if (deliverySchedule.size() > i + 1) {
          nextDelivery = deliverySchedule.get(i + 1);
        }

        if (!delivery.onTime() && deliverySchedule.size() > 1 && i > 0) {
          var previousDelivery = deliverySchedule.get(i - 1);
          Duration elapsedTime = Duration.between(previousDelivery.timeOfDelivery(),
              delivery.timeOfDelivery());
          _mapService.updateAverageSpeed(previousDelivery.location(), delivery.location(),
              elapsedTime);
        }
      }
    }

    if (nextDelivery != null) {
      var nextEta = _mapService.calculateETA(deliveryEvent.location(), nextDelivery.location());
      var message =

          "Your delivery to %s is next, estimated time of arrival is in %s minutes. Be ready!".formatted(
              nextDelivery.location(), nextEta.getSeconds() / 60);
      _emailGateway.send(nextDelivery.contactEmail(), "Your delivery will arrive soon", message);
    }
  }
}
