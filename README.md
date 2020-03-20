# Crea tu API (Datos Deportivos API)
Este repositorio sirve para controlar el código del curso **["Crea tu API"](https://hijosdelspectrum.blogspot.com/p/spring-framework-crear-una-api.html)**

## Contenido
Se generó un proyecto [gradle](https://gradle.org/) usando [Spring Initialzr](https://start.spring.io/) con la siguiente configuración:
* Project: Gradle
* Language: Java
* Group: `es.lanyu`
* Artifact: datosdeportivosapi
* Dependencies: Spring Data JPA, H2 Database, PostgreSQL Driver, Rest Repositories
* Resto de las opciones por defecto
Se puede disponer de toda esta configuración usando la [configuración compartida](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.5.RELEASE&packaging=jar&jvmVersion=1.8&groupId=es.lanyu&artifactId=datosdeportivosapi&name=datosdeportivosapi&description=Proyecto%20para%20generar%20API%20REST%20de%20datos%20deportivos&packageName=es.lanyu.datosdeportivosapi&dependencies=data-jpa,h2,postgresql,data-rest). Para descargar el proyecto usar el botón `Generate - Ctrl + ENTER`.

### Spring Contenedor Dependencias (Spring básico)
Ejemplo de las distintas formas de inyectar dependencias sin usar SpringBoot.

Después se siguieron los siguiente pasos:
1. Creación de bean por XML
1. Escaneando componentes con anotaciones `@Component`
1. Sobreescribiendo `@Component` con XML
   * Se puede invertir el orden de los ficheros y ver los cambios en el log

1. Cambiando paquete base para escanear
   * Ya no se sobreescribe. No detecta fuera del paquete base
1. Desambiguar varias beans por identificador
1. `@Bean` en `@Configuration`
   * Ver cómo se crean todas las dependencias de las tres formas
1. Refactorizar a Spring Boot
1. Propiedades y `@Value`
1. Logs
1. Inyección de Dependencias: `@Autowired`

El código hasta este punto está etiquetado en la [release v0.0.1](https://github.com/LanyuEStudio/spring-ejemplo-contenedor/releases/tag/v0.0.1).

### Pasando a supuesto de Datos Deportivos
A continuación de lo anterior se borra todo el código de ejemplo de Spring básico y se añade la dependencia [datos-deportivos](https://github.com/LanyuEStudio/datos-deportivos) para continuar con persistencia con JPA, Spring Data REST para ofrecer una API RESTful HATEOAS, formateo de salida con Jackson y Entity Listener. Se ven distintos ejemplos en la forma de hacerlo (XML, Configuración Java y Anotaciones).

Para continuar desde aquí hay que hacer clone de este repositorio y situarse en el commit correspondiente a la [release v0.0.2](https://github.com/LanyuEStudio/spring-ejemplo-contenedor/releases/tag/v0.0.2). También hay que hacer clone a la misma carpeta de [datos-deportivos](https://github.com/LanyuEStudio/datos-deportivos) porque es multiproyecto y seguramente se toquen cosas de ese proyecto para comparar resultados. Una vez se tiene eso se ejecuta la tarea `bootRun` desde este proyecto para ver que todas las dependencias se han resuelto. Finalmente se prueba desde el IDE que se use que detecta los tipos del proyecto datos-deportivos (por ejemplo se ve Partido, Participante, Gol, etc... uno cualquiera es suficiente).

Los puntos que se van a ver son:
1. Entidades (POJO @Entity) y Repositorios (@Repository)
1. ORM por XML
1. Data REST (endpoints HATEOAS)
1. Personalizar payload con Jackson
1. Callbacks y Listeners en JPA
