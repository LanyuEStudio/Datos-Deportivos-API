package es.lanyu.datosdeportivosapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("anotacion")
public class Test extends es.lanyu.Test{
	
	@Value("${datosdeportivos.testString}")
	String stringPropiedad;
	
//    public Test() {
//        testString = stringPropiedad;// No se tiene acceso en el constructor
//    }
    
    public Test(@Value("${valor.parametro}")// Para usarlo en constructor tiene que ser por parametro
    			String stringPropiedad) { // Usa el constructor sin parametro si tambien existe
        System.out.println(stringPropiedad);
    }
    
    public void init() {
    	testString = stringPropiedad;// System.out.println(stringPropiedad);
    	super.init();
    }
}
