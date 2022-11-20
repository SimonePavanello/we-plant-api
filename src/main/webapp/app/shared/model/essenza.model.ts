export interface IEssenza {
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
}

export class Essenza implements IEssenza {
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
        public specie?: string
    ) {}
}
