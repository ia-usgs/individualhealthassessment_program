package com.example.individualhealthassessment_program;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    private Button saveButton;
    @FXML
    private TextField filepathField;
    @FXML
    private Pane rootPane;

    private BloodPressureChecker bloodPressureChecker;
    private BMICalculator bmiCalculator;
    private BloodGlucoseChecker bloodGlucoseChecker;
    private CholesterolChecker cholesterolChecker;
    private TriglyceridesChecker triglyceridesChecker;

    private HealthReport healthReport;

    @FXML
    protected void initialize() {
        bloodPressureChecker = new BloodPressureChecker();
        bmiCalculator = new BMICalculator();
        bloodGlucoseChecker = new BloodGlucoseChecker();
        cholesterolChecker = new CholesterolChecker();
        triglyceridesChecker = new TriglyceridesChecker();

        analyzeButton.setOnAction(e -> analyzeInputValues());
        saveButton.setOnAction(e -> saveHealthReport());
        openButton.setOnAction(e -> loadHealthReport());
    }

    private void analyzeInputValues() {
        String bloodPressure = textField1.getText();
        String bmi = textField2.getText();
        String bloodGlucose = textField3.getText();
        String cholesterol = textField4.getText();
        String triglycerides = textField5.getText();

        //This is where the checks happen
        String bloodPressureResult = bloodPressureChecker.checkBloodPressure(Double.parseDouble(bloodPressure));
        String bmiResult = bmiCalculator.checkBMI(Double.parseDouble(bmi));
        String bloodGlucoseResult = bloodGlucoseChecker.checkBloodGlucose(Double.parseDouble(bloodGlucose));
        String cholesterolResult = cholesterolChecker.checkCholesterol(cholesterol);
        String triglyceridesResult = triglyceridesChecker.checkTriglycerides(Double.parseDouble(triglycerides));

        String notification = "Health Assessment Results:\n";
        notification += "Blood Pressure: " + bloodPressureResult + "\n";
        notification += "BMI: " + bmiResult + "\n";
        notification += "Blood Glucose: " + bloodGlucoseResult + "\n";
        notification += "Cholesterol: " + cholesterolResult + "\n";
        notification += "Triglycerides: " + triglyceridesResult;

        textArea.setText(notification);

        if (bloodPressureResult.equals("Very severe")) {
            showWarningNotification("Blood Pressure");
        }
        if (bmiResult.equals("Underweight") || bmiResult.equals("Obese")) {
            showWarningNotification("BMI");
        }
        if (bloodGlucoseResult.equals("Out of control") || bloodGlucoseResult.equals("Poor")) {
            showWarningNotification("Blood Glucose");
        }
        if (cholesterolResult.equals("Not Optimal")) {
            showWarningNotification("Cholesterol");
        }
        if (triglyceridesResult.equals("Optimal")) {
            showWarningNotification("Triglycerides");
        }
    }

    private void saveHealthReport() {
        String filePath = filepathField.getText().trim();
        if (!filePath.isEmpty()) {
            HealthReport healthReport = new HealthReport();
            healthReport.setBloodPressure(textField1.getText());
            healthReport.setBmi(textField2.getText());
            healthReport.setBloodGlucose(textField3.getText());
            healthReport.setCholesterol(textField4.getText());
            healthReport.setTriglycerides(textField5.getText());

            saveHealthReportToXml(healthReport, filePath);
        } else {
            showSaveErrorNotification("Please provide a valid file path!");
        }
    }

    public void loadHealthReport() {
        String filePath = filepathField.getText().trim();
        if (!filePath.isEmpty()) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    JAXBContext context = JAXBContext.newInstance(HealthReport.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    HealthReport report = (HealthReport) unmarshaller.unmarshal(file);

                    textField1.setText(report.getBloodPressure());
                    textField2.setText(report.getBmi());
                    textField3.setText(report.getBloodGlucose());
                    textField4.setText(report.getCholesterol());
                    textField5.setText(report.getTriglycerides());

                    showLoadSuccessNotification(filePath);
                } else {
                    showLoadErrorNotification("File does not exist!");
                }
            } catch (JAXBException e) {
                showLoadErrorNotification("Error loading the file!");
            }
        } else {
            showLoadErrorNotification("Please provide a valid file path!");
        }
    }

    public void saveHealthReportToXml(HealthReport report, String filePath) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Health Report");
            fileChooser.setInitialFileName("HealthReport.xml");

            File selectedFile = fileChooser.showSaveDialog(new Stage()); //allows you to type a file path and save

            if (selectedFile != null) {
                JAXBContext context = JAXBContext.newInstance(HealthReport.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(report, selectedFile);

                showSaveSuccessNotification(selectedFile.getAbsolutePath());
            }
        } catch (JAXBException ex) {
            showSaveErrorNotification("An error occurred while saving the health assessment report.");
        }
    }

    private void showWarningNotification(String parameter) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Health Parameter Warning");
        alert.setHeaderText(null);
        alert.setContentText("The " + parameter + " reading is far out of bounds.\nPlease see a doctor or consult a health professional.");
        alert.showAndWait();
    }

    private void showSaveSuccessNotification(String filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Health Report Saved");
        alert.setHeaderText(null);
        alert.setContentText("The health assessment report has been saved successfully.\nFile location: " + filePath);
        alert.showAndWait();
    }

    private void showSaveErrorNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Save Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showLoadSuccessNotification(String filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Health Report Loaded");
        alert.setHeaderText(null);
        alert.setContentText("The health assessment report has been loaded successfully.\nFile location: " + filePath);
        alert.showAndWait();
    }

    private void showLoadErrorNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Load Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
