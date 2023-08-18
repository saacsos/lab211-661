package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.models.Student;
import ku.cs.models.StudentList;
import ku.cs.services.*;

import java.io.IOException;

public class StudentListController {
    @FXML
    private ListView<Student> studentListView;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label scoreLabel;
    @FXML private Label errorLabel;
    @FXML private TextField giveScoreTextField;

    @FXML private AnchorPane studentInfoPane;
    @FXML private Button closePaneButton;


    private StudentList studentList;
    private Student selectedStudent;

    private Datasource<StudentList> datasource;

    @FXML
    public void initialize() {
        clearStudentInfo();
//        StudentHardCodeDatasource datasource = new StudentHardCodeDatasource();
//        Datasource<StudentList> datasource = new StudentListHardCodeDatasource();
        // เปลี่ยนเป็นการอ่านข้อมูลทั้งหมดจากไฟล์นอกโปรเจค
//        Datasource<StudentList> datasource = new StudentListFileDatasource("data", "student-list.csv");
        datasource = new StudentListFileDatasource("data", "student-list.csv");
        studentList = datasource.readData();
        showList(studentList);
        studentInfoPane.setVisible(false);
        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue == null) {
                    clearStudentInfo();
                    selectedStudent = null;
                } else {
                    showStudentInfo(newValue);
                    selectedStudent = newValue;
                }
            }
        });

        closePaneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                studentInfoPane.setVisible(false);
                selectedStudent = null;
                studentListView.getSelectionModel().clearSelection();
            }
        });
    }

    private void showList(StudentList studentList) {
        studentListView.getItems().clear();
        studentListView.getItems().addAll(studentList.getStudents());
    }

    private void showStudentInfo(Student student) {
        studentInfoPane.setVisible(true);
        idLabel.setText(student.getId());
        nameLabel.setText(student.getName());
        scoreLabel.setText(String.format("%.2f", student.getScore()));
    }

    private void clearStudentInfo() {
        idLabel.setText("");
        nameLabel.setText("");
        scoreLabel.setText("");
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("hello");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onGiveScoreButtonClick() {
        if (selectedStudent != null) {
            String scoreText = giveScoreTextField.getText();
            String errorMessage = "";

            try {
                double score = Double.parseDouble(scoreText);
                studentList.giveScoreToId(selectedStudent.getId(), score);
                showStudentInfo(selectedStudent);

                // เขียนข้อมูลลงในไฟล์เมื่อมีการเปลี่ยนแปลงของข้อมูล
                datasource.writeData(studentList);

                showList(studentList);
            } catch (NumberFormatException e) {
                errorMessage = "Please insert number value";
                errorLabel.setText(errorMessage);
            } finally {
                if (errorMessage.equals("")) {
                    giveScoreTextField.setText("");
                }
            }
        } else {
            giveScoreTextField.setText("");
            errorLabel.setText("");
        }
    }
}
