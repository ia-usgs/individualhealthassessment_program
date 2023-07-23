package com.example.individualhealthassessment_program;

public class CholesterolChecker {
    public String checkCholesterol(String cholesterolInput) {
        try {
            String[] values = cholesterolInput.split("/");
            if (values.length == 2) {
                double ldl = Double.parseDouble(values[0].trim());
                double hdl = Double.parseDouble(values[1].trim());

                if (ldl < 100 && hdl > 40) {
                    return "Optimal";
                } else {
                    return "Not Optimal";
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        }
        return "Invalid Input";
    }
}
