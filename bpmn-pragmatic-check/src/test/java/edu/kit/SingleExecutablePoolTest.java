package edu.kit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kit.domain.common.Outlier;
import edu.kit.domain.quality.SingleExecutablePool;
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
public class SingleExecutablePoolTest {

  String resourcePath = Paths.get("src", "test", "resources").toFile().getAbsolutePath();
  SingleExecutablePool singleExecutablePool;
  List<Outlier> outliers;

  @BeforeAll
  public void init() throws FileNotFoundException {
    ModelInstance modelInstance = Bpmn.readModelFromStream(
        new FileInputStream(resourcePath + "\\SingleExecutablePoolTest.bpmn"));
    singleExecutablePool = new SingleExecutablePool(modelInstance);
    outliers = singleExecutablePool.getOutliers();
  }

  @Test
  public void hasOutlierTooManyStartEvents() {
    assertEquals(2,outliers.size());
    assertTrue(outliers.contains("Pool1"));
    assertTrue(outliers.contains("Process_11govsw"));
    assertFalse(outliers.contains("Process_05cf19n"));
  }
}
