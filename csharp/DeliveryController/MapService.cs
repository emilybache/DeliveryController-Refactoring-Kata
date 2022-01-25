using System;

namespace DeliveryController
{
    public class MapService 
    {
        // in km/h
        private double _averageSpeed = 50.0;

        public double CalculateETA(Location location1, Location location2)
        {
            var distance = this.CalculateDistance(location1, location2);
            return distance / this._averageSpeed * MINUTES_PER_HOUR;
        }

        public void UpdateAverageSpeed(Location location1, Location location2, TimeSpan elapsedTime)
        {
            var distance = this.CalculateDistance(location1, location2);
            var updatedSpeed = distance / (elapsedTime.Seconds / SECONDS_PER_HOUR);
            this._averageSpeed = updatedSpeed;
        }

        private double CalculateDistance(Location location1, Location location2)
        {
            var d1 = location1.Latitude * (Math.PI / 180.0);
            var num1 = location1.Longitude * (Math.PI / 180.0);
            var d2 = location2.Latitude * (Math.PI / 180.0);
            var num2 = location2.Longitude * (Math.PI / 180.0) - num1;
            var d3 = Math.Pow(Math.Sin((d2 - d1) / 2.0), 2.0) + Math.Cos(d1) * Math.Cos(d2) * Math.Pow(Math.Sin(num2 / 2.0), 2.0);
    
            return R * (2.0 * Math.Atan2(Math.Sqrt(d3), Math.Sqrt(1.0 - d3)));
        }

        private const int MINUTES_PER_HOUR = 60;
        private const int SECONDS_PER_HOUR = 3600;
        private const double R = 6373.0;
    }
}