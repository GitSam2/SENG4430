# Software Quality Tool
## Description
...
## Prerequisites
- Maven (mvn)
- Java 25 SDK

## Workflows
...
## Contributing
...
## Building JAR
This is a Maven project with the shade plugin configured to product a JAR with the main class.
To build, just run:
```bash
mvn package -f pom.xml
```
This produces target/src-1.0-SNAPSHOT.jar (named after the artifactId src).

## Running the program
```bash
java -jar target/src-1.0-SNAPSHOT.jar cc
```

Examples:
```bash
java -jar target/src-1.0-SNAPSHOT.jar cc -p src/test/java/ditTest/ABCExample
```
```bash
java -jar target/src-1.0-SNAPSHOT.jar dit -p src/test/java/ditTest/ABCExample
```