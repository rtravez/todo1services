# Backend
# Crear la base de datos en Mysql o H2"
CREATE DATABASE hulkstore
#
Si prefiere cambiar las configuraciones en el archivo `application.properties `

# Spring Tools
El proyecto esta generado con Spring Tools 4.13.0 y Java 11
Realizar el  Cleand and Build al proyecto backed y automáticamente se crea el esquema de base de datos con datos de prueba.

# Frontend
El proyecto fue generado con  [Angular CLI](https://github.com/angular/angular-cli) version 12.2.12
Ejecutar `npm install` para descargar todas las dependencias del proyecto.
#
Los datos de pruebas son los siguientes:
#
Usuario admin
Contraseña: 12345
#
Usuario rene
Contrasena: 12345
#
## Development server
Ejecutar en la consola de proyecto `ng serve -o` y navegar `http://localhost:4200/`.

# Ejercicio
Objetivo: Se espera que el candidato ofrezca una solución orientada a objetos al problema. Queda
abierta la posibilidad de que el candidato proponga una solución con las restricciones del paradigma
funcional.
Enunciado: “Hulk Store”
La empresa Todo1 ha decido realizar un emprendimiento, el cual consiste en la creación de una tienda
de productos para sus empleados, aquí podrás encontrar camisetas, vasos, comics, juguetes y accesorios
basados en los superhéroes de Marvel y DC comics, incluso algunos alternativos creados por la
comunidad.
Actualmente el control es llevado en libros, con un pequeño sistema kardex que controla los productos
del inventario, los cuales se incrementan con el ingreso de nuevos productos o disminuyen con la venta
de los mismos. Esto ha funcionado, pero se ha visto la necesidad de sistematizar esta información con el
fin de hacer un control más óptimo y hacer más eficiente el servicio en la tienda.
Por lo anterior tenemos una misión para ti, desarrollar la fase I de este proyecto, la cual consiste en
crear un sistema kardex que controle nuestros productos, incremente con el registro de nuevos
productos y disminuya con la salida de los mismos, además que nuestro vendedor realice cambios esas
cantidades a través de las interfaces correspondientes. Importante aclarar que cuando un producto no
tenga stock no permita realizar movimientos con él.







