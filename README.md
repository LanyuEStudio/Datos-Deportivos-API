# Crea tu API (Datos Deportivos API)
Este repositorio sirve para realizar el código del curso **["Crea tu API"](https://hijosdelspectrum.blogspot.com/p/spring-framework-crear-una-api.html)**

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

Para continuar desde aquí hay que hacer clone de este repositorio y situarse en el commit correspondiente a la [release v0.0.2](https://github.com/LanyuEStudio/spring-ejemplo-contenedor/releases/tag/v0.0.2). También hay que hacer clone a la misma carpeta de [datos-deportivos](https://github.com/LanyuEStudio/datos-deportivos) porque es multiproyecto y seguramente se toquen cosas de ese proyecto para comparar resultados. Una vez se tiene eso se ejecuta la tarea `bootRun` desde este proyecto para ver que todas las dependencias se han resuelto. Finalmente se prueba desde el IDE que se use que detecta los tipos del proyecto datos-deportivos (por ejemplo se ve `Partido`, `Participante`, `Gol`, etc... uno cualquiera es suficiente).

Los puntos que se van a ver son:
1. [Entidades (POJO `@Entity`) y Repositorios (`@Repository`)](https://hijosdelspectrum.blogspot.com/2020/03/entidades-y-repositorios-con-jpa.html)
1. ORM por XML(I)
   1. [De POJO simple](https://www.hijosdelspectrum.com/2020/03/orm-por-xml-de-pojo-simple.html)
   1. [Con Herencia](https://www.hijosdelspectrum.com/2020/03/orm-por-xml-de-clases-con-herencia.html)
   1. Con Relación
      1. [`@OneToMany`](https://www.hijosdelspectrum.com/2020/03/orm-por-xml-con-relaciones-onetomany.html)
1. [Data REST](https://www.hijosdelspectrum.com/2020/03/la-potencia-de-spring-data-rest.html) (endpoints HATEOAS)
1. Personalizar payload con Jackson
1. ORM por XML (II)
   1. Herencia con varias subclases ([SINGLE TABLE](https://www.hijosdelspectrum.com/2020/03/orm-por-xml-guardar-subclases-en.html))
1. Inyectar bean en objetos no gestionados
   1. En [entidades leídas desde BD](https://www.hijosdelspectrum.com/2020/04/inyectar-un-bean-una-entidad-leida.html) (`Events` y `Listeners` en JPA)
   1. En objetos [desde las peticiones HTTP](https://www.hijosdelspectrum.com/2020/04/inyectar-un-bean-un-restresource.html) (`@JsonComponent`)
1. Añadir código personalizado
   1. [Personalizar endpoints con `@RestResource`](https://www.hijosdelspectrum.com/2020/04/personalizar-endpoints-con-restresource.html)
   1. Añadir [método personalizado](https://www.hijosdelspectrum.com/2020/04/anadir-metodo-personalizado-en.html) a repositorio
   1. [Exponer método con `@RepositoryRestController`](https://www.hijosdelspectrum.com/2020/04/exponer-metodo-con-repositoryrestcontro.html)
   1. Añadir [link a endpoint `/search`](https://www.hijosdelspectrum.com/2020/05/anadir-link-resourcessearch.html)
   1. [Ruta con `@PathVariable`](https://www.hijosdelspectrum.com/2020/05/rutas-con-pathvariable.html)
   1. [Detección automática de links](https://www.hijosdelspectrum.com/2020/05/codigo-util-clase-configuracionrest.html) con `ConfiguracionRest`


## Despliegue en local

Antes de arrancar la API se debe arrancar la BD. Se usa [H2](https://h2database.com/html/main.html) en modo servidor.
Se puede levantar la BD usando el archivo `h2-version.jar`. Comprobar que tenemos acceso a la consola de H2 y que está corriendo.

Las propiedades de conexión son las que vienen por defecto:
> url=jdbc:h2:tcp://localhost/~/test  
username=sa  
(sin password)

Entonces ejecutar la API con la última [release](https://github.com/LanyuEStudio/Datos-Deportivos-API/releases).

Mejor desde una consola para ver el log `java -jar datosdeportivosapi-VERSION.jar`.

Para conseguir este `.jar` lo mejor es [seguir los pasos indicados en el blog](https://www.hijosdelspectrum.com/2020/05/empaquetar-la-api-en-un-ficherojar.html).

Puedes ver la [documentación de la API en Postman](https://documenter.getpostman.com/view/9800655/Szme3dFP?version=latest) y usar el [deploy en Heroku](https://github.com/LanyuEStudio/Datos-Deportivos-API/deployments) para probarlo directamente. Para [Heroku](https://www.heroku.com/) tener en cuenta que:
1. El servicio hiberna despúes 30 minutos sin usar y tardará un poco más en la primera petición
1. Usar el environment `Heroku` de Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/a390ad12b3ffc8e3ef4f)