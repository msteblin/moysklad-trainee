package io.github.msteblin.Marketplace;

import io.github.msteblin.MoySkladAPI.APIClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@SuppressWarnings("unused")
public class APIConfig {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    APIClient apiClient(String msToken) {
        return new APIClient(msToken);
    }
}
