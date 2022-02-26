package edu.kit.domain.quality;

import edu.kit.domain.common.CriteriaType;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(
        value = "pragmatic.singleExecutablePool.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class SingleExecutablePool extends DiagramQualityCriteria {

    public SingleExecutablePool() {
        super();
    }

    public SingleExecutablePool(ModelInstance modelInstance) {
        super(modelInstance);
        criteriaType = CriteriaType.MANDATORY;
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
