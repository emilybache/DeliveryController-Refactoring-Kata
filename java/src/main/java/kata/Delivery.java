package kata;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Delivery {

  private long id;
  private String contactEmail;
  private Location location;
  private LocalDateTime timeOfDelivery;
  private boolean arrived;
  private boolean onTime;

  public Delivery(long id, String contactEmail, Location location, LocalDateTime timeOfDelivery,
      boolean arrived, boolean onTime) {
    this.id = id;
    this.contactEmail = contactEmail;
    this.location = location;
    this.timeOfDelivery = timeOfDelivery;
    this.arrived = arrived;
    this.onTime = onTime;
  }

  public long id() {
    return id;
  }

  public String contactEmail() {
    return contactEmail;
  }

  public Location location() {
    return location;
  }

  public LocalDateTime timeOfDelivery() {
    return timeOfDelivery;
  }

  public boolean arrived() {
    return arrived;
  }

  public void setTimeOfDelivery(LocalDateTime timeOfDelivery) {
    this.timeOfDelivery = timeOfDelivery;
  }

  public void setArrived(boolean arrived) {
    this.arrived = arrived;
  }

  public void setOnTime(boolean onTime) {
    this.onTime = onTime;
  }

  public boolean onTime() {
    return onTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Delivery) obj;
    return Objects.equals(this.id, that.id) &&
        Objects.equals(this.contactEmail, that.contactEmail) &&
        Objects.equals(this.location, that.location) &&
        Objects.equals(this.timeOfDelivery, that.timeOfDelivery) &&
        this.arrived == that.arrived &&
        this.onTime == that.onTime;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, contactEmail, location, timeOfDelivery, arrived, onTime);
  }

  @Override
  public String toString() {
    return "Delivery[" +
        "id=" + id + ", " +
        "contactEmail=" + contactEmail + ", " +
        "location=" + location + ", " +
        "timeOfDelivery=" + timeOfDelivery + ", " +
        "arrived=" + arrived + ", " +
        "onTime=" + onTime + ']';
  }
}
