package edu.kit.service;

import edu.kit.domain.DiagramAnalysis;
import edu.kit.domain.ProcessAnalysis;
import edu.kit.service.ProcessAnalysisService;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiagramAnalysisService {

    private  ProcessAnalysisService processAnalysisService;


    // TODO more than one executable process? Not Good!

    public DiagramAnalysis analyseDiagram(ModelInstance modelInstance) {
        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        List<ProcessAnalysis> processAnalysisList = new ArrayList<>();
        int executableProcesses = 0;
        for (Process process:processes) {
            ProcessAnalysis processAnalysis = processAnalysisService.analyseProcess(process);
            processAnalysisList.add(processAnalysis);
            // add count to exectuable Processes
            if(processAnalysis.isExecutable()) {
                executableProcesses++;
            }
        }
        return new DiagramAnalysis(processAnalysisList,executableProcesses);
    }


    @Autowired
    public void setProcessAnalysisService(ProcessAnalysisService processAnalysisService) {
        this.processAnalysisService = processAnalysisService;
    }
}
