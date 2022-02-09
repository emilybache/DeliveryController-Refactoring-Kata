package kata;

import java.time.Duration;

public class MapService {

  // in km/h
  private double _averageSpeed = 50.0;

  private final int MINUTES_PER_HOUR = 60;
  private final int SECONDS_PER_HOUR = 3600;
  private final double R = 6373.0;

  public Duration calculateETA(Location location, Location location1) {
    var distance = this.calculateDistance(location, location1);
    Double v = distance / this._averageSpeed * MINUTES_PER_HOUR;
    return Duration.ofMinutes(v.longValue());
  }

  public void updateAverageSpeed(Location location1, Location location2, Duration elapsedTime) {
    var distance = this.calculateDistance(location1, location2);
    var updatedSpeed = distance / (elapsedTime.getSeconds() / (double) SECONDS_PER_HOUR);
    this._averageSpeed = updatedSpeed;
  }

  private double calculateDistance(Location location1, Location location2) {
    var d1 = location1.latitude() * (Math.PI / 180.0);
    var num1 = location1.longitude() * (Math.PI / 180.0);
    var d2 = location2.latitude() * (Math.PI / 180.0);
    var num2 = location2.longitude() * (Math.PI / 180.0) - num1;
    var d3 = Math.pow(Math.sin((d2 - d1) / 2.0), 2.0) + Math.cos(d1) * Math.cos(d2) * Math.pow(
        Math.sin(num2 / 2.0), 2.0);

    return R * (2.0 * Math.atan2(Math.sqrt(d3), Math.sqrt(1.0 - d3)));
  }
}
