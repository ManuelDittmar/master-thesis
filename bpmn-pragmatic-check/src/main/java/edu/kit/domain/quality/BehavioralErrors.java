package edu.kit.domain.quality;

import org.camunda.bpm.model.bpmn.instance.Process;



//@Component
public class BehavioralErrors extends ProcessQualityCriteria {

    public BehavioralErrors() {
    }

    public BehavioralErrors(Process process) {
        super(process);
    }

    // TODO Check if Start Events in Subprocess will also be taken into account

    @Override
    public void init() {

    }

}
