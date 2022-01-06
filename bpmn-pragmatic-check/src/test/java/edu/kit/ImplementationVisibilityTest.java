package edu.kit;

import edu.kit.domain.quality.ImplementationVisibility;
import edu.kit.domain.Outlier;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImplementationVisibilityTest {
    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    ImplementationVisibility implementationVisibility;
    List<Outlier> outliers;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\ImplementationVisibilityTest.bpmn"));
        Process process = (Process) new ArrayList(modelInstance.getModelElementsByType(Process.class)).get(0);
        implementationVisibility = new ImplementationVisibility(process);
        outliers = implementationVisibility.getOutliers();
    }

    @Test
    public void hasOutlierStartEvent() {
        List<Outlier> outliers = implementationVisibility.getOutliers();
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("StartEventWithListenerStartEvent")));
    }

    @Test
    public void hasOutlierEndEvent() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("EndEventWithListenerEndEvent")));
    }

    @Test
    public void hasOutlierSequenceFlow() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("SequenceFlowWithListenerSequenceFlow")));
    }

    @Test
    public void hasOutlierNonTypeTask() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("NonTypeServiceWithListenerTask")));
    }

    @Test
    public void hasOutlierServiceTask() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ServiceTaskWithListenerTask")));
    }

    @Test
    public void hasOutlierIntermediateEvent() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("IntermediateEventWithListenerEvent")));
    }

    @Test
    public void hasOutlierUserTask() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("UserTaskWithListenerTask")));
    }

    @Test
    public void hasOutlierGateway() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("GatewayWithListenerGateway")));
    }

    @Test
    public void hasOutlierSubprocess() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("SubprocessWithListenerSubProcess")));
    }

    @Test
    public void hasOutlierTaskInSubprocess() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("ServiceTaskWithListenerInSubTask")));
    }

    @Test
    public void hasOutlierCallActivity() {
        assertEquals(true, outliers.stream()
                .anyMatch(outlier -> outlier.getId().equals("CallActivityWithListenerCallActivity")));
    }
}
