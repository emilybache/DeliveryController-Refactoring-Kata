import pytest
import datetime

from delivery_controller import DeliveryController, Delivery, DeliveryEvent
from map_service import MapService, Location

location1 = Location(52.2296756, 21.0122287)
location2 = Location(52.406374, 16.9251681)

def test_map_service():
    map_service = MapService()
    assert map_service.calculate_distance(location1, location2) == pytest.approx(278.546, rel=1e-2)


class FakeEmailGateway:
    def send(self, address, subject, message):
        self.address = address
        self.subject = subject
        self.message = message

def test_delivery_calculator():
    delivery1 = Delivery("1234", "customer1@example.com", location1, datetime.datetime.fromisoformat('2021-09-03T15:56'), False, False)
    delivery2 = Delivery("5678", "customer2@example.com", location2, datetime.datetime.fromisoformat('2021-09-03T21:00'), False, False)
    deliveries = [delivery1, delivery2]
    sut = DeliveryController(deliveries)
    sut.email_gateway = FakeEmailGateway()
    sut.update_delivery(DeliveryEvent("1234", datetime.datetime.fromisoformat('2021-09-03T16:00'), location1))
    assert delivery1.time_of_delivery == datetime.datetime.fromisoformat('2021-09-03T16:00')
    assert delivery1.was_on_time() == True
    assert sut.email_gateway.message != None
    assert sut.map_service.average_speed == 50

def test_delivery_calculator_late():
    delivery1 = Delivery("1234", "customer1@example.com", location1, datetime.datetime.fromisoformat('2021-09-03T15:46'), False, False)
    delivery2 = Delivery("5678", "customer2@example.com", location2, datetime.datetime.fromisoformat('2021-09-03T21:00'), False, False)
    deliveries = [delivery1, delivery2]
    sut = DeliveryController(deliveries)
    sut.email_gateway = FakeEmailGateway()
    sut.update_delivery(DeliveryEvent("5678", datetime.datetime.fromisoformat('2021-09-03T21:15'), location1))
    assert delivery2.time_of_delivery == datetime.datetime.fromisoformat('2021-09-03T21:15')
    assert delivery2.was_on_time() == False
    assert sut.email_gateway.message != None
    assert sut.map_service.average_speed == pytest.approx(50.79, rel=1e-2)
