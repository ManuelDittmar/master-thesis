package edu.kit;

import edu.kit.domain.qualityMetrics.Explicitness;
import edu.kit.domain.qualityMetrics.HumanTaskAssignment;
import edu.kit.domain.qualityMetrics.Outlier;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HumanTaskAssignmentTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    HumanTaskAssignment humanTaskAssignment;
    List<Outlier> outliers;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\HumanTaskAssignmentTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        humanTaskAssignment = new HumanTaskAssignment(processList.get(0));
        outliers = humanTaskAssignment.getOutliers();
    }

    @Test
    public void hasTwoOutlier() {
        assertEquals(2,outliers.size());
    }

    @Test
    public void hasOutlierTask() {
        outliers.contains("WithoutAssignmentTask");
    }

    @Test
    public void hasOutlierTaskInSubProcess() {
        outliers.contains("WithoutAssignmentSubTask");
    }
}
