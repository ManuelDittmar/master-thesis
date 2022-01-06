package edu.kit.controller;

import edu.kit.domain.DiagramAnalysis;
import edu.kit.service.BpmnAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProcessAnalysisController {

    @Autowired
    BpmnAnalysisService bpmnAnalysisService;

    @GetMapping("/")
    public String homepage() {
        return "home";
    }

    @PostMapping(value = "/analyze", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DiagramAnalysis analyzeProcess(@RequestParam("file") MultipartFile file) throws IOException {
        return bpmnAnalysisService.analyseBpmnFile(file.getInputStream());
    }

    @PostMapping(value = "/analyzePage", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public String analyzeProcessPage(@RequestParam("file") MultipartFile file) throws IOException {
        DiagramAnalysis analysis = bpmnAnalysisService.analyseBpmnFile(file.getInputStream());
        System.out.println(analysis.getProcessAnalysisList().get(0).getProcessKey());
        return "redirect:/";
    }

}
