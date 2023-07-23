package com.example.individualhealthassessment_program;

public class TriglyceridesChecker {
    public String checkTriglycerides(double triglycerides) {
        if (triglycerides >= 251) {
            return "Optimal";
        } else {
            return "Excellent";
        }
    }
}
