import {Location, MapService} from './mapService'

describe("Map service", () =>{

    const location1 = {latitude: 52.2296756, longitude: 21.0122287}
    const location2 = {latitude: 52.406374, longitude: 16.9251681}

    const maps = new MapService();

    it("should work!", () => {
        expect(maps.calculateDistance(location1, location2))
            .toBeCloseTo(278.546, 3);
    });
});
