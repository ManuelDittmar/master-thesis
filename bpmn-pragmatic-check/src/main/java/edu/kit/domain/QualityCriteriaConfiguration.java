package edu.kit.domain;

import edu.kit.domain.qualityMetrics.ProcessQualityCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class QualityCriteriaConfiguration {

    @Autowired
    List<ProcessQualityCriteria> processQualityCriteriaList;

    @Bean
    public List<ProcessQualityCriteria> qualityCriteriaList() {
        return this.processQualityCriteriaList;
    }

    @PostConstruct
    public void init() {
        System.out.println(processQualityCriteriaList.size() + " Criteria!");
    }


}
