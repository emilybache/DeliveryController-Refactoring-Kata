using System;
using System.Net.Mail;

namespace DeliveryController
{
    public class Delivery
    {
        public string Id { get; }
        public string ContactEmail { get; }
        public Location Location { get; }
        public DateTime TimeOfDelivery { get; set; }
        public bool Arrived { get; set; }
        public bool OnTime { get; set; }

        public Delivery(string id, string contactEmail, Location location, DateTime timeOfDelivery, bool arrived, bool onTime)
        {
            Id = id;
            ContactEmail = contactEmail;
            Location = location;
            TimeOfDelivery = timeOfDelivery;
            Arrived = arrived;
            OnTime = onTime;
        }
        
    }
}