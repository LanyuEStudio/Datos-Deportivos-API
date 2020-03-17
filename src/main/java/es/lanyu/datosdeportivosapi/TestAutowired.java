package es.lanyu.datosdeportivosapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import es.lanyu.Test;

@Component("autowired")
public class TestAutowired extends Test {

	private final Logger log = LoggerFactory.getLogger(TestAutowired.class);
	
	@Autowired
	@Qualifier("test")
	Test testInyectado;
	
	@Autowired
	private void setTestInyectado(//@Qualifier("config") Test testPorSetter,
								  @Qualifier("test") Test testPorSetter2,
								  Test testPrincipal) {
		testInyectado = testPrincipal;
//		testInyectado = new Test();
//		testInyectado.setTestString(testPorSetter.getTestString()
//				+ " y " + testPorSetter2.getTestString());
	}
	
	@Autowired(required=false)
	public TestAutowired() {}
	
	@Autowired(required=false)
	public TestAutowired(es.lanyu.datosdeportivosapi.Test testPorConstructor) {
		testInyectado = testPorConstructor;
	}
	
	@Override
	public void init() {
		log.warn("Texto de bean inyectada: {}", testInyectado.getTestString());
	}
}
