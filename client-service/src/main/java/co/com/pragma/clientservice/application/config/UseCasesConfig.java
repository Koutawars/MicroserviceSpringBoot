package co.com.pragma.clientservice.application.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(
        basePackages = "co.com.pragma.clientservice.domain.usecase",
        includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
        useDefaultFilters = false)
public class UseCasesConfig {}
