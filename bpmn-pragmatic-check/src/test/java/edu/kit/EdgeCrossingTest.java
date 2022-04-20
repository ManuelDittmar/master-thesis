package edu.kit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.kit.domain.quality.EdgeCrossing;
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
public class EdgeCrossingTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    EdgeCrossing edgeCrossing;

    @BeforeAll
    public void init() throws FileNotFoundException {
      ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\EdgeCrossing.bpmn"));
      List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
      edgeCrossing = new EdgeCrossing(processList.get(0));
    }

    @Test
    public void hasOutlierCrossing() {
      List outliers = edgeCrossing.getOutliers();
      assertEquals(1, outliers.size());
      String[] outlier = (String[]) outliers.get(0);
      assertEquals("Flow_1.2",outlier[0]);
      assertEquals("Flow_1.1",outlier[1]);

    }

}
