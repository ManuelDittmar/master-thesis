package edu.kit;

import edu.kit.domain.qualityMetrics.Explicitness;
import edu.kit.domain.qualityMetrics.Outlier;
import edu.kit.domain.qualityMetrics.TaskTypeDefinition;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExplicitnessTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    Explicitness explicitness;
    List<Outlier> outliers;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\ExplicitnessTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        explicitness = new Explicitness(processList.get(0));
        outliers = explicitness.getOutliers();
    }

    @Test
    public void hasOutlierImplicitSplitTask() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitSplitTask")));
    }

    @Test
    public void hasOutlierImplicitXORTask() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitXORTask")));
    }

    @Test
    public void hasOutlierImplicitXOREvent() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitXOREvent")));
    }

    @Test
    public void hasOutlierImplicitSplitEvent() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitSplitEvent")));
    }

    @Test
    public void hasOutlierImplicitXORSubProcess() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitSubProcess")));
        Outlier outlierSubProcess =outliers.stream().filter(outlier -> outlier.getId().equals("ImplicitSubProcess")).collect(Collectors.toList()).get(0);
        assertEquals(true, outlierSubProcess.getReasons().contains("Flow_06dzkxh"));
        assertEquals(true, outlierSubProcess.getReasons().contains("Flow_0e7dok5"));
    }

    @Test
    public void hasOutlierImplicitSplitSubProcess() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitSubProcess")));
        Outlier outlierSubProcess =outliers.stream().filter(outlier -> outlier.getId().equals("ImplicitSubProcess")).collect(Collectors.toList()).get(0);
        assertEquals(true, outlierSubProcess.getReasons().contains("Flow_0t8unbh"));
        assertEquals(true, outlierSubProcess.getReasons().contains("Flow_0cmcuts"));
    }

    @Test
    public void hasOutlierImplicitSplitStartEvent() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitSplitStartEvent")));
    }

    @Test
    public void hasOutlierImplicitXOREndEvent() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ImplicitXOREndEvent")));
    }

}
