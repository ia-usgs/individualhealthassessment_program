module com.example.individualhealthassessment_program {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires jakarta.xml.bind;
    requires java.xml.bind; // Use javax.xml.bind; for Java versions before 9

    opens com.example.individualhealthassessment_program to javafx.fxml;
    exports com.example.individualhealthassessment_program;
}
