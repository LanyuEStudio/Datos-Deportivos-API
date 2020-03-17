package es.lanyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.lanyu.Test;

@Configuration
public class JavaConfig {

    @Bean(name="config")
    public Test miTest() {
        Test test = new Test() {
            int llamadas = 0;
            
            @Override
            public void init() {
                System.out.printf("Llamado %s veces", ++llamadas);
                System.out.println();
            }
            
        };
        test.setTestString("Por configuracion");
        test.init();
        
        return test;
    }
}
