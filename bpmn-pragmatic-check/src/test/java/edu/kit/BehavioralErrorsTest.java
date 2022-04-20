package edu.kit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kit.domain.common.Outlier;
import edu.kit.domain.quality.BehavioralErrors;
import edu.kit.domain.quality.EdgeOrthogonality;
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
public class BehavioralErrorsTest {

  String resourcePath = Paths.get("src", "test", "resources").toFile().getAbsolutePath();
  BehavioralErrors behavioralErrors;
  List<Outlier> outliers;

  @BeforeAll
  public void init() throws FileNotFoundException {
    ModelInstance modelInstance = Bpmn.readModelFromStream(
        new FileInputStream(resourcePath + "\\BehavioralErrorsTest.bpmn"));
    List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
    behavioralErrors = new BehavioralErrors(processList.get(0));
    outliers = behavioralErrors.getOutliers();
  }

  @Test
  public void hasOutlierDeadlock() {
    assertEquals(1,outliers.size());
    assertEquals("DeadlockGateway",outliers.get(0).getId());
  }
}