package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(
        value = "pragmatic.humanTaskAssignment.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class HumanTaskAssignment extends ProcessQualityCriteria {

    public HumanTaskAssignment(Process process) {
        super(process);
    }

    public HumanTaskAssignment() {
        super();
    }

    @Override
    public void init() {
        List<UserTask> userTasks = new ArrayList<>(process.getChildElementsByType(UserTask.class));
        if (hasSubProcess(process)) {
            List<UserTask> subUserTasks = getFlowElementsOfSubprocesses(process, List.of(UserTask.class))
                    .stream().map(flowElement -> (UserTask) flowElement)
                    .collect(Collectors.toList());
            userTasks.addAll(subUserTasks);
        }
        userTasks.forEach(userTask -> {
            if (userTask.getCamundaAssignee() == null && userTask.getCamundaCandidateUsers() == null && userTask.getCamundaCandidateGroups() == null) {
                outliers.add(userTask.getId());
            }
        });
        setCalculatedScore(userTasks.size());
    }
}
