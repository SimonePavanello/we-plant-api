import { Moment } from 'moment';

export const enum TipoDiSuolo {
    AREA_VERDE = 'AREA_VERDE',
    ZANELLA = 'ZANELLA',
    AIUOLA = 'AIUOLA'
}

export interface IAlbero {
    id?: number;
    entityid?: number;
    idPianta?: number;
    codiceArea?: number;
    nomeComune?: string;
    classeAltezza?: number;
    altezza?: number;
    diametroFusto?: number;
    diametro?: number;
    wkt?: string;
    aggiornamento?: string;
    nota?: string;
    tipoDiSuolo?: TipoDiSuolo;
    dataImpianto?: Moment;
    dataAbbattimento?: Moment;
    dataUltimoAggiornamento?: Moment;
    dataPrimaRilevazione?: Moment;
    noteTecniche?: string;
    posizione?: string;
    deleted?: boolean;
    essenzaNomeComune?: string;
    essenzaId?: number;
    modificatoDaEmail?: string;
    modificatoDaId?: number;
    mainId?: number;
}

export class Albero implements IAlbero {
    constructor(
        public id?: number,
        public entityid?: number,
        public idPianta?: number,
        public codiceArea?: number,
        public nomeComune?: string,
        public classeAltezza?: number,
        public altezza?: number,
        public diametroFusto?: number,
        public diametro?: number,
        public wkt?: string,
        public aggiornamento?: string,
        public nota?: string,
        public tipoDiSuolo?: TipoDiSuolo,
        public dataImpianto?: Moment,
        public dataAbbattimento?: Moment,
        public dataUltimoAggiornamento?: Moment,
        public dataPrimaRilevazione?: Moment,
        public noteTecniche?: string,
        public posizione?: string,
        public deleted?: boolean,
        public essenzaNomeComune?: string,
        public essenzaId?: number,
        public modificatoDaEmail?: string,
        public modificatoDaId?: number,
        public mainId?: number
    ) {
        this.deleted = false;
    }
}
