package Controllers;

import Classes.ChartsDataBar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ChartsBarController {

    @FXML
    HBox DataBarWindow;
    @FXML
    ListView textField;
    @FXML
    Button DataBarOKbtn;


    public static ChartsBarController chartsBarController;
    public ChartsDataBar chartsDataBar = null;


    @FXML
    public void initialize() {
        chartsBarController = this;
        chartsDataBar = new ChartsDataBar(DataBarWindow, textField, DataBarOKbtn);
    }


    public static ChartsBarController getInstance(){
        return chartsBarController;
    }



    //dataBar_OK_btn function
    @FXML
    private void dataBar_OK_btn(){

    }
}
