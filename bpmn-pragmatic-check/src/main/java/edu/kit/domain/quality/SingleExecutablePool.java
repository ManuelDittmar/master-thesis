package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public void init() {
        List<String> executableProcesses = new ArrayList<>();
        List<Process> processes = (List<Process>) modelInstance.getModelElementsByType(Process.class);
        for (Process process : processes) {
            if (process.isExecutable()) {
                executableProcesses.add(process.getId());
            }
        }
        if (executableProcesses.size() == 1) {
            score = 1;
        } else {
            score = 0;
            outliers = executableProcesses;
        }
    }
}
