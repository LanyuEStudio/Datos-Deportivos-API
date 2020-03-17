package es.lanyu.datosdeportivosapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.lanyu.Test;

@Component("autowired")
public class TestAutowired extends Test {

	private final Logger log = LoggerFactory.getLogger(TestAutowired.class);

	Test testInyectado;

	public TestAutowired() {}
	
	@Autowired
	public TestAutowired(es.lanyu.datosdeportivosapi.Test testPorConstructor) {
		testInyectado = testPorConstructor;
	}
	
	@Override
	public void init() {
		log.warn("Texto de bean inyectada: {}", testInyectado.getTestString());
	}
}
