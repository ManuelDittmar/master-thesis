package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleExecutablePool extends DiagramQualityCriteria {

    public SingleExecutablePool() {
        super();
    }

    public SingleExecutablePool(ModelInstance modelInstance) {
        super(modelInstance);
    }

    @Override
    public void calculate() {
        int executableProcesses = 0;
        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        for (Process process:processes) {
            if(process.isExecutable()) {
                executableProcesses++;
            }
        }
        if(executableProcesses == 1) {
            score = 1;
        } else {
            score = 0;
        }
    }
}
