import { Moment } from 'moment';

export interface IPrivacy {
    id?: number;
    time?: Moment;
    privacy?: boolean;
    userId?: number;
}

export class Privacy implements IPrivacy {
    constructor(public id?: number, public time?: Moment, public privacy?: boolean, public userId?: number) {
        this.privacy = false;
    }
}
