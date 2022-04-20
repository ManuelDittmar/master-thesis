package edu.kit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kit.domain.common.Outlier;
import edu.kit.domain.quality.RightwardFlowDirection;
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
public class RightwardFlowDirectionTest {

  String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
  RightwardFlowDirection rightwardFlowDirection;
  List<Outlier> outliers;

  @BeforeAll
  public void init() throws FileNotFoundException {
    ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\RightwardFlowDirectionTest.bpmn"));
    List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
    rightwardFlowDirection = new RightwardFlowDirection(processList.get(0));
    outliers = rightwardFlowDirection.getOutliers();
  }

  @Test
  public void hasOutlierDownwards() {
    assertTrue(outliers.contains("NotOKBecauseSourceIsNotAGatewaySequenceFlow"));
  }

  @Test
  public void hasOutlierLeftwards() {
    assertTrue(outliers.contains("NotOKAfterGatewaySequenceFlow"));
    assertTrue(outliers.contains("NotOKSequenceFlow"));
  }

  @Test
  public void hasNotOutlierThatArePartOfLoop() {
    assertFalse(outliers.contains("AllowedLoopSequenceFlow"));
  }

}
