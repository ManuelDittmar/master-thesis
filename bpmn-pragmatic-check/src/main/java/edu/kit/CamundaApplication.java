package edu.kit;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("bpmn-pragmatic-check")
public class CamundaApplication {

  //TODO USE STABLE VERSION OR NO ENGINE AT ALL
  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }

}
