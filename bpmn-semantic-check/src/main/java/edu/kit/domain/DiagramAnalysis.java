package edu.kit.domain;

import org.camunda.bpm.model.bpmn.impl.instance.SequenceFlowImpl;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnEdge;
import org.camunda.bpm.model.bpmn.instance.di.Waypoint;
import org.camunda.bpm.model.xml.ModelInstance;

import java.util.*;

public class DiagramAnalysis {
    private int executableProcesses;
    private List<ProcessAnalysis> processAnalysisList;

    // TODO more than one executable process? Not Good!

    public DiagramAnalysis(ModelInstance modelInstance) {
        this.processAnalysisList = new ArrayList<>();
        this.executableProcesses = 0;
        // Get all Processes
        List <BpmnEdge> bpmnEdgeList = (List<BpmnEdge>) modelInstance.getModelElementsByType(BpmnEdge.class);
        Map<String,Collection<Waypoint>> sequenceFlowsCoordinates = new HashMap<>();
        for (BpmnEdge bpmnEdge:bpmnEdgeList) {
            String sequenceFlowId = bpmnEdge.getBpmnElement().getId();
            sequenceFlowsCoordinates.put(sequenceFlowId,bpmnEdge.getWaypoints());

        }

        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        for (Process process:processes) {
            // Get Sequence Flows to get coordinates
            Collection<FlowElement> flowElements = process.getFlowElements();
            // Safe all Sequence Flows
            List<SequenceFlowDTO> sequenceFlowDTOList = new ArrayList<>();
            for (FlowElement element: flowElements) {
                if (element.getClass().equals(SequenceFlowImpl.class)) {
                    // Get Coordinates via bpmn api
                    SequenceFlow sequenceFlow = (SequenceFlow) element;
                    List<Waypoint> waypointList = new ArrayList<>(sequenceFlowsCoordinates.get(element.getId()));
                    sequenceFlowDTOList.add(new SequenceFlowDTO(sequenceFlow, waypointList));
                    //System.out.println("List of WayPoints for: " + element.getId() +  " "+ sequenceFlowsCoordinates.get(element.getId()));
                }
            }
            ProcessAnalysis processAnalysis = new ProcessAnalysis(process, sequenceFlowDTOList);
            processAnalysisList.add(processAnalysis);
            // add count to exectuable Processes
            if(processAnalysis.isExecutable()) {
                this.executableProcesses++;
            }
        }
    }

    public int getExecutableProcesses() {
        return executableProcesses;
    }

    public List<ProcessAnalysis> getProcessAnalysisList() {
        return processAnalysisList;
    }
}
