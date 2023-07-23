package com.example.individualhealthassessment_program;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "HealthReport")
public class HealthReport {
    private List<ParameterResult> parameterResults;

    public HealthReport() {
        parameterResults = new ArrayList<>();
    }

    @XmlElement(name = "ParameterResult")
    public List<ParameterResult> getParameterResults() {
        return parameterResults;
    }

    public void addParameterResult(ParameterResult parameterResult) {
        parameterResults.add(parameterResult);
    }
}
