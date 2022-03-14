# Examen
Mercado Libre '' Prueba Tecnica.
Marlon Eduardo Castro
marloncastro77@gmail.com
Entrega: 13 de Marzo del 2022

## Required Setup
* Java 1.13 

## Use

database:
```
MongoDB 4.4 Community Edition
AWS
uri: "mongodb://admin:XXXXX@3.212.189.37:27017/admin"
SO: Linux Ubuntu 20.04
```
Codigo:
```
Java 1.13
Framework: SprintBoot 
Test: JUnit
IDE: IntelliJ IDEA 2021.2.3
Manejo de dependencias: Gradlew
Contenedor: Docker
Repositorio: Github
Para los servicios se uso programación funcional (stream)
Para correrlo localmente con la clase AdnApplication o generar la imagen Docker
Arquitectura DDD
```

Servicios:
```
AWS
SO: Linux Ubuntu 20.04 (Se cargaron usando docker)
GET
http://3.212.189.37/stats
Response:
{
    "ratio": 1.0,
    "count_mutant_dna": 2,
    "count_human_dna": 2
}

POST
http://3.212.189.37/mutant
Request:
{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCTAAT","TCTCTC"]}
Response:
Mutante 200 OK
No mutante 403Forbidden
```

Estadísticas código:

```
En la carpeta .scannerwork resultados generados por Sonar (issues, codesmells, vulnerabilidades)
En la carpeta Cobertura: resultados generados por la cobertura en promedio un 90%
```

## Examen
Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar
contra los X-Men.

Te ha contratado a ti para que desarrolles un proyecto que detecte si un
humano es mutante basándose en su secuencia de ADN.

Para eso te ha pedido crear un programa con un método o función con la siguiente firma (En
alguno de los siguiente lenguajes: Java / Golang / C-C++ / Javascript (node) / Python / Ruby):
* boolean isMutant(String[] dna); // Ejemplo Java

En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla
de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las
cuales representa cada base nitrogenada del ADN.

### No-Mutante 
A T G C G A 

C A G T G C

T T A T T T

A G A C G G

G C G T C A

T C A C T G


### Mutante
A T G C G A

C A G T G C

T T A T G T

A G A A G G

C C C C T A

T C A C T G



Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras
iguales​, de forma oblicua, horizontal o vertical.
Ejemplo (Caso mutante):

String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};


En este caso el llamado a la función isMutant(dna) devuelve “true”.
Desarrolla el algoritmo de la manera más eficiente posible.
Desafíos:


## Nivel 1:
Programa (en cualquier lenguaje de programación) que cumpla con el método pedido por
Magneto.

## Nivel 2:
Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine,
Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es
mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el
siguiente formato:

POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un
403-Forbidden

## Nivel 3:
Anexar una base de datos, la cual guarde los ADN’s verificados con la API.
Solo 1 registro por ADN.

Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las
verificaciones de ADN: {"count_mutant_dna" : 40, "count_human_dna" : 100, "ratio" : 0.4 }
Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1
millón de peticiones por segundo).
Test-Automáticos, Code coverage > 80%.


