using System;

namespace DeliveryController
{
    public class DeliveryEvent
    {
        public string Id { get; }
        public DateTime TimeOfDelivery { get; }
        public Location Location { get; }

        public DeliveryEvent(string id, DateTime timeOfDelivery, Location location)
        {
            Id = id;
            TimeOfDelivery = timeOfDelivery;
            Location = location;
        }
    }
}