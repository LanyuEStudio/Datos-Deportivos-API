package es.lanyu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Test {
	private final Logger log = LoggerFactory.getLogger(Test.class);

    protected String testString = "String por defecto";
    
    // getters y setters necesarios para configurar el bean
    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    // metodo que se llamara al iniciar opcionalmente
    public void init() {
        log.info("Llamado init(): {}", testString);
    }
}
