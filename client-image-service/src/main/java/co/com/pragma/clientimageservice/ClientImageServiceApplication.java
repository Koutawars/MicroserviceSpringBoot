package co.com.pragma.clientimageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientImageServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientImageServiceApplication.class, args);
	}
}
