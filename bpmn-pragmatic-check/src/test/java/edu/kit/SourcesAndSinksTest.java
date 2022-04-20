package edu.kit;


import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.kit.domain.common.Outlier;
import edu.kit.domain.quality.BehavioralErrors;
import edu.kit.domain.quality.SourcesAndSinks;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SourcesAndSinksTest {

  String resourcePath = Paths.get("src", "test", "resources").toFile().getAbsolutePath();
  SourcesAndSinks sourcesAndSinks;
  List<Outlier> outliers;

  @BeforeAll
  public void init() throws FileNotFoundException {
    ModelInstance modelInstance = Bpmn.readModelFromStream(
        new FileInputStream(resourcePath + "\\SourcesAndSinksTest.bpmn"));
    List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
    sourcesAndSinks = new SourcesAndSinks(processList.get(0));
    sourcesAndSinks.setMaxStartEvents(2);
    sourcesAndSinks.setMaxEndEvents(10);
    sourcesAndSinks.init();
    outliers = sourcesAndSinks.getOutliers();
  }

  @Test
  public void hasOutlierTooManyStartEvents() {
    assertEquals(1,outliers.size());
    assertEquals("StartEvents > Max StartEvents (2)",outliers.get(0));
  }
}