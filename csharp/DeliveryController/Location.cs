namespace DeliveryController
{
    public class Location
    {
        public float Latitude { get; }
        public float Longitude { get; }

        public Location(float latitude, float longitude)
        {
            Latitude = latitude;
            Longitude = longitude;
        }
    }
}