import {EmailGateway} from './emailGateway'
import {Location, MapService} from './mapService';

const TEN_MINUTES = 1000 * 60 * 10;

export interface Delivery {
   id: string
    contactEmail: string
    location: Location
    timeOfDelivery: Date
    arrived: boolean
    onTime: boolean
}

export interface DeliveryEvent {
    id: string
    timeOfDelivery: Date
    location: Location
}

export class DeliveryController {
    #emailGateway: EmailGateway;
    #mapService: MapService;
    #deliveries: Array<Delivery>;

    constructor(deliveries: Array<Delivery>) {
        this.#deliveries = deliveries;
        this.#mapService = new MapService();
        this.#emailGateway = new EmailGateway();
    }

    public async updateDelivery(event: DeliveryEvent) {
        let nextDelivery: Delivery;

        for(let i = 0; i < this.#deliveries.length; i++){
            const delivery = this.#deliveries[i];
            if (delivery.id === event.id) {
                delivery.arrived = true;
                var timeDifference = delivery.timeOfDelivery.getTime() - event.timeOfDelivery.getTime();
                if (timeDifference < TEN_MINUTES) {
                    delivery.onTime = true;
                }
                delivery.timeOfDelivery = event.timeOfDelivery;
                let message = `Regarding your delivery today at ${delivery.timeOfDelivery}. How likely would you be to recommend this delivery service to a friend? Click <a href='url'>here</a>`
                this.#emailGateway.send(delivery.contactEmail, "Your feedback is important to us", message)
                if(this.#deliveries.length > i + 1) {
                    nextDelivery = this.#deliveries[i + 1];
                }

                if(!delivery.onTime && this.#deliveries.length > 1 && i > 0) {
                    var previousDelivery = this.#deliveries[i - 1];
                    var elapsedTime = delivery.timeOfDelivery.getTime() - previousDelivery.timeOfDelivery.getTime();
                    this.#mapService.updateAverageSpeed(previousDelivery.location, delivery.location, elapsedTime);
                }
            }
        }

        if (nextDelivery !== undefined) {
            var nextEta = this.#mapService.calculateETA(event.location, nextDelivery.location);
            const message = `Your delivery to ${nextDelivery.location} is next, estimated time of arrival is in ${nextEta} minutes. Be ready!`
            await this.#emailGateway.send(nextDelivery.contactEmail, "Your delivery will arrive soon.", message);
        }
    }
}
