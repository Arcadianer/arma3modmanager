package manuel.fun.arma3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.java.Log;

@SpringBootApplication
@ComponentScan
@Log
public class Arma3workshopdownloaderApplication { 
ModManagerService service;
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Arma3workshopdownloaderApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
	}

	
		

	
}
