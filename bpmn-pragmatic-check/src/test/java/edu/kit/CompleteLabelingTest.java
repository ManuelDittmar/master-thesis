package edu.kit;

import edu.kit.domain.qualityMetrics.CompleteLabeling;
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
public class CompleteLabelingTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    CompleteLabeling completeLabeling;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\LabelingTest.bpmn"));
        List<Process> processList = new ArrayList(modelInstance.getModelElementsByType(Process.class));
        completeLabeling = new CompleteLabeling(processList.get(0));
    }

    @Test
    public void hasCriteriaID() {
        assertEquals("CompleteLabeling",completeLabeling.getCriteriaID());
    }

    @Test
    public void hasOutliers() {
        assertEquals(false,completeLabeling.getOutliers().isEmpty());
    }

    @Test
    public void hasOutlierXOR() {
        assertEquals(true, completeLabeling.getOutliers().contains("UnlabeledGateWaySplitting"));
        assertEquals(false, completeLabeling.getOutliers().contains("UnlabeledMergingXOR1"));
        assertEquals(false, completeLabeling.getOutliers().contains("UnlabeledMergingXOR2"));
    }

    @Test
    public void hasOutlierORSplitting() {
        assertEquals(true, completeLabeling.getOutliers().contains("UnlabeledGatewayInclusiveORSplitting"));
        assertEquals(false, completeLabeling.getOutliers().contains("UnlabeledGatewayInclusiveORSync"));
    }

    @Test
    public void hasNotOutlierParallelGateway() {
        assertEquals(false,completeLabeling.getOutliers().contains("UnlabeledParallelGateWaySplitting"));
        assertEquals(false,completeLabeling.getOutliers().contains("UnlabeledParallelGateWaySync"));
    }

    @Test
    public void hasNotOutlierEventBasedGateway() {
        assertEquals(false,completeLabeling.getOutliers().contains("UnlabeledEventBasedGateway"));
    }

    @Test
    public void hasOutlierStartEvent() {
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledStartEvent1"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledStartEvent2"));
    }

    @Test
    public void hasOutlierEndEvent() {
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledEndEvent1"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledEndEvent2"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledEndEventBoundary"));
    }

    @Test
    public void hasOutlierIntermediateEvent() {
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledIntermediateEvent"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledBoundaryEvent"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledMessageIntermediateEvent"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledTimerIntermediateEvent"));

    }

    @Test
    public void hasOutlierSequenceFlows() {
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledSequenceFlowXORSplitting1"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledSequenceFlowXORSplitting2"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledSequenceFlowORSplitting1"));
        assertEquals(true,completeLabeling.getOutliers().contains("UnlabeledSequenceFlowORSplitting2"));
        assertEquals(false,completeLabeling.getOutliers().contains("UnlabeledSequenceFlowXORSplittingDefault"));
    }

    @Test
    public void hasNotOutlierSubprocess() {
        assertEquals(false,completeLabeling.getOutliers().contains("EventSubprocess"));
        assertEquals(false,completeLabeling.getOutliers().contains("EventSubprocess"));
    }
}
