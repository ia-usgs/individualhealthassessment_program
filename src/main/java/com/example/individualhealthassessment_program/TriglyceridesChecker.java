package com.example.individualhealthassessment_program;

public class TriglyceridesChecker {
    public String checkTriglycerides(double triglycerides) {
        if (triglycerides >= 250) {
            return "Optimal";
        } else {
            return "Excellent";
        }
    }
}
