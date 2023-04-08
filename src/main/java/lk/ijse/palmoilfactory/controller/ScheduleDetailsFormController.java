package lk.ijse.palmoilfactory.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class ScheduleDetailsFormController {

    @FXML
    private Pane scheduleDetailsPane;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        scheduleDetailsPane.setVisible(false);
    }

}
