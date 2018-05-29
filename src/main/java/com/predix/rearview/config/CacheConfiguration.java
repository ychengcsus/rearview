package edu.four04.sscapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Appointment.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.AppointmentColor.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.AppointmentType.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Semester.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Semester.class.getName() + ".appointments", jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Note.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Notebook.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Notebook.class.getName() + ".notes", jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.Assets.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.GeoLocationInformation.class.getName(), jcacheConfiguration);
            cm.createCache(edu.four04.sscapp.domain.TrafficMeasurements.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
