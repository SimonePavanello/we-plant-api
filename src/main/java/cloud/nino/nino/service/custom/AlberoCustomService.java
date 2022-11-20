package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Albero;
import cloud.nino.nino.domain.Essenza;
import cloud.nino.nino.domain.EssenzaAudit;
import cloud.nino.nino.domain.User;
import cloud.nino.nino.repository.EssenzaRepository;
import cloud.nino.nino.repository.UserRepository;
import cloud.nino.nino.repository.custom.AlberoCustomRepository;
import cloud.nino.nino.repository.custom.EssenzaAuditCustomRepository;
import cloud.nino.nino.repository.custom.ImageCustomRepository;
import cloud.nino.nino.security.SecurityUtils;
import cloud.nino.nino.service.dto.AlberoDTO;
import cloud.nino.nino.service.dto.EssenzaAuditDTO;
import cloud.nino.nino.service.dto.EssenzaDTO;
import cloud.nino.nino.service.dto.custom.AlberoCustomDTO;
import cloud.nino.nino.service.dto.custom.AlberoUserOperations;
import cloud.nino.nino.service.dto.custom.LatLon;
import cloud.nino.nino.service.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Albero.
 */
@Service
@Transactional
public class AlberoCustomService {

    private final Logger log = LoggerFactory.getLogger(AlberoCustomService.class);

    private final AlberoCustomRepository alberoCustomRepository;

    private final AlberoMapper alberoMapper;

    private final EssenzaMapper essenzaMapper;

    private final EssenzaAuditMapper essenzaAuditMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final EssenzaAuditCustomRepository essenzaAuditCustomRepository;

    private final EssenzaRepository essenzaRepository;

    private final ImageCustomRepository imageCustomRepository;

    private final ImageMapper imageMapper;

    public AlberoCustomService(AlberoCustomRepository alberoCustomRepository, AlberoMapper alberoMapper, EssenzaMapper essenzaMapper, EssenzaAuditMapper essenzaAuditMapper, UserRepository userRepository, UserMapper userMapper, EssenzaAuditCustomRepository essenzaAuditCustomRepository, EssenzaRepository essenzaRepository, ImageCustomRepository imageCustomRepository, ImageMapper imageMapper) {
        this.alberoCustomRepository = alberoCustomRepository;
        this.alberoMapper = alberoMapper;
        this.essenzaMapper = essenzaMapper;
        this.essenzaAuditMapper = essenzaAuditMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.essenzaAuditCustomRepository = essenzaAuditCustomRepository;
        this.essenzaRepository = essenzaRepository;
        this.imageCustomRepository = imageCustomRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Save a albero.
     *
     * @param alberoDTO the entity to save
     * @return the persisted entity
     */
    public AlberoDTO save(AlberoDTO alberoDTO) {
        log.debug("Request to save Albero : {}", alberoDTO);
        Albero albero = alberoMapper.toEntity(alberoDTO);
        albero = alberoCustomRepository.save(albero);
        return alberoMapper.toDto(albero);
    }

    /**
     * Get all the alberos.
     *
     * @param pageable
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlberoCustomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alberos");
        return alberoCustomRepository.findAllByMainIdEqualsToId(pageable).stream()
            .map(this::findAlbero)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    private AlberoCustomDTO findAlbero(Albero albero) {
        Optional<AlberoCustomDTO> alberoCustomDTO = alberoCustomRepository.findFirstByMainIdOrderByDataUltimoAggiornamentoDesc(albero.getMain().getId()).map(this::getAlberCustomDto);
        if (!alberoCustomDTO.isPresent()) {
            return new AlberoCustomDTO(alberoMapper.toDto(albero));
        } else {
            return alberoCustomDTO.get();
        }

    }


    /**
     * Get one albero by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AlberoCustomDTO> findOne(Long id) {
        log.debug("Request to get Albero : {}", id);
        Optional<AlberoCustomDTO> alberoCustomDTO = alberoCustomRepository.findById(id).map(this::getAlberCustomDto);
        if (!alberoCustomDTO.isPresent()) {
            return Optional.of(null);
        }
        if (alberoCustomDTO.get().getId() == alberoCustomDTO.get().getMainId()) {
            return alberoCustomDTO;
        }
        return alberoCustomRepository.findFirstByMainIdOrderByDataUltimoAggiornamentoDesc(alberoCustomDTO.get().getMainId()).map(this::getAlberCustomDto);
    }

    /**
     * Delete the albero by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Albero : {}", id);
        alberoCustomRepository.deleteById(id);
    }

    /**
     * Get one albero with it's essenza by id pianta.
     *
     * @param idPianta
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AlberoCustomDTO> findByIdPianta(Integer idPianta) {
        log.debug("Request to get Albero : {}", idPianta);
        return alberoCustomRepository.findFirstByIdPiantaOrderByDataUltimoAggiornamentoDesc(idPianta)
            .map(this::getAlberCustomDto);
    }

    private AlberoCustomDTO getAlberCustomDto(Albero albero) {
        EssenzaDTO essenzaDTO = essenzaMapper.toDto(albero.getEssenza());
        AlberoCustomDTO alberoCustomDTO = new AlberoCustomDTO(alberoMapper.toDto(albero));
        alberoCustomDTO.setEssenza(essenzaDTO);
        essenzaAuditCustomRepository.findFirstByEssenzaOrderByDataUltimoAggiornamentoDesc(albero.getEssenza())
            .ifPresent(essenzaAudit -> {
                alberoCustomDTO.setEssenzaAudit(essenzaAuditMapper.toDto(essenzaAudit)); //if present override with last modified essenza value
            });
        if (albero.getMain().getId() != null) {
            alberoCustomDTO.setImages(imageCustomRepository.findByAlbero_Id(albero.getMain().getId()).stream().map(imageMapper::toDto).collect(Collectors.toList()));
        }
        return alberoCustomDTO;
    }


    /**
     * Save details of albero and essenza with audit logics
     *
     * @param alberoCustomDTO
     * @return
     */
    public AlberoCustomDTO saveAlberoAndEssenzaAudit(AlberoCustomDTO alberoCustomDTO) {
        AlberoDTO alberoDTO = new AlberoDTO();
        //retrieve current user;
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new UsernameNotFoundException("Current user not found"));
        User currentUser = userRepository.findOneByLogin(currentUserLogin).get();
        //update essenza audit
        EssenzaAuditDTO essenzaAuditDTO = new EssenzaAuditDTO();
        BeanUtils.copyProperties(alberoCustomDTO.getEssenza(), essenzaAuditDTO);
        EssenzaAudit essenzaAudit = essenzaAuditMapper.toEntity(essenzaAuditDTO);
        //remove id to allow creating new record
        essenzaAudit.setId(null);
        essenzaAudit.setModificatoDa(currentUser);
        essenzaAudit.setDataUltimoAggiornamento(ZonedDateTime.now());
        //Set the original Essenza (not EssenzaAudit that has different id)
        Essenza originalEssenza = new Essenza();
        originalEssenza.setId(alberoCustomDTO.getEssenzaId());
        essenzaAudit.setEssenza(originalEssenza);
        this.essenzaAuditCustomRepository.save(essenzaAudit);
        //create new record of albero (audit logic)
        BeanUtils.copyProperties(alberoCustomDTO, alberoDTO);
        alberoDTO.setDeleted(alberoCustomDTO.isDeleted());
        Albero albero = alberoMapper.toEntity(alberoDTO);
        albero.setModificatoDa(currentUser);
        albero.setDataUltimoAggiornamento(ZonedDateTime.now());
        albero.setNomeComune(alberoCustomDTO.getEssenza().getNomeComune());
        Albero main = new Albero();
        if (alberoDTO.getMainId() != null) {
            main.setId(alberoDTO.getMainId());
            albero.setMain(main);
        }
        //remove id to allow creating new record
        albero.setId(null);
        alberoCustomRepository.save(albero);
        if (alberoDTO.getMainId() == null) {
            albero.setMain(albero);
            alberoCustomRepository.save(albero);
        }

        BeanUtils.copyProperties(alberoMapper.toDto(albero), alberoCustomDTO);
        return alberoCustomDTO;
    }

    public List<EssenzaDTO> findAllEssenza() {
        return this.essenzaRepository.findAll().stream().map(essenza -> {
            Optional<EssenzaAudit> essenzaAuditOptional = essenzaAuditCustomRepository.findFirstByEssenzaOrderByDataUltimoAggiornamentoDesc(essenza);
            if (essenzaAuditOptional.isPresent()) {
                essenza.setNomeComune(essenzaAuditOptional.get().getNomeComune());
            }
            return essenzaMapper.toDto(essenza);
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    public List<AlberoDTO> findAllWithoutPlantId() {
        List<AlberoDTO> alberoDTOS = new LinkedList<>();
        this.alberoCustomRepository.findDistinctByMain().stream().forEach(aLong -> {
            Optional<Albero> albero = alberoCustomRepository.findFirstByMain_IdOrderByDataUltimoAggiornamentoDesc(aLong);
            if (albero.get().isDeleted() == null || !albero.get().isDeleted()) {
                alberoDTOS.add(alberoMapper.toDto(albero.get()));
            }
        });
        return alberoDTOS;
    }

    public Boolean isIdPiantaFree(Integer idPianta) {
        return !this.alberoCustomRepository.findFirstByIdPianta(idPianta).isPresent();
    }

    public List<AlberoDTO> getAlberosByCodiceArea(Integer codiceArea) {
        List<AlberoDTO> alberoDTOS = new LinkedList<>();
        this.alberoCustomRepository.findDistinctByMainAndCodiceArea(codiceArea).stream().forEach(aLong -> {
            Optional<Albero> albero = alberoCustomRepository.findFirstByMain_IdOrderByDataUltimoAggiornamentoDesc(aLong);
            if (albero.get().isDeleted() == null || !albero.get().isDeleted()) {
                alberoDTOS.add(alberoMapper.toDto(albero.get()));
            }
        });
        return alberoDTOS;
    }

    public List<AlberoUserOperations> getUserOperationsOnTrees(ZonedDateTime from, ZonedDateTime to) {
        List<Albero> alberos = this.alberoCustomRepository.findAllByModificatoDaNotNullAndDataUltimoAggiornamentoIsGreaterThanEqualAndDataUltimoAggiornamentoIsLessThanEqual(from, to);
        Map<Long, List<AlberoCustomDTO>> dtoAlberoDTOMap = new HashMap<>();
        alberos.forEach(albero -> {
            if (dtoAlberoDTOMap.containsKey(albero.getModificatoDa().getId())) {
                AlberoCustomDTO alberoDTO = new AlberoCustomDTO();
                BeanUtils.copyProperties(alberoMapper.toDto(albero), alberoDTO);
                alberoDTO.setModificatoDa(userMapper.userToUserDTO(albero.getModificatoDa()));
                dtoAlberoDTOMap.get(albero.getModificatoDa().getId()).add(alberoDTO);
            } else {
                AlberoCustomDTO alberoDTO = new AlberoCustomDTO();
                BeanUtils.copyProperties(alberoMapper.toDto(albero), alberoDTO);
                alberoDTO.setModificatoDa(userMapper.userToUserDTO(albero.getModificatoDa()));
                List<AlberoCustomDTO> alberoDTOS = new LinkedList<>();
                alberoDTOS.add(alberoDTO);
                dtoAlberoDTOMap.put(albero.getModificatoDa().getId(), alberoDTOS);
            }
        });
        return dtoAlberoDTOMap.entrySet().stream().map(entity -> {
            entity.getKey();
            entity.getValue();
            return new AlberoUserOperations(entity.getValue().get(0).getModificatoDa(), entity.getValue());
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    public ByteArrayOutputStream downloadAlberoHistory(List<Long> mainIds) {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Pagina 1");
        Row header = sheet.createRow(0);
        Cell idH = header.createCell(0);
        Cell codiceAreaH = header.createCell(1);
        Cell nomeComuneH = header.createCell(2);
        Cell altezzaH = header.createCell(3);
        Cell diametroH = header.createCell(4);
        Cell latH = header.createCell(5);
        Cell lonH = header.createCell(6);
        Cell notaH = header.createCell(7);
        Cell noteTecnicheH = header.createCell(8);
        Cell tipoDiSuoloH = header.createCell(9);
        Cell dataImpiantoH = header.createCell(10);
        Cell dataUltimoAggiornamentoH = header.createCell(11);
        Cell oraUltimoAggiornamentoH = header.createCell(12);
        Cell dataPrimaRilevazioneH = header.createCell(13);
        Cell posizioneH = header.createCell(14);
        Cell eliminatoH = header.createCell(15);
        Cell idEssenzaH = header.createCell(16);
        Cell autoreH = header.createCell(17);
        Cell idComuneH = header.createCell(18);
        idH.setCellValue("Id");
        codiceAreaH.setCellValue("Codice area");
        nomeComuneH.setCellValue("Nome comune");
        altezzaH.setCellValue("Altezza");
        diametroH.setCellValue("Diametro");
        latH.setCellValue("Latitudine");
        lonH.setCellValue("Longitudine");
        notaH.setCellValue("Nota");
        noteTecnicheH.setCellValue("Note tecniche");
        tipoDiSuoloH.setCellValue("Tipo di suolo");
        dataImpiantoH.setCellValue("Data di impianto");
        dataUltimoAggiornamentoH.setCellValue("Data ultimo aggiornamento");
        oraUltimoAggiornamentoH.setCellValue("Ora ultimo aggiornamento");
        dataPrimaRilevazioneH.setCellValue("Data prima rilevazione");
        posizioneH.setCellValue("Posizione");
        eliminatoH.setCellValue("Eliminato");
        idEssenzaH.setCellValue("Id Essenza");
        autoreH.setCellValue("Autore");
        idComuneH.setCellValue("Id Comune");

        int rowCounter = 1;
        for (int i = 0; i < mainIds.size(); i++) {
            LinkedList<AlberoDTO> alberoDTOS = alberoCustomRepository.findByMain_IdOrderByDataUltimoAggiornamento(mainIds.get(i)).stream()
                .map(alberoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
            for (int j = 0; j < alberoDTOS.size(); j++) {
                Row row = sheet.createRow(rowCounter++);
                AlberoDTO alberoDTO = alberoDTOS.get(j);
                Cell id = row.createCell(0);
                Cell codiceArea = row.createCell(1);
                Cell nomeComune = row.createCell(2);
                Cell altezza = row.createCell(3);
                Cell diametro = row.createCell(4);
                Cell lat = row.createCell(5);
                Cell lon = row.createCell(6);
                Cell nota = row.createCell(7);
                Cell noteTecniche = row.createCell(8);
                Cell tipoDiSuolo = row.createCell(9);
                Cell dataImpianto = row.createCell(10);
                Cell dataUltimoAggiornamento = row.createCell(11);
                Cell oraUltimoAggiornamento = row.createCell(12);
                Cell dataPrimaRilevazione = row.createCell(13);
                Cell posizione = row.createCell(14);
                Cell eliminato = row.createCell(15);
                Cell idEssenza = row.createCell(16);
                Cell autore = row.createCell(17);
                Cell idComune = row.createCell(18);
                if (alberoDTO.getMainId() != null) {
                    id.setCellValue(alberoDTO.getMainId());
                }
                if (alberoDTO.getCodiceArea() != null) {
                    codiceArea.setCellValue(alberoDTO.getCodiceArea());
                }
                if (alberoDTO.getNomeComune() != null) {
                    nomeComune.setCellValue(alberoDTO.getNomeComune());
                }
                if (alberoDTO.getAltezza() != null) {
                    altezza.setCellValue(alberoDTO.getAltezza());
                }
                if (alberoDTO.getDiametro() != null) {
                    diametro.setCellValue(alberoDTO.getDiametro());
                }
                if (alberoDTO.getWkt() != null) {
                    try {
                        lat.setCellValue(wKTToLatLon(alberoDTO.getWkt()).getLat());
                        lon.setCellValue(wKTToLatLon(alberoDTO.getWkt()).getLon());
                    } catch (NumberFormatException n) {
                        log.error("Cannot format wkt: {}", alberoDTO.getWkt());
                    }

                }
                if (alberoDTO.getNota() != null) {
                    nota.setCellValue(alberoDTO.getNota());
                }
                if (alberoDTO.getNoteTecniche() != null) {
                    noteTecniche.setCellValue(alberoDTO.getNoteTecniche());
                }
                if (alberoDTO.getTipoDiSuolo() != null) {
                    tipoDiSuolo.setCellValue(alberoDTO.getTipoDiSuolo().toString());
                }
                if (alberoDTO.getDataImpianto() != null && alberoDTO.getDataImpianto().toString().length() >= 11) {
                    dataImpianto.setCellValue(alberoDTO.getDataImpianto().toString().substring(0, 10));
                }
                if (alberoDTO.getDataUltimoAggiornamento() != null) {
                    if (alberoDTO.getDataUltimoAggiornamento().toString().length() >= 20) {
                        dataUltimoAggiornamento.setCellValue(alberoDTO.getDataUltimoAggiornamento().toString().substring(0, 10));
                        oraUltimoAggiornamento.setCellValue(alberoDTO.getDataUltimoAggiornamento().toString().substring(11, 16));
                    }
                }
                if (alberoDTO.getDataPrimaRilevazione() != null && alberoDTO.getDataPrimaRilevazione().toString().length() >= 11) {
                    dataPrimaRilevazione.setCellValue(alberoDTO.getDataPrimaRilevazione().toString().substring(0, 10));
                }
                if (alberoDTO.getPosizione() != null) {
                    posizione.setCellValue(alberoDTO.getPosizione());
                }
                if (alberoDTO.isDeleted() != null) {
                    eliminato.setCellValue(alberoDTO.isDeleted());
                }
                if (alberoDTO.getEssenzaId() != null) {
                    idEssenza.setCellValue(alberoDTO.getEssenzaId());
                }
                if (alberoDTO.getModificatoDaEmail() != null) {
                    autore.setCellValue(alberoDTO.getModificatoDaEmail());
                }
                if (alberoDTO.getIdPianta() != null) {
                    idComune.setCellValue(alberoDTO.getIdPianta());
                }
            }
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            bos.close();
            return bos;
        } catch (Exception e) {
            log.error("Cannot create file", e);
            throw new IllegalArgumentException("Cannot create file");
        }

    }

    /**
     * Transform wkt to lat lon
     *
     * @param wkt
     * @return LatLon
     */
    public LatLon wKTToLatLon(String wkt) throws NumberFormatException {
        LatLon latLon = new LatLon();
        if (StringUtils.isBlank(wkt)
            || !StringUtils.contains(wkt, '.')
            || !StringUtils.contains(wkt, '(')
            || !StringUtils.contains(wkt, ')')
            || !StringUtils.contains(wkt, ' ')
            || !StringUtils.contains(wkt, "POINT")) {
            throw new NumberFormatException();
        }
        wkt = StringUtils.remove(wkt, '(');
        wkt = StringUtils.remove(wkt, ')');
        wkt = StringUtils.remove(wkt, "POINT");
        wkt = StringUtils.removeStart(wkt, " ");
        String[] latlonArr = wkt.split(" ");
        if (latlonArr.length < 2) {
            throw new NumberFormatException();
        }
        latLon.setLon(Double.parseDouble(latlonArr[0]));
        latLon.setLat(Double.parseDouble(latlonArr[1]));
        return latLon;

    }

    public Resource downloadAlberoHistoryAll() {
        List<Long> alberoList = alberoCustomRepository.findDistinctByMainModified();
        ByteArrayOutputStream bos = downloadAlberoHistory(alberoList);

        Resource resource = new ByteArrayResource(bos.toByteArray());
        return resource;
    }
}
