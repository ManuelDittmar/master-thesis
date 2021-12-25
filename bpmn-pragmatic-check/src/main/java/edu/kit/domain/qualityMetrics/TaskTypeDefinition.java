package edu.kit.domain.qualityMetrics;

import org.camunda.bpm.model.bpmn.instance.Process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskTypeDefinition extends QualityCriteria{

    Process process;
    List<String> forbiddenTaskTypes;

    // TODO allow to modify allowedSymbols
    public TaskTypeDefinition(Process process) {
        criteriaID = "Task Type Definition";
        this.process = process;
        outliers = new ArrayList<String>();
        forbiddenTaskTypes = Arrays.asList("manualTask","task","scriptTask");
        calculate();
    }

    // TODO difference between expanded and non-expanded subprocess

    // TODO Boundary Event cannot be non-typed

    // TODO when task is in subprocess, also needs to be checked

    // TODO Loop marker not allowed

    @Override
    public void calculate() {
        int taskCount = (int) process.getFlowElements().stream().filter(flowElement -> flowElement.getElementType().getBaseType().getTypeName().matches("task|activity")).count();
        this.outliers = process.getFlowElements().stream()
                .filter(flowElement -> forbiddenTaskTypes.contains(flowElement.getElementType().getTypeName())).map(task -> task.getId()).collect(Collectors.toList());
        outliers.addAll(process.getFlowElements().stream()
                .filter(flowElement -> flowElement.getElementType().getTypeName().equals("subProcess") && flowElement.getRawTextContent().isEmpty())
                .map(subProcess -> subProcess.getId()).collect(Collectors.toList()));
        this.score = (taskCount- (double) outliers.size()) / taskCount;
    }
}
