import { IEventAndLocation } from 'app/shared/model//event-and-location.model';

export interface INinoUser {
    id?: number;
    userId?: number;
    administeredEventsAndLocations?: IEventAndLocation[];
}

export class NinoUser implements INinoUser {
    constructor(public id?: number, public userId?: number, public administeredEventsAndLocations?: IEventAndLocation[]) {}
}
