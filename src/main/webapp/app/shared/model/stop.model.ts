import { Moment } from 'moment';

export const enum StopType {
    REGULAR = 'REGULAR',
    URGENT = 'URGENT',
    SECONDARY = 'SECONDARY',
    MY_POSITION = 'MY_POSITION'
}

export interface IStop {
    id?: number;
    poiId?: string;
    reached?: boolean;
    startTime?: Moment;
    endTime?: Moment;
    stopType?: StopType;
    lat?: number;
    lon?: number;
    visitId?: number;
}

export class Stop implements IStop {
    constructor(
        public id?: number,
        public poiId?: string,
        public reached?: boolean,
        public startTime?: Moment,
        public endTime?: Moment,
        public stopType?: StopType,
        public lat?: number,
        public lon?: number,
        public visitId?: number
    ) {
        this.reached = false;
    }
}
