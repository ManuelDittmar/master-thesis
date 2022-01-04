package edu.kit.domain;

import edu.kit.domain.qualityMetrics.QualityCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class QualityCriteriaConfiguration {

    @Autowired
    List<QualityCriteria> qualityCriteriaList;

    @Bean
    public List<QualityCriteria> qualityCriteriaList() {
        return this.qualityCriteriaList;
    }

    @PostConstruct
    public void init() {
        System.out.println(qualityCriteriaList.size() + " Criteria!");
    }


}
