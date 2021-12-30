package edu.kit;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskTypeDefinitionTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    TaskTypeDefinition taskTypeDefinition;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\TaskTypeDefinitionTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        taskTypeDefinition = new TaskTypeDefinition(processList.get(0));
    }

    @Test
    public void hasOutlierNoneTaskType() {
        assertEquals(true, taskTypeDefinition.getOutliers().contains("NoneTypeTask"));
        assertEquals(true, taskTypeDefinition.getOutliers().contains("NoneTaskTypeSubprocess"));
    }

    @Test
    public void hasOutlierNonTypedBoundaryEvent() {
        assertEquals(true, taskTypeDefinition.getOutliers().contains("NoneTypeBoundaryEvent"));
        assertEquals(false, taskTypeDefinition.getOutliers().contains("MessageBoundaryEvent"));
    }

    @Test
    public void hasOutlierScriptTask() {
        assertEquals(true, taskTypeDefinition.getOutliers().contains("ScriptTask"));
    }

    @Test
    public void hasOutlierCollapsedSubprocess() {
        assertEquals(true, taskTypeDefinition.getOutliers().contains("CollapsedSubprocess"));
    }

    @Test
    public void hasOutlierManualTask() {
        assertEquals(true, taskTypeDefinition.getOutliers().contains("ManualTask"));
    }

    @Test
    public void hasOutlierLoopMarker() {
        assertEquals(true, taskTypeDefinition.getOutliers().contains("LoopMarkerServiceTask"));
    }

    @Test
    public void hasNotOutlierServiceTask() {
        assertEquals(false, taskTypeDefinition.getOutliers().contains("ServiceTaskNoMarker"));
    }

    @Test
    public void hasNotOutlierExpandedSubprocess() {
        assertEquals(false, taskTypeDefinition.getOutliers().contains("ExpandedSubProcess"));
    }
}
