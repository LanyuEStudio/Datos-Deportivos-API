# Spring Contenedor Dependencias
Ejemplo de las distintas formas de inyectar dependencias sin usar SpringBoot.

## Contenido
Se generó un proyecto [gradle](https://gradle.org/) usando [Spring Initialzr](https://start.spring.io/) con la siguiente configuración:
* Project: Gradle
* Language: Java
* Group: `es.lanyu`
* Artifact: datosdeportivosapi
* Dependencies: Spring Data JPA, H2 Database, PostgreSQL Driver, Rest Repositories
* Resto de las opciones por defecto
Se puede disponer de toda esta configuración usando la [configuración compartida](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.5.RELEASE&packaging=jar&jvmVersion=1.8&groupId=es.lanyu&artifactId=datosdeportivosapi&name=datosdeportivosapi&description=Proyecto%20para%20generar%20API%20REST%20de%20datos%20deportivos&packageName=es.lanyu.datosdeportivosapi&dependencies=data-jpa,h2,postgresql,data-rest). Para descargar el proyecto usar el botón `Generate - Ctrl + ENTER`.

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
   