package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HumanTaskAssignment extends QualityCriteria{

    public HumanTaskAssignment(Process process) {
        super(process);
        calculate();
    }

    public HumanTaskAssignment() {
        super();
    }

    @Override
    public void calculate() {
        List<UserTask> userTasks = new ArrayList<>(process.getChildElementsByType(UserTask.class));
        if(hasSubProcess(process)) {
            List<UserTask> subUserTasks = getFlowElementsOfSubprocesses(process,List.of(UserTask.class))
                    .stream().map(flowElement -> (UserTask) flowElement)
                    .collect(Collectors.toList());
            userTasks.addAll(subUserTasks);
        }
        userTasks.forEach(userTask -> {
            if (userTask.getCamundaAssignee() == null && userTask.getCamundaCandidateUsers() == null && userTask.getCamundaCandidateGroups() == null) {
                outliers.add(userTask.getId());
            }
        });
        double outlierSize = outliers.size();
        score = (userTasks.size() - outlierSize)/ userTasks.size();
    }
}
