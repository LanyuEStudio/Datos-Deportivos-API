package es.lanyu.datosdeportivosapi;

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

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(DatosdeportivosapiApplication.class, args);
	    
		Test test;
	    
//	    test = context.getBean(Test.class);
//	    test = context.getBean("test", Test.class);
	    test = context.getBean("anotacion", Test.class);
//	    test = context.getBean("config", Test.class);
	    test.init();
	    System.out.println(test.getClass().getName());
	    
	    context.close();
	}

}
