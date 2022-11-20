package cloud.nino.nino.service;

import cloud.nino.nino.domain.NinoUser;
import cloud.nino.nino.repository.NinoUserRepository;
import cloud.nino.nino.service.dto.NinoUserDTO;
import cloud.nino.nino.service.mapper.NinoUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing NinoUser.
 */
@Service
@Transactional
public class NinoUserService {

    private final Logger log = LoggerFactory.getLogger(NinoUserService.class);

    private final NinoUserRepository ninoUserRepository;

    private final NinoUserMapper ninoUserMapper;

    public NinoUserService(NinoUserRepository ninoUserRepository, NinoUserMapper ninoUserMapper) {
        this.ninoUserRepository = ninoUserRepository;
        this.ninoUserMapper = ninoUserMapper;
    }

    /**
     * Save a ninoUser.
     *
     * @param ninoUserDTO the entity to save
     * @return the persisted entity
     */
    public NinoUserDTO save(NinoUserDTO ninoUserDTO) {
        log.debug("Request to save NinoUser : {}", ninoUserDTO);
        NinoUser ninoUser = ninoUserMapper.toEntity(ninoUserDTO);
        ninoUser = ninoUserRepository.save(ninoUser);
        return ninoUserMapper.toDto(ninoUser);
    }

    /**
     * Get all the ninoUsers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NinoUserDTO> findAll() {
        log.debug("Request to get all NinoUsers");
        return ninoUserRepository.findAll().stream()
            .map(ninoUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ninoUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NinoUserDTO> findOne(Long id) {
        log.debug("Request to get NinoUser : {}", id);
        return ninoUserRepository.findById(id)
            .map(ninoUserMapper::toDto);
    }

    /**
     * Delete the ninoUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NinoUser : {}", id);
        ninoUserRepository.deleteById(id);
    }
}
