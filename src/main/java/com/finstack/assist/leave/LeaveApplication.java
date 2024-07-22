package com.finstack.assist.leave;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Slf4j
@SpringBootApplication
public class LeaveApplication {

    public static void main(String[] args)
    {
        log.info("in Leave application");
        SpringApplication.run(LeaveApplication.class, args);
    }
    
    

@Bean
public OpenAPI myOpenAPI() {
	Contact contact = new Contact();
	contact.setEmail("raghu@finstack-tech.com");
	contact.setName("raghu dodla");
	contact.setUrl("https://assist-leave-api.com");

	Server localServer = new Server();
	localServer.setUrl("http://localhost:8001");
	localServer.setDescription("Server URL in Local environment");

	Server productionServer = new Server();
	productionServer.setUrl("http://localhost:8001");
	productionServer.setDescription("Server URL in Production environment");
		
	License mitLicense = new License()
			.name("MIT License")
			.url("https://choosealicense.com/licenses/mit/");

	Info info = new Info()
			.title("ASSIST LEAVE API")
			.contact(contact)
			.version("1.0")
			.description("This API exposes endpoints for users to manage their leaves.")
			.termsOfService("https://my-awesome-api.com/terms")
			.license(mitLicense);

	return new OpenAPI()
			.info(info)
			.servers(List.of(localServer, productionServer));
}

}
