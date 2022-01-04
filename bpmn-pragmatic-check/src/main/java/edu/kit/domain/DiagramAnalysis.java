package edu.kit.domain;

import java.util.List;


public class DiagramAnalysis {

    private int executableProcesses;
    private List<ProcessAnalysis> processAnalysisList;

    public DiagramAnalysis(List<ProcessAnalysis> processAnalysisList, int executableProcesses) {
        this.executableProcesses = executableProcesses;
        this.processAnalysisList = processAnalysisList;
    }

    // TODO more than one executable process? Not Good!

    public void setExecutableProcesses(int executableProcesses) {
        this.executableProcesses = executableProcesses;
    }

    public void setProcessAnalysisList(List<ProcessAnalysis> processAnalysisList) {
        this.processAnalysisList = processAnalysisList;
    }

    public int getExecutableProcesses() {
        return executableProcesses;
    }

    public List<ProcessAnalysis> getProcessAnalysisList() {
        return processAnalysisList;
    }
}
