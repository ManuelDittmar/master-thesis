package edu.kit.domain;

import edu.kit.domain.quality.ProcessQualityCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class QualityCriteriaConfiguration {

    @Autowired
    List<ProcessQualityCriteria> processQualityCriteriaList;

    @PostConstruct
    public void init() {
        System.out.println(processQualityCriteriaList.size() + " Criteria!");
    }


}
