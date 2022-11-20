package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Essenza;
import cloud.nino.nino.repository.EssenzaRepository;
import cloud.nino.nino.repository.custom.AlberoCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static java.lang.Math.toIntExact;

/**
 * Created by dawit on 28/04/2019.
 */
@Service
@Transactional

public class AlberoMigrationService {
    private final Logger log = LoggerFactory.getLogger(AlberoMigrationService.class);
    private final AlberoCustomRepository alberoCustomRepository;
    private final EssenzaRepository essenzaRepository;

    public AlberoMigrationService(AlberoCustomRepository alberoCustomRepository, EssenzaRepository essenzaRepository) {
        this.alberoCustomRepository = alberoCustomRepository;
        this.essenzaRepository = essenzaRepository;
    }

    void populateAlberoEssenzaRelationField() {
        log.info("Populate albero essenza relation field schedule running");
        long totalAlberi = alberoCustomRepository.countByEssenzaIsNull();
        Long maxPage = totalAlberi / 100;
        log.info("The count of alberi is " + totalAlberi);
        log.info("Max page " + maxPage);
        IntStream.range(0, toIntExact(maxPage)).forEach(page -> {
            log.info("Current page " + page);
            Pageable pageable = PageRequest.of(page, 100);
            alberoCustomRepository.findAllByEssenzaIsNull(pageable).forEach(albero -> {
                Essenza essenzaTmp = new Essenza();
                essenzaTmp.setNomeComune(albero.getNomeComune().toUpperCase());
                Example<Essenza> essenzaExample = Example.of(essenzaTmp);
                essenzaRepository.findAll(essenzaExample).forEach(essenza ->
                    {
                        log.info("Found " + essenza);
                        albero.setEssenza(essenza);
                        alberoCustomRepository.save(albero);
                    }
                );


            });
        });
        log.info("Migration finished");

    }
}
