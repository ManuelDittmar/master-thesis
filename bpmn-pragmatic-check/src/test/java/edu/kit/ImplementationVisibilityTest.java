package edu.kit;

import edu.kit.domain.SequenceFlowDTO;
import edu.kit.domain.qualityMetrics.ImplementationVisibility;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.impl.instance.SequenceFlowImpl;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnEdge;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;
import org.camunda.bpm.model.xml.ModelInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImplementationVisibilityTest {
    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    ImplementationVisibility implementationVisibility;

    @BeforeAll
    public void init() throws FileNotFoundException {
        ModelInstance modelInstance = Bpmn.readModelFromStream(new FileInputStream(resourcePath +"\\ImplementationVisibilityTest.bpmn"));
        Process process = (Process) new ArrayList(modelInstance.getModelElementsByType(Process.class)).get(0);

        List <BpmnEdge> bpmnEdgeList = (List<BpmnEdge>) modelInstance.getModelElementsByType(BpmnEdge.class);
        Map<String, Collection<Waypoint>> sequenceFlowsCoordinates = new HashMap<>();
        for (BpmnEdge bpmnEdge:bpmnEdgeList) {
            String sequenceFlowId = bpmnEdge.getBpmnElement().getId();
            sequenceFlowsCoordinates.put(sequenceFlowId,bpmnEdge.getWaypoints());

        }
        List<FlowElement> flowElements = process.getFlowElements().stream()
                .filter(flowElement -> flowElement.getClass().equals(SequenceFlowImpl.class))
                .collect(Collectors.toList());
        // Safe all Sequence Flows
        List<SequenceFlowDTO> sequenceFlowDTOList = new ArrayList<>();
        for (FlowElement element: flowElements) {
            // Get Coordinates via bpmn api
            SequenceFlow sequenceFlow = (SequenceFlow) element;
            List<Waypoint> waypointList = new ArrayList<>(sequenceFlowsCoordinates.get(element.getId()));
            sequenceFlowDTOList.add(new SequenceFlowDTO(sequenceFlow, waypointList));
        }
        implementationVisibility = new ImplementationVisibility(process,sequenceFlowDTOList);
    }

    @Test
    public void hasOutlierStartEvent() {
        assertEquals(true, implementationVisibility.getOutliers().contains("StartEventWithListenerStartEvent"));
    }

    @Test
    public void hasOutlierEndEvent() {
        assertEquals(true, implementationVisibility.getOutliers().contains("EndEventWithListenerEndEvent"));
    }

    @Test
    public void hasOutlierSequenceFlow() {
        assertEquals(true, implementationVisibility.getOutliers().contains("SequenceFlowWithListenerSequenceFlow"));
    }

    @Test
    public void hasOutlierNonTypeTask() {
        assertEquals(true, implementationVisibility.getOutliers().contains("NonTypeServiceWithListenerTask"));
    }

    @Test
    public void hasOutlierServiceTask() {
        assertEquals(true, implementationVisibility.getOutliers().contains("ServiceTaskWithListenerTask"));
    }

    @Test
    public void hasOutlierIntermediateEvent() {
        assertEquals(true, implementationVisibility.getOutliers().contains("IntermediateEventWithListenerEvent"));
    }

    @Test
    public void hasOutlierUserTask() {
        assertEquals(true, implementationVisibility.getOutliers().contains("UserTaskWithListenerTask"));
    }

    @Test
    public void hasOutlierGateway() {
        assertEquals(true, implementationVisibility.getOutliers().contains("GatewayWithListenerGateway"));
    }

    @Test
    public void hasOutlierSubprocess() {
        assertEquals(true, implementationVisibility.getOutliers().contains("SubprocessWithListenerSubProcess"));
    }

    @Test
    public void hasOutlierTaskInSubprocess() {
        assertEquals(true, implementationVisibility.getOutliers().contains("ServiceTaskWithListenerInSubTask"));
    }

    @Test
    public void hasOutlierCallActivity() {
        assertEquals(true, implementationVisibility.getOutliers().contains(" CallActivityWithListenerCallActivity"));
    }
}
