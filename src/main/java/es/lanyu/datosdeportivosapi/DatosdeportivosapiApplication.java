package es.lanyu.datosdeportivosapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"logging.properties"})
public class DatosdeportivosapiApplication {
	private static final Logger log = LoggerFactory.getLogger(DatosdeportivosapiApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(DatosdeportivosapiApplication.class, args);
	    
	    context.close();
	}

}
