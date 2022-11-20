import { Moment } from 'moment';

export interface IEssenzaAudit {
    id?: number;
    idComune?: number;
    tipoVerde?: string;
    genereESpecie?: string;
    nomeComune?: string;
    valoreSicurezza?: string;
    valoreBioAmbientale?: string;
    provenienza?: string;
    descrizione?: string;
    usieCuriosita?: string;
    genere?: string;
    specie?: string;
    dataUltimoAggiornamento?: Moment;
    essenzaId?: number;
    modificatoDaEmail?: string;
    modificatoDaId?: number;
}

export class EssenzaAudit implements IEssenzaAudit {
    constructor(
        public id?: number,
        public idComune?: number,
        public tipoVerde?: string,
        public genereESpecie?: string,
        public nomeComune?: string,
        public valoreSicurezza?: string,
        public valoreBioAmbientale?: string,
        public provenienza?: string,
        public descrizione?: string,
        public usieCuriosita?: string,
        public genere?: string,
        public specie?: string,
        public dataUltimoAggiornamento?: Moment,
        public essenzaId?: number,
        public modificatoDaEmail?: string,
        public modificatoDaId?: number
    ) {}
}
