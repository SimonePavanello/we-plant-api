package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.AlberoVisit;
import cloud.nino.nino.domain.Authority;
import cloud.nino.nino.repository.custom.AlberoVisitCustomRepository;
import cloud.nino.nino.repository.custom.AuthorityCustomRepository;
import cloud.nino.nino.security.AuthoritiesConstants;
import cloud.nino.nino.service.dto.AlberoVisitDTO;
import cloud.nino.nino.service.dto.custom.AlberoVisitEssenza;
import cloud.nino.nino.service.mapper.AlberoVisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AlberoVisit.
 */
@Service
@Transactional
public class AlberoVisitCustomService {

    private final Logger log = LoggerFactory.getLogger(AlberoVisitCustomService.class);

    private final AlberoVisitCustomRepository alberoVisitCustomRepository;

    private final AlberoVisitMapper alberoVisitMapper;

    private final AuthorityCustomRepository authorityCustomRepository;

    public AlberoVisitCustomService(AlberoVisitCustomRepository alberoVisitRepository, AlberoVisitMapper alberoVisitMapper, AuthorityCustomRepository authorityCustomRepository) {
        this.alberoVisitCustomRepository = alberoVisitRepository;
        this.alberoVisitMapper = alberoVisitMapper;
        this.authorityCustomRepository = authorityCustomRepository;
    }

    /**
     * Save a alberoVisit.
     *
     * @param alberoVisitDTO the entity to save
     * @return the persisted entity
     */
    public AlberoVisitDTO save(AlberoVisitDTO alberoVisitDTO) {
        log.debug("Request to save AlberoVisit : {}", alberoVisitDTO);
        AlberoVisit alberoVisit = alberoVisitMapper.toEntity(alberoVisitDTO);
        alberoVisit = alberoVisitCustomRepository.save(alberoVisit);
        return alberoVisitMapper.toDto(alberoVisit);
    }

    /**
     * Get all the alberoVisits.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlberoVisitDTO> findAll() {
        log.debug("Request to get all AlberoVisits");
        return alberoVisitCustomRepository.findAll().stream()
            .map(alberoVisitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get all the alberoVisits.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlberoVisitEssenza> findAllByAutogeneratedUser() {
        log.debug("Request to get all AlberoVisits");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityCustomRepository.findFirstByName(AuthoritiesConstants.AUTOGENERATED).get());
        List<AlberoVisit> alberoVisits = alberoVisitCustomRepository.findAllByUser_AuthoritiesIn(authorities);
        Map<String, LinkedList<AlberoVisitDTO>> essenzaAlberoVisits = new HashMap<>();
        alberoVisits.forEach(alberoVisit -> {
            if (essenzaAlberoVisits.containsKey(alberoVisit.getAlbero().getNomeComune())) {
                essenzaAlberoVisits.get(alberoVisit.getAlbero().getNomeComune()).add(alberoVisitMapper.toDto(alberoVisit));
            } else {
                LinkedList<AlberoVisitDTO> linkedList = new LinkedList<>();
                linkedList.add(alberoVisitMapper.toDto(alberoVisit));
                essenzaAlberoVisits.put(alberoVisit.getAlbero().getNomeComune(), linkedList);
            }
        });
        return essenzaAlberoVisits.entrySet().stream().map(entry -> {
            return new AlberoVisitEssenza(entry.getKey(), entry.getValue());
        }).collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one alberoVisit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AlberoVisitDTO> findOne(Long id) {
        log.debug("Request to get AlberoVisit : {}", id);
        return alberoVisitCustomRepository.findById(id)
            .map(alberoVisitMapper::toDto);
    }

    /**
     * Delete the alberoVisit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlberoVisit : {}", id);
        alberoVisitCustomRepository.deleteById(id);
    }
}
