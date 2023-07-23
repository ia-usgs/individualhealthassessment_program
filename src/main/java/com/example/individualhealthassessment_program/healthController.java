package com.example.individualhealthassessment_program;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class healthController {
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;
    @FXML
    private TextField textField4;
    @FXML
    private TextField textField5;
    @FXML
    private TextArea textArea;
    @FXML
    private Button openButton;
    @FXML
    private Button analyzeButton;
    @FXML
    private Pane rootPane;

    private BloodPressureChecker bloodPressureChecker;
    private BMICalculator bmiCalculator;
    private BloodGlucoseChecker bloodGlucoseChecker;
    private CholesterolChecker cholesterolChecker;
    private TriglyceridesChecker triglyceridesChecker;

    @FXML
    protected void initialize() {
        bloodPressureChecker = new BloodPressureChecker();
        bmiCalculator = new BMICalculator();
        bloodGlucoseChecker = new BloodGlucoseChecker();
        cholesterolChecker = new CholesterolChecker();
        triglyceridesChecker = new TriglyceridesChecker();

        analyzeButton.setOnAction(e -> analyzeInputValues());
    }

    private void analyzeInputValues() {
        String bloodPressure = textField1.getText();
        String bmi = textField2.getText();
        String bloodGlucose = textField3.getText();
        String cholesterol = textField4.getText();
        String triglycerides = textField5.getText();

        // Perform range checks and show notifications if values are out of bounds
        String notification = "Health Assessment Results:\n";

        // Blood Pressure Range Check
        String bloodPressureResult = bloodPressureChecker.checkBloodPressure(Double.parseDouble(bloodPressure));
        notification += "Blood Pressure: " + bloodPressureResult + "\n";
        if (bloodPressureResult.equals("Very severe")) {
            showWarningNotification("Blood Pressure");
        }

        // BMI Range Check
        String bmiResult = bmiCalculator.checkBMI(Double.parseDouble(bmi));
        notification += "BMI: " + bmiResult + "\n";
        if (bmiResult.equals("Underweight") || bmiResult.equals("Obese")) {
            showWarningNotification("BMI");
        }

        // Blood Glucose Range Check
        String bloodGlucoseResult = bloodGlucoseChecker.checkBloodGlucose(Double.parseDouble(bloodGlucose));
        notification += "Blood Glucose: " + bloodGlucoseResult + "\n";
        if (bloodGlucoseResult.equals("Out of control") || bloodGlucoseResult.equals("Poor")) {
            showWarningNotification("Blood Glucose");
        }

        // Cholesterol Range Check
        String cholesterolResult = cholesterolChecker.checkCholesterol(cholesterol);
        notification += "Cholesterol: " + cholesterolResult + "\n";
        if (cholesterolResult.equals("Not Optimal")) {
            showWarningNotification("Cholesterol");
        }

        // Triglycerides Range Check
        String triglyceridesResult = triglyceridesChecker.checkTriglycerides(Double.parseDouble(triglycerides));
        notification += "Triglycerides: " + triglyceridesResult;
        if (triglyceridesResult.equals("Optimal")) {
            showWarningNotification("Triglycerides");
        }

        textArea.setText(notification);

        // Save the health assessment report to an XML file
        saveHealthReport();
    }

    private void showWarningNotification(String parameter) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Health Parameter Warning");
        alert.setHeaderText(null);
        alert.setContentText("The " + parameter + " reading is far out of bounds.\nPlease see a doctor or consult a health professional.");
        alert.showAndWait();
    }

    private void saveHealthReport() {
        HealthReport healthReport = new HealthReport();

        // Create ParameterResult objects and add them to the health report
        healthReport.addParameterResult(new ParameterResult("Blood Pressure", bloodPressureChecker.checkBloodPressure(Double.parseDouble(textField1.getText()))));
        healthReport.addParameterResult(new ParameterResult("BMI", bmiCalculator.checkBMI(Double.parseDouble(textField2.getText()))));
        healthReport.addParameterResult(new ParameterResult("Blood Glucose", bloodGlucoseChecker.checkBloodGlucose(Double.parseDouble(textField3.getText()))));
        healthReport.addParameterResult(new ParameterResult("Cholesterol", cholesterolChecker.checkCholesterol(textField4.getText())));
        healthReport.addParameterResult(new ParameterResult("Triglycerides", triglyceridesChecker.checkTriglycerides(Double.parseDouble(textField5.getText()))));

        // Save the health report to an XML file
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(HealthReport.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Save the XML file to disk
            File outputFile = new File("health_report.xml");
            marshaller.marshal(healthReport, outputFile);

            showSaveSuccessNotification(outputFile.getAbsolutePath());
        } catch (JAXBException e) {
            e.printStackTrace();
            showSaveErrorNotification();
        }
    }

    private void showSaveSuccessNotification(String filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Health Report Saved");
        alert.setHeaderText(null);
        alert.setContentText("The health assessment report has been saved successfully.\nFile location: " + filePath);
        alert.showAndWait();
    }

    private void showSaveErrorNotification() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Save Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while saving the health assessment report.");
        alert.showAndWait();
    }
}
