import { Moment } from 'moment';

export interface IImage {
    id?: number;
    createDate?: Moment;
    modifiedDate?: Moment;
    name?: string;
    format?: string;
    location?: string;
    imagePath?: string;
    thumbnailPath?: string;
    alberoNomeComune?: string;
    alberoId?: number;
    poiName?: string;
    poiId?: number;
    cratedById?: number;
}

export class Image implements IImage {
    constructor(
        public id?: number,
        public createDate?: Moment,
        public modifiedDate?: Moment,
        public name?: string,
        public format?: string,
        public location?: string,
        public imagePath?: string,
        public thumbnailPath?: string,
        public alberoNomeComune?: string,
        public alberoId?: number,
        public poiName?: string,
        public poiId?: number,
        public cratedById?: number
    ) {}
}
