package manuel.fun.arma3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;

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
		System.out.println("Starting log...");
		log.info("Performing preflight check");
		
		
		//application.property check
		log.info("Checking application.properties");
		File appprop=new File("application.properties");
		if(!appprop.exists()) {
			try {
				throw new FileNotFoundException("application.properties not found");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				log.log(Level.SEVERE, "application.properties not found");
				//System.exit(1);
			}
		}
	
		
		log.info("Check complete");
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Arma3workshopdownloaderApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
	}

	
		

	
}
