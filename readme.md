# Software Quality Tool
## Description
The system is designed to measure static java code quality against selected metrics.

## Prerequisites
- Maven (mvn)
- Java 25 SDK

## Workflows
- Release Please: This workflow is triggered when a working branch is merged into main; this adds/creates the release please branch, which contains all the changes made from from the last release. To create a new release or "cut a release", merge the this branch into main.
- Test: This worflow runs when you create a pull request into main. The result is integrated into githubs PR GUI, showing developers if their code has broken anything. This does not currently block failures, but could easily be modified to do so.
- Release JAR: We also have a more incremental release process, this creates a release for branches with a version tag.
- Build and Publish (Maven): Test feature for deploying the project as a maven package. This could be used in future to embed our software quality tool inside of other target repos.

## Building JAR
This is a Maven project with the shade plugin configured to product a JAR with the main class.
To build, just run:
```bash
mvn package -f pom.xml
```
This produces target/qualitytool-1.0-SNAPSHOT.jar (named after the artifactId qualitytool).

## Running the program
```bash
java -jar target/qualitytool-1.0.jar cc
```

Examples:
```bash
java -jar target/qualitytool-1.0.jar cc -p src/test/java/ditTest/ABCExample
```
```bash
java -jar target/qualitytool-1.0.jar dit -p src/test/java/ditTest/ABCExample
```
