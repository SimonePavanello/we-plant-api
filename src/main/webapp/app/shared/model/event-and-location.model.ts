import { INinoUser } from 'app/shared/model//nino-user.model';
import { IPoi } from 'app/shared/model//poi.model';

export interface IEventAndLocation {
    id?: number;
    name?: string;
    adminUsers?: INinoUser[];
    pois?: IPoi[];
}

export class EventAndLocation implements IEventAndLocation {
    constructor(public id?: number, public name?: string, public adminUsers?: INinoUser[], public pois?: IPoi[]) {}
}
