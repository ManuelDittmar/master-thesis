package edu.kit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kit.domain.common.Outlier;
import edu.kit.domain.quality.CompleteImplementation;
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
public class CompleteImplementationTest {

  String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
  CompleteImplementation completeImplementation;
  List<Outlier> outliers;

  @BeforeAll
  public void init() throws FileNotFoundException {
    ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\CompleteImplementationTest.bpmn"));
    List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
    completeImplementation = new CompleteImplementation(processList.get(0));
    outliers = completeImplementation.getOutliers();
  }

  @Test
  public void hasOutlierServiceTask() {
    assertTrue(outliers.contains("ServiceTaskTask"));
  }

  @Test
  public void hasOutlierScriptTask() {
    assertTrue(outliers.contains("ScriptTaskTask"));
  }

  @Test
  public void hasOutlierMessageEvent() {
    assertTrue(outliers.contains("ReceiveMessageEvent"));
  }

}