package edu.kit.controller;

import edu.kit.domain.DiagramAnalysis;
import edu.kit.domain.ProcessAnalysis;
import edu.kit.service.BpmnAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProcessAnalysisController {

    @Autowired
    BpmnAnalysisService bpmnAnalysisService;

    @PostMapping(value = "/analyze",consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
    DiagramAnalysis analyzeProcess(@RequestParam("file") MultipartFile file) throws IOException {
        return bpmnAnalysisService.analyseBpmnFile(file.getInputStream());
    }
}
