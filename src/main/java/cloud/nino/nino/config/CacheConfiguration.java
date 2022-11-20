package cloud.nino.nino.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(cloud.nino.nino.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(cloud.nino.nino.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Visit.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Visit.class.getName() + ".stops", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Stop.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Albero.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Essenza.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Privacy.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.EssenzaAudit.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.NinoUser.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.NinoUser.class.getName() + ".administeredLocations", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Poi.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.EventAndLocation.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.EventAndLocation.class.getName() + ".adminUsers", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.EventAndLocation.class.getName() + ".pois", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.NinoUser.class.getName() + ".administeredEventsAndLocations", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Poi.class.getName() + ".eventsAndlocations", jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.AlberoVisit.class.getName(), jcacheConfiguration);
            cm.createCache(cloud.nino.nino.domain.Email.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
