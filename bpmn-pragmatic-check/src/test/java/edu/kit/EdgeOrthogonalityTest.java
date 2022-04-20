package edu.kit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kit.domain.common.Outlier;
import edu.kit.domain.quality.EdgeOrthogonality;
import edu.kit.domain.quality.TransactionBoundaries;
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
public class EdgeOrthogonalityTest {

  String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
  EdgeOrthogonality edgeOrthogonality;
  List<Outlier> outliers;

  @BeforeAll
  public void init() throws FileNotFoundException {
    ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\EdgeOrthogonalityTest.bpmn"));
    List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
    edgeOrthogonality = new EdgeOrthogonality(processList.get(0));
    outliers = edgeOrthogonality.getOutliers();
  }

  @Test
  public void hasOutlier() {
    assertTrue(outliers.contains("No90Degree"));
  }

}
