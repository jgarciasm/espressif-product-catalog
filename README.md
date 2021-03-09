# Espressif Product Catalog

Eclipse plugins that provides the following functionalities:
- Saving Espressif products to a catalog (JSON file). 
- Searching for and view all Espressif product catalog.

## Additional functionalities:
- Validation of empty input data.
- Validation of existing products.
- Complete text search in real time while typing.
- Sort by every column.
- Remove products from the catalog.
- Updating product information directly in the table.
- Get texts from bundle for future internationalization.

# Documentation

## _Prerequisites_ 📋

* Eclipse IDE
* JDK 11

## _Getting started_ 🚀

If you want to see the code, the fastest route is to clone the repository:

```
git clone https://github.com/jgarciasm/espressif-product-catalog.git PROJECT_NAME
```
_Replace PROJECT_NAME with the name of your preference._

Then you can import the 2 projects to Eclipse.

## _Structure of the project_ 🏬

The project contains:

- _/com.bugsyteam.plugins.espressif-prod_: Eclipse project with the plugin to submit new products to Espressif Product catalog.
- _/com.bugsyteam.plugins.espressif-prods_: Eclipse project with the plugin to manage the Espressif Product catalog.
- _/compiled_: directory with the last version of the compiled plugins ready to install in Eclipse.
- _resources/products.json_: datasource for test purposes.


## _Configuration and Deployment of the Plugins_ ⚙️
- Copy the compiled plugins inside the plugins directory of your Eclipse distribution (Eclipse root directory/plugins)
    - com.bugsyteam.plugins.espressif-prod_1.0.0.202012121141.jar
    -  com.bugsyteam.plugins.espressif-prods_1.0.0.202012121141.jar 
- Launch Eclipse.
- Go to menu Window->Show View->Other...  in your runtime Eclipse and select the plugin you want to show inside the Espressif category. Do this operation with every plugin.
- If you want to test the plugins with a test product catalog based in Espressif real products you has to copy "resources/products.json" inside the temporal folder ("espressif-prod") created for the plugins in the temporary directory used by the Java Virtual Machine (JVM). Commonly, in Windows, the default temporary folder is  "C:\Temp",  "%Windows%\Temp", or a temporary directory per user in  "Local Settings\Temp"  (this location is usually controlled via the  "TEMP"  environment variable). In Linux/Unix, the global temporary directories are /tmp"  and  "/var/tmp" . The preceding line of code will return the default location, depending on the operating system. For example:
    - C:\Users\USER\AppData\Local\Temp\espressif-prod (Windows)


## _Build with_ 🛠️

* [eclipse-committers-2020-09-R-win32-x86_64](https://www.eclipse.org/eclipseide/) - Development IDE
* [openjdk-11.0.2_windows-x64_bin](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - Development IDE


## _Authors_ ✒️

* **Jonad García San Martín** - *Development and Documentation* - jgarciasm89@gmail.com


---
