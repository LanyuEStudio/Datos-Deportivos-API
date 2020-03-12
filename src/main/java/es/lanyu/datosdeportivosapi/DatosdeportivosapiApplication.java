package es.lanyu.datosdeportivosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.lanyu.Test;

//@SpringBootApplication
public class DatosdeportivosapiApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DatosdeportivosapiApplication.class, args);
	    ConfigurableApplicationContext context = 
	            new ClassPathXmlApplicationContext( 
	                                                new String[]{"config-scan.xml", "config.xml"}
	                                            );
	    Test test = new Test();
	    test.init();
	    
//	    test = context.getBean(Test.class);
//	    test = context.getBean("test", Test.class);
//	    test = context.getBean("anotacion", Test.class);
	    test = context.getBean("config", Test.class);
	    test.init();
	    
	    context.close();
	}

}
