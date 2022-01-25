import datetime
from dataclasses import dataclass
from math import sin, cos, sqrt, atan2, radians

# approximate radius of earth in km
R = 6373.0
MINUTES_PER_HOUR = 60
SECONDS_PER_HOUR = 3600

@dataclass
class Location:
    latitude: float
    longitude: float

class MapService:
    def __init__(self, average_speed=50):
        "average speed in km/h"
        self.average_speed = average_speed

    def calculate_eta(self, location1 : Location, location2 : Location) -> float:
        "return the number of minutes it will take to travel between locations at average speed."
        distance = self.calculate_distance(location1, location2)
        return distance / self.average_speed * MINUTES_PER_HOUR

    def calculate_distance(self, location1 : Location, location2 : Location) -> float:
        lat1 = radians(location1.latitude)
        lon1 = radians(location1.longitude)
        lat2 = radians(location2.latitude)
        lon2 = radians(location2.longitude)
        dlon = lon2 - lon1
        dlat = lat2 - lat1
        a = sin(dlat / 2) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2) ** 2
        c = 2 * atan2(sqrt(a), sqrt(1 - a))
        # km
        distance = R * c
        return distance

    def update_average_speed(self, location1 : Location, location2 : Location, elapsed_time: datetime.timedelta):
        distance = self.calculate_distance(location1, location2)
        updated_speed = distance / (elapsed_time.seconds / SECONDS_PER_HOUR)
        self.average_speed = updated_speed

