package co.com.pragma.clientservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "co.com.pragma.clientservice")
@EnableDiscoveryClient
@EnableFeignClients("co.com.pragma.clientservice")
@EnableJpaRepositories("co.com.pragma.clientservice")
@ComponentScan(basePackages = {"co.com.pragma.clientservice"})
@EntityScan("co.com.pragma.clientservice")
public class ClientServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
