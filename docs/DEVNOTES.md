Mutant API
====

## Local Development
* Maven 3.5
* Java 8
* Spring Boot: https://spring.io/projects/spring-boot

# Check out repository

```bash
git clone https://github.com/vvicario/mutant.git
```

## Installation
Run `mvn clean install`.

## Deploying
Run `MutantApplication` from your favorite IDE. Alternatively, run `mvn clean package` to generate mutant-1.jar, and
then look in your target directory and run `java -jar campsite-1.jar`. To verify your deployment,
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