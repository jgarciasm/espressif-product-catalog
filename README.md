# Espressif Product Catalog

Eclipse plugins that provides the following functionalities:
- Submit Espressif products to a catalog (JSON file).
- Search for and view all Espressif product catalog.

## It has the additional following functionalities:
- Validation of empty input data.
- Validation of existing products.
- Complete text search in real time while typing.
- Sort by every column.
- Remove product from the catalog.
- Update product information directly in the table.
- Get texts from bundle for future internationalization.

# Documentation

## _Prerequisites_ 📋

* Eclipse IDE
* JDK 11

## _Getting started_ 🚀

If you want to see the code, the fastest route is to clone the repository:

```
git clone https://github.com/jgarciasm/ssv-api.git PROJECT_NAME
```
_Replace PROJECT_NAME with the name of your preference._

Then you can import the 2 projects (nombres) to Eclipse.

## _Structure of the project_ 🏬

The project contains:

- a pom.xml file

- a main verticle file (src/main/java/com.bugsyteam.verticles/MainVerticle.java)

- a traditional main class (src/main/java/com.bugsyteam.main/VertxApplication.java)

- unit test cases (src/main/test/com.bugsyteam.verticles/MainVerticleTest.java)

- collection of utility classes (src/main/java/com.bugsyteam.utils)

- collection of endpoint call handlers (src/main/java/com.bugsyteam.endpoints)

- a Swagger specification to document the API (postmanTests/ssv-api.postman_collection.json)

- a Postman collection to test the API (src/main/resources/webroot/node_modules/swagger-ui-dist/ssv_api_specification.yml)

_All the classes and methods are well documented for study purposes._


## _Configuration and Deployment of the Plugins_ ⚙️
- copiar el fichero si se desea
- copiar los compilados en la carpeta de plugins de eclipse
- ejecutar eclipse
- mostrar las vistas


## _Build with_ 🛠️

* [eclipse-committers-2020-09-R-win32-x86_64](https://www.eclipse.org/eclipseide/) - Development IDE
* [OPEN_JDK 11](https://www.eclipse.org/eclipseide/) - Development IDE


## _Authors_ ✒️

* **Jonad García San Martín** - *Development and Documentation* - [jgarciasm89](jgarciasm89@gmail.com)


---
