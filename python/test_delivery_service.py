import pytest
import datetime

from delivery_controller import DeliveryController, Delivery, DeliveryEvent
from map_service import MapService, Location

location1 = Location(52.2296756, 21.0122287)
location2 = Location(52.406374, 16.9251681)

def test_map_service():
    map_service = MapService()
    assert map_service.calculate_distance(location1, location2) == pytest.approx(278.546, rel=1e-2)

# TODO: test delivery_controller