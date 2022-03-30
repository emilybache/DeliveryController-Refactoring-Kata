import datetime
from dataclasses import dataclass

from email_gateway import EmailGateway
from map_service import MapService, Location


@dataclass(frozen=True)
class DeliveryEvent:
    id: str
    time_of_delivery: datetime.datetime
    location: Location

@dataclass
class Delivery:
    id: str
    contact_email: str
    location: Location
    time_of_delivery: datetime.datetime
    arrived: bool
    on_time: bool


class DeliveryController:

    def __init__(self, delivery_schedule : list):
        self.delivery_schedule = delivery_schedule
        self.email_gateway = EmailGateway()
        self.map_service = MapService()

    def update_delivery(self, delivery_event : DeliveryEvent):
        next_delivery = None
        for i, delivery in enumerate(self.delivery_schedule):
            if delivery_event.id == delivery.id:
                delivery.arrived = True
                time_difference = delivery_event.time_of_delivery - delivery.time_of_delivery
                if time_difference < datetime.timedelta(minutes=10):
                    delivery.on_time = True
                delivery.time_of_delivery = delivery_event.time_of_delivery
                message = f"""Regarding your delivery today at {delivery.time_of_delivery}. How likely would you be to recommend this delivery service to a friend? Click <a href="url">here</a>"""
                self.email_gateway.send(delivery.contact_email, "Your feedback is important to us", message)
                if len(self.delivery_schedule) > i+1:
                    next_delivery = self.delivery_schedule[i+1]
                if not delivery.on_time and len(self.delivery_schedule) > 1 and i > 0:
                    previous_delivery = self.delivery_schedule[i-1]
                    elapsed_time = delivery.time_of_delivery - previous_delivery.time_of_delivery
                    self.map_service.update_average_speed(previous_delivery.location, delivery.location, elapsed_time)
        if next_delivery:
            next_eta = self.map_service.calculate_eta(delivery_event.location, next_delivery.location)
            message = f"Your delivery to {next_delivery.location} is next, estimated time of arrival is in {next_eta:.0f} minutes. Be ready!"
            self.email_gateway.send(next_delivery.contact_email, "Your delivery will arrive soon", message)



