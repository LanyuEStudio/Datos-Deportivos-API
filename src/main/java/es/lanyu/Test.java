package es.lanyu;

public class Test {

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
        System.out.println(testString);
    }
}
