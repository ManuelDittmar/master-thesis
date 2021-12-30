package edu.kit;

import edu.kit.domain.DiagramAnalysis;
import edu.kit.service.BpmnAnalysisService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiagramAnalysisTest {

    String resourcePath = Paths.get("src","test","resources").toFile().getAbsolutePath();
    BpmnAnalysisService bpmnAnalysisService = new BpmnAnalysisService();
    DiagramAnalysis diagramAnalysis;

    @BeforeAll
    public void init() throws FileNotFoundException {
        diagramAnalysis = bpmnAnalysisService.analyseBpmnFile(new FileInputStream(resourcePath +"\\SubProcessExample.bpmn"));
    }

    @Test
    public void hasOnlyOneProcess(){
        assertEquals(1,diagramAnalysis.getProcessAnalysisList().size());
    }

    @Test
    public void hasOnlyOneExecutableProcess() throws FileNotFoundException {
        assertEquals(1,diagramAnalysis.getExecutableProcesses());
    }

    @Test
    public void hasNameSubProcess() {
        assertEquals("SubProcessesProcess",diagramAnalysis.getProcessAnalysisList().get(0).getProcessKey());
        assertEquals("SubProcesses",diagramAnalysis.getProcessAnalysisList().get(0).getProcessName());
    }

}
