import { INinoUser } from 'app/shared/model//nino-user.model';
import { IStop } from 'app/shared/model//stop.model';

export interface ILocation {
    id?: number;
    name?: string;
    adminUsers?: INinoUser[];
    locations?: IStop[];
}

export class Location implements ILocation {
    constructor(public id?: number, public name?: string, public adminUsers?: INinoUser[], public locations?: IStop[]) {}
}
