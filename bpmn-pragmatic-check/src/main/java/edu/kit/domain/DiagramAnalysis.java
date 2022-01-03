package edu.kit.domain;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;

import java.util.*;

public class DiagramAnalysis {
    private int executableProcesses;
    private final List<ProcessAnalysis> processAnalysisList;

    // TODO more than one executable process? Not Good!

    public DiagramAnalysis(ModelInstance modelInstance) {
        this.processAnalysisList = new ArrayList<>();
        this.executableProcesses = 0;

        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        for (Process process:processes) {
            ProcessAnalysis processAnalysis = new ProcessAnalysis(process);
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
