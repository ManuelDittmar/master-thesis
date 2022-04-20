# Pragmatic Quality Check of BPMN Diagrams

Created Prototype for the Master Thesis "Automatic assessment of pragmatic quality
aspects of executable BPMN models" at Hector School

## Built with
- [Maven](https://maven.apache.org/) - Dependency Management
- Spring Boot
- Camunda

## Getting Started

Executing the mvn spring-boot:run command triggers the download of Apache Tomcat and initializes the startup of Tomcat.

```
mvn spring-boot:run
```

You can access the frontend on [localhost:8080](localhost:8080)

## Configuration

Criteria can be configured in the application.yaml file e.g.:

```
  taskTypeDefinition:
    enabled: true
    forbiddenTaskTypes: manualTask,task,scriptTask
```

## Running the tests

Explain how to run the automated tests for this system

```
mvn clean test
```

## Limitations

Only BPMN Diagrams created for the Camunda Platform 7.x can be analysed.

## REST API

### POST /analyze

form-data

| key  | value  |
|------|--------|
| file | *.bpmn |

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
