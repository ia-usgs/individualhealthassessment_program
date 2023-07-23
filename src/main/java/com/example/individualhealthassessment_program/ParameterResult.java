package com.example.individualhealthassessment_program;

import javax.xml.bind.annotation.XmlElement;

public class ParameterResult {
    private String parameter;
    private String result;

    public ParameterResult() {
    }

    public ParameterResult(String parameter, String result) {
        this.parameter = parameter;
        this.result = result;
    }

    @XmlElement
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @XmlElement
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
