import { Moment } from 'moment';

export const enum EmailStatus {
    SUCCESS = 'SUCCESS',
    FAILED = 'FAILED'
}

export interface IEmail {
    id?: number;
    from?: string;
    to?: string;
    date?: Moment;
    sesMessageId?: string;
    status?: EmailStatus;
    htmlBody?: string;
    textBody?: string;
    subject?: string;
}

export class Email implements IEmail {
    constructor(
        public id?: number,
        public from?: string,
        public to?: string,
        public date?: Moment,
        public sesMessageId?: string,
        public status?: EmailStatus,
        public htmlBody?: string,
        public textBody?: string,
        public subject?: string
    ) {}
}
