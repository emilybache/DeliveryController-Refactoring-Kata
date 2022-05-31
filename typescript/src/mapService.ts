
const MINUTES_PER_HOUR = 60;
const SECONDS_PER_HOUR = 60 * MINUTES_PER_HOUR;
const R = 6373.0; // Radius of the earth

const radians = (d: number) : number => d * (Math.PI / 180);

export interface Location {
    latitude: number
    longitude: number
}

export class MapService {

    #averageSpeed : number;

    public calculateETA(location1: Location, location2: Location) {
        const distance = this.calculateDistance(location1, location2)
        return distance / this.#averageSpeed * MINUTES_PER_HOUR;
    }

    public calculateDistance(location1: Location, location2: Location) {
        const lat1 = radians(location1.latitude);
        const lon1 = radians(location1.longitude);
        const lat2 = radians(location2.latitude);
        const lon2 = radians(location2.longitude);

        const dlat = lat2 - lat1
        const dlon = lon2 - lon1

        const a = Math.sin(dlat / 2) ** 2 + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2) ** 2
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public updateAverageSpeed(location1: Location, location2: Location, elapsed: number) {
        const distance = this.calculateDistance(location1, location2)
        this.#averageSpeed = distance / (elapsed / SECONDS_PER_HOUR)
    }

}
