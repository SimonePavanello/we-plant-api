import { Moment } from 'moment';
import { IStop } from 'app/shared/model//stop.model';

export const enum VisitDifficulty {
    LOW = 'LOW',
    MEDIUM = 'MEDIUM',
    NO_LIMIT = 'NO_LIMIT'
}

export interface IVisit {
    id?: number;
    lastLat?: number;
    lastLon?: number;
    exitPoiId?: string;
    maxVisitTime?: number;
    startTime?: Moment;
    createdDate?: Moment;
    modifiedDate?: Moment;
    maxVisitLengthMeters?: number;
    active?: boolean;
    inProgress?: boolean;
    difficulty?: VisitDifficulty;
    startPointId?: number;
    endPointId?: number;
    stops?: IStop[];
    userId?: number;
    eventAndlocationId?: number;
}

export class Visit implements IVisit {
    constructor(
        public id?: number,
        public lastLat?: number,
        public lastLon?: number,
        public exitPoiId?: string,
        public maxVisitTime?: number,
        public startTime?: Moment,
        public createdDate?: Moment,
        public modifiedDate?: Moment,
        public maxVisitLengthMeters?: number,
        public active?: boolean,
        public inProgress?: boolean,
        public difficulty?: VisitDifficulty,
        public startPointId?: number,
        public endPointId?: number,
        public stops?: IStop[],
        public userId?: number,
        public eventAndlocationId?: number
    ) {
        this.active = false;
        this.inProgress = false;
    }
}
