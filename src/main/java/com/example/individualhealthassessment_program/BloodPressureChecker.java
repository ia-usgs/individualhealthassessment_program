package com.example.individualhealthassessment_program;

public class BloodPressureChecker {
    public String checkBloodPressure(double bloodPressure) {
        if (bloodPressure >= 210) {
            return "Very severe";
        } else if (bloodPressure >= 180) {
            return "Severe";
        } else if (bloodPressure >= 160) {
            return "Moderate";
        } else if (bloodPressure >= 140) {
            return "Mild";
        } else if (bloodPressure >= 90) {
            return "Normal";
        } else {
            return "Low";
        }
    }
}
