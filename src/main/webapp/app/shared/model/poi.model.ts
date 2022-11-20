import { IEventAndLocation } from 'app/shared/model//event-and-location.model';

export interface IPoi {
    id?: number;
    poiId?: string;
    name?: string;
    eventsAndlocations?: IEventAndLocation[];
}

export class Poi implements IPoi {
    constructor(public id?: number, public poiId?: string, public name?: string, public eventsAndlocations?: IEventAndLocation[]) {}
}
