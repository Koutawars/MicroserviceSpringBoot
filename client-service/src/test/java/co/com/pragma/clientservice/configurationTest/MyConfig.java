package co.com.pragma.clientservice.configurationTest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MyConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
