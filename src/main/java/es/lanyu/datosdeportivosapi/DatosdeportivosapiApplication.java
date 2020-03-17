package es.lanyu.datosdeportivosapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import es.lanyu.Test;
import es.lanyu.config.JavaConfig;

@SpringBootApplication
@ImportResource({"config.xml"})
@Import({JavaConfig.class})
@PropertySource({"application2.properties"})
public class DatosdeportivosapiApplication {
	private static final Logger log = LoggerFactory.getLogger(DatosdeportivosapiApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(DatosdeportivosapiApplication.class, args);
	    
		Test test;
	    String nombre = "anotacion";//"test";//"config";//
//	    test = context.getBean(Test.class);
	    test = context.getBean(nombre, Test.class);
	    test.init();
	    log.debug("Recuperado bean \"{}\" del tipo {}", nombre, test.getClass().getName());
	    
	    context.close();
	}

}
