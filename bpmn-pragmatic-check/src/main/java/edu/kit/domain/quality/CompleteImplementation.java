package edu.kit.domain.quality;

import edu.kit.domain.common.CriteriaType;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(
        value = "pragmatic.completeImplementation.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class CompleteImplementation extends ProcessQualityCriteria{

    public CompleteImplementation() {
        criteriaType = CriteriaType.MANDATORY;
    }

    public CompleteImplementation(Process process) {
        super(process);
        criteriaType = CriteriaType.MANDATORY;
    }

    @Override
    public void init() {
        List<FlowElement> flowElementList =getAllFlowElements(process).stream().filter(element -> needsLabel(element)).collect(Collectors.toList());
        int elementsToBeChecked = flowElementList.size();
        flowElementList.forEach(element -> {
            String instanceType = element.getElementType().getInstanceType().getSimpleName();
            switch (instanceType) {
                case "ServiceTask":
                    if(!hasImplemenationDetails((ServiceTask) element)) {
                        outliers.add(element.getId());
                    }
                    break;
                case "ScriptTask":
                    if(!hasImplemenationDetails((ScriptTask) element)) {
                        outliers.add(element.getId());
                    }
                    break;
                case "SequenceFlow":
                    SequenceFlow sequenceFlow = (SequenceFlow) element;
                    if(sequenceFlow.getConditionExpression()==null) {
                        outliers.add(element.getId());
                    }
                    break;
                case "BusinessRuleTask":
                    hasImplemenationDetails((BusinessRuleTask) element);
                    break;
                case "SendTask":
                    if(!hasImplemenationDetails((SendTask) element)) {
                    outliers.add(element.getId());
                }
                    break;
                case "IntermediateThrowEvent":
                case "EndEvent":
                    if(!hasImplemenationDetails((ThrowEvent) element)) {
                    outliers.add(element.getId());
                }
                    break;
                case "IntermediateCatchEvent":
                case "StartEvent":
                    if(!hasImplemenationDetails((CatchEvent) element)) {
                    outliers.add(element.getId());
                }
                    break;
                default: break;

            }
        });
        setCalculatedScore(elementsToBeChecked);
    }

    public boolean hasImplemenationDetails(ServiceTask serviceTask) {
        return hasCamundaImplemenation(serviceTask.getCamundaClass(), serviceTask.getCamundaDelegateExpression(), serviceTask.getCamundaExpression(), serviceTask.getCamundaTopic());
    }

    public boolean hasImplemenationDetails(SendTask sendTask) {
        return hasCamundaImplemenation(sendTask.getCamundaClass(), sendTask.getCamundaDelegateExpression(), sendTask.getCamundaExpression(), sendTask.getCamundaTopic());
    }

    private boolean hasCamundaImplemenation(String camundaClass, String camundaDelegateExpression, String camundaExpression, String camundaTopic) {
        if(camundaClass==null && camundaDelegateExpression==null && camundaExpression==null && camundaTopic==null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasImplemenationDetails(ScriptTask scriptTask) {
        String scriptFormat = scriptTask.getScriptFormat();
        Script script = scriptTask.getScript();
        String resource = scriptTask.getCamundaResource();
        if(scriptFormat==null) {
            return false;
        }
        if(script==null && resource==null) {
            return false;
        }
        return true;
    }

    public boolean hasImplemenationDetails(BusinessRuleTask businessRuleTask) {
        String camundaClass = businessRuleTask.getCamundaClass();
        String camundaDelegateExpression = businessRuleTask.getCamundaDelegateExpression();
        String camundaExpression = businessRuleTask.getCamundaExpression();
        String camundaTopic = businessRuleTask.getCamundaTopic();
        String dmn = businessRuleTask.getCamundaDecisionRef();
        if(camundaClass==null && camundaDelegateExpression==null && camundaExpression==null && camundaTopic==null && dmn==null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasImplemenationDetails(ThrowEvent throwEvent) {
        List<EventDefinition> eventDefinition = new ArrayList<>(throwEvent.getEventDefinitions());
        if(eventDefinition.size() > 0) {
            return hasImplementationDetails(eventDefinition.get(0));
        }
        return true;
    }

    public boolean hasImplemenationDetails(CatchEvent catchEvent) {
        List<EventDefinition> eventDefinition = new ArrayList<>(catchEvent.getEventDefinitions());
        if(eventDefinition.size() > 0) {
            return hasImplementationDetails(eventDefinition.get(0));
        }
        return true;
    }

    public boolean hasImplementationDetails(EventDefinition eventDefinition) {
        String eventDefinitionName = eventDefinition.getElementType().getInstanceType().getSimpleName();
        if(eventDefinitionName.equals("SignalEventDefinition")) {
            SignalEventDefinition signalEventDefinition = (SignalEventDefinition) eventDefinition;
            if(signalEventDefinition.getSignal() == null) {
                return false;
            }
        }
        if(eventDefinitionName.equals("MessageEventDefinition")) {
            MessageEventDefinition messageEventDefinition = (MessageEventDefinition) eventDefinition;
            if(messageEventDefinition.getMessage() == null) {
                return false;
            }
            return hasCamundaImplemenation(messageEventDefinition.getCamundaClass(), messageEventDefinition.getCamundaDelegateExpression(), messageEventDefinition.getCamundaExpression(), messageEventDefinition.getCamundaTopic());
        }

        if(eventDefinitionName.equals("EscalationEventDefinition")) {
            EscalationEventDefinition escalationEventDefinition = (EscalationEventDefinition) eventDefinition;
            if(escalationEventDefinition.getEscalation() == null) {
                return false;
            }
        }

        if(eventDefinitionName.equals("ErrorEventDefinition")) {
            ErrorEventDefinition errorEventDefinition = (ErrorEventDefinition) eventDefinition;
            if(errorEventDefinition.getError() == null) {
                return false;
            }
        }

        if(eventDefinitionName.equals("CompensateEventDefinition")) {
            CompensateEventDefinition compensateEventDefinition = (CompensateEventDefinition) eventDefinition;
            if(compensateEventDefinition.getActivity() == null) {
                return false;
            }
        }

        if(eventDefinitionName.equals("CompensateEventDefinition")) {
            LinkEventDefinition linkEventDefinition = (LinkEventDefinition) eventDefinition;
            if(linkEventDefinition.getTarget() == null) {
                return false;
            }
        }

        if(eventDefinitionName.equals("TimerEventDefinition")) {
            TimerEventDefinition timerEventDefinition = (TimerEventDefinition) eventDefinition;
            if(timerEventDefinition.getTimeCycle() == null && timerEventDefinition.getTimeDate() == null && timerEventDefinition.getTimeDuration() == null) {
                return false;
            }
        }

        if(eventDefinitionName.equals("ConditionalEventDefinition")) {
            ConditionalEventDefinition conditionalEventDefinition = (ConditionalEventDefinition) eventDefinition;
            if(conditionalEventDefinition.getCondition() == null) {
                return false;
            }
        }
        return true;
    }


}
