package edu.kit.domain.quality;

import edu.kit.domain.common.SequenceFlowDTO;
import org.camunda.bpm.model.bpmn.impl.instance.SequenceFlowImpl;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnEdge;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;

import java.util.*;
import java.util.stream.Collectors;

public abstract class FlowProcessQualityCriteria extends ProcessQualityCriteria {

    List<SequenceFlowDTO> sequenceFlowDTOList;

    public FlowProcessQualityCriteria() {
        super();
    }

    public FlowProcessQualityCriteria(Process process) {
        super(process);
        this.sequenceFlowDTOList = initSequenceFlowDTOs();
    }

    public List<SequenceFlowDTO> initSequenceFlowDTOs() {
        List<BpmnEdge> bpmnEdgeList = (List<BpmnEdge>) process.getModelInstance().getModelElementsByType(BpmnEdge.class);
        Map<String, Collection<Waypoint>> sequenceFlowsCoordinates = new HashMap<>();
        for (BpmnEdge bpmnEdge : bpmnEdgeList) {
            String sequenceFlowId = bpmnEdge.getBpmnElement().getId();
            sequenceFlowsCoordinates.put(sequenceFlowId, bpmnEdge.getWaypoints());
        }
        List<FlowElement> flowElements = getAllFlowElements(process).stream()
                .filter(flowElement -> flowElement.getClass().equals(SequenceFlowImpl.class))
                .collect(Collectors.toList());
        // Safe all Sequence Flows
        List<SequenceFlowDTO> sequenceFlowDTOList = new ArrayList<>();
        for (FlowElement element : flowElements) {
            // Get Coordinates via bpmn api
            SequenceFlow sequenceFlow = (SequenceFlow) element;
            List<Waypoint> waypointList = new ArrayList<>(sequenceFlowsCoordinates.get(element.getId()));
            sequenceFlowDTOList.add(new SequenceFlowDTO(sequenceFlow, waypointList));
        }
        return sequenceFlowDTOList;
    }

    public void setSequenceFlowDTOList(List<SequenceFlowDTO> sequenceFlowDTOList) {
        this.sequenceFlowDTOList = sequenceFlowDTOList;
    }

}
