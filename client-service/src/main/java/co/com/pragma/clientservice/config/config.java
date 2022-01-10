package co.com.pragma.clientservice.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerFactory;


@Configuration
public class config {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Customizer<HystrixCircuitBreakerFactory> defaultConfig() {
        return factory -> factory.configureDefault(id -> HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)));
    }
}
