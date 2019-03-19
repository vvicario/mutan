Mutant
====

[![Build Status](https://travis-ci.com/vvicario/mutant.svg?branch=master)](https://travis-ci.com/vvicario/mutant.svg?branch=master)

[![Coverage Status](https://coveralls.io/repos/github/vvicario/mutant/badge.svg?branch=master)](https://coveralls.io/github/vvicario/mutant?branch=master)

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Mens.
Se necesita detectar si un humano es mutante basándose en su secuencia de ADN.
En donde se recibirá como parámetro un array de Strings que representan cada fila de una tabla
de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las
cuales representa cada base nitrogenada del ADN.
Un humano es mutante, si se encuentra ​ más de una secuencia de cuatro letras iguales​, de forma oblicua, horizontal o vertical.

### Development

Mutant API
====

## Local Development
* Maven 3.5
* Java 8
* Spring Boot: https://spring.io/projects/spring-boot
* Cloud Datastore https://cloud.google.com/datastore/

# Check out repository

```bash
git clone https://github.com/vvicario/mutant.git
```

## Installation
Run `mvn clean install`.

## Deploying
Run `MutantApplication` from your favorite IDE. Alternatively, run `mvn clean package` to generate mutant-1.jar, and
then look in your target directory and run `java -jar mutant-1.jar`. To verify your deployment,
issue a GET to localhost:8080/stats This find out statistics.

## Deployed Version

URL: https://mutant-230214.appspot.com
## Endpoints:

- GET: https://mutant-230214.appspot.com/stats

- POST: https://mutant-230214.appspot.com/mutant

Body Example:
{
"dna":["TCCAGG","CTCAGG","CGACGT","ACGTCG","ATCGTC","TCACGA"]
}
