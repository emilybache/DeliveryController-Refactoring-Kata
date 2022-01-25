using System;
using System.Collections.Generic;


namespace DeliveryController
{
    public class DeliveryController
    {
        private readonly EmailGateway _emailGateway;
        private readonly MapService _mapService;
        public List<Delivery> DeliverySchedule { get; }

        public DeliveryController(List<Delivery> deliverySchedule)
        {
            DeliverySchedule = deliverySchedule;
            _emailGateway = new EmailGateway();
            _mapService = new MapService();
        }
        
        public void UpdateDelivery(DeliveryEvent deliveryEvent)
        {
            Delivery nextDelivery = null;
            for (int i = 0; i < DeliverySchedule.Count; i++)
            {
                Delivery delivery = DeliverySchedule[i];
                if (deliveryEvent.Id == delivery.Id)
                {
                    delivery.Arrived = true;
                    var timeDifference = deliveryEvent.TimeOfDelivery - delivery.TimeOfDelivery;
                    if (timeDifference < TimeSpan.FromMinutes(10))
                    {
                        delivery.OnTime = true;
                    }
                    delivery.TimeOfDelivery = deliveryEvent.TimeOfDelivery;
                    string message = $"Regarding your delivery today at {delivery.TimeOfDelivery}. How likely would you be to recommend this delivery service to a friend? Click <a href='url'>here</a>";
                    _emailGateway.send(delivery.ContactEmail, "Your feedback is important to us", message);
                    if (DeliverySchedule.Count > i + 1)
                    {
                        nextDelivery = DeliverySchedule[i + 1];
                    }

                    if (!delivery.OnTime && DeliverySchedule.Count > 1 && i > 0)
                    {
                        var previousDelivery = DeliverySchedule[i - 1];
                        var elapsedTime = delivery.TimeOfDelivery - previousDelivery.TimeOfDelivery;
                        _mapService.UpdateAverageSpeed(previousDelivery.Location, delivery.Location, elapsedTime);
                    }
                }
            }

            if (nextDelivery != null)
            {
                var nextEta = _mapService.CalculateETA(deliveryEvent.Location, nextDelivery.Location);
                var message =
                    $"Your delivery to {nextDelivery.Location} is next, estimated time of arrival is in {nextEta} minutes. Be ready!";
                _emailGateway.send(nextDelivery.ContactEmail, "Your delivery will arrive soon", message);

            }
        }
    }
    

}