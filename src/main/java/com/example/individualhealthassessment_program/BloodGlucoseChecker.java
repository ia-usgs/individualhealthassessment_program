package com.example.individualhealthassessment_program;

public class BloodGlucoseChecker {
    public String checkBloodGlucose(double bloodGlucose) {
        if (bloodGlucose >= 340) {
            return "Out of control";
        } else if (bloodGlucose >= 270) {
            return "Poor";
        } else if (bloodGlucose >= 210) {
            return "Marginal";
        } else if (bloodGlucose >= 150) {
            return "Good";
        } else {
            return "Excellent";
        }
    }
}
