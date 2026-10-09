
# Biblioteca Escolar

## Descripción del proyecto

Biblioteca Escolar es una aplicación de escritorio desarrollada en Java para apoyar la gestión de una biblioteca escolar.

El sistema permite administrar libros y estudiantes, registrar préstamos y devoluciones, consultar la disponibilidad de ejemplares y generar reportes sobre los préstamos realizados.

Este proyecto fue desarrollado como parte de la evaluación final de la asignatura Desarrollo Orientado a Objetos II de Duoc UC, aplicando los contenidos trabajados durante el curso.

## Funcionalidades

- Inicio de sesión con perfiles de bibliotecario y estudiante
- Registro, consulta, modificación y eliminación de libros
- Registro, consulta, modificación y eliminación de estudiantes
- Registro de préstamos y devoluciones
- Actualización del stock disponible al prestar o devolver un libro
- Validación de disponibilidad antes de realizar un préstamo
- Prevención de devoluciones duplicadas
- Consulta de historial de préstamos
- Reportes de libros más prestados y préstamos activos

Las opciones disponibles en el menú dependen del perfil del usuario que inicia sesión.

## Tecnologías utilizadas

- Java
- Java Swing para las ventanas de la aplicación
- MySQL para almacenar los datos
- JDBC para conectar Java con la base de datos
- Maven para administrar las dependencias
- IntelliJ IDEA como entorno de desarrollo

## Organización del proyecto

El código está organizado en paquetes para separar las distintas responsabilidades:

- `model`: contiene las clases que representan los datos y las entidades del sistema
- `view`: contiene las ventanas e interfaces gráficas
- `controller`: comunica las acciones de las ventanas con la lógica del sistema
- `dao`: contiene las operaciones de acceso a la base de datos
- `util`: contiene la configuración de conexión a MySQL
- `main`: contiene el punto de entrada de la aplicación

Se utiliza el patrón MVC junto con clases DAO para mantener separadas la interfaz, la lógica y el acceso a los datos.

También se aplican conceptos de programación orientada a objetos, como herencia, abstracción, interfaces y polimorfismo.

En el registro de préstamos y devoluciones se utiliza `synchronized` para controlar el acceso simultáneo a las operaciones. Además, se emplean transacciones JDBC para mantener relacionados los cambios del préstamo y del stock.

## Base de datos

El proyecto utiliza una base de datos MySQL llamada `biblioteca`.

En la carpeta `sql` se incluyen los archivos necesarios para crear las tablas y cargar los datos iniciales:

1. `PRY2203_EFT_S9_Script_crea_tablas_biblioteca.sql`
2. `PRY2203_EFT_S9_Script_poblado_tablas_biblioteca.sql`

Estos scripts deben ejecutarse en ese orden.

## Cómo ejecutar el proyecto

1. Tener instalados Java, MySQL y un entorno compatible con Maven.
2. Abrir el proyecto en IntelliJ IDEA.
3. Ejecutar los scripts de la carpeta `sql` en MySQL Workbench.
4. Configurar la variable de entorno `BIBLIOTECA_DB_PASSWORD` con la contraseña del usuario de MySQL.
5. Revisar que los datos de conexión de `DatabaseConnection.java` correspondan a la instalación local de MySQL.
6. Ejecutar la clase `main.Main`.

Para configurar la contraseña desde IntelliJ IDEA, se puede ingresar a **Run → Edit Configurations → Environment variables** y agregar la variable indicada.

La contraseña de MySQL no se incluye directamente en el código fuente.

## Ejecución del archivo JAR

Se dispone de una versión ejecutable de la aplicación llamada `Biblioteca_Escolar_FINAL.jar`, preparada para incluirse en el paquete de entrega.

Para ejecutarla:

1. Tener Java instalado y MySQL en ejecución.
2. Crear y poblar la base de datos `biblioteca` mediante los scripts SQL incluidos.
3. Abrir PowerShell en la carpeta donde se encuentra `Biblioteca_Escolar_FINAL.jar`.
4. Ejecutar los siguientes comandos:

```powershell
$env:BIBLIOTECA_DB_PASSWORD = Read-Host "Contraseña de MySQL"
java -jar ".\Biblioteca_Escolar_FINAL.jar"
```

El primer comando solicita la contraseña de MySQL y configura la variable de entorno para esa sesión de PowerShell. El segundo inicia la aplicación.

El archivo JAR se incluirá en el ZIP de entrega; no forma parte de los archivos publicados en el repositorio GitHub.

## Pruebas realizadas

Durante el desarrollo se realizaron pruebas de las principales funcionalidades:

- Inicio de sesión con distintos perfiles
- Registro y consulta de libros y estudiantes
- Validación de registros duplicados
- Registro de préstamos con descuento de stock
- Registro de devoluciones con recuperación de stock
- Intento de devolución de un préstamo ya devuelto
- Consulta de los reportes disponibles

Estas pruebas permitieron revisar el funcionamiento de las operaciones y corregir problemas detectados durante el desarrollo.

## Autor

Proyecto académico desarrollado para la asignatura Desarrollo Orientado a Objetos II.

Duoc UC — Analista Programador Computacional.
