package Controllers;

import Classes.AnalyzerTableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class ExecuteCommandController {


    @FXML
    ComboBox comboBox;
    @FXML
    TextArea sqlCommandTextArea;


    @FXML
    public void initialize() {

        Controller controller = Controller.getInstance();
        ArrayList<AnalyzerTableView> tables = controller.getTables();

        for(AnalyzerTableView table : tables)
            comboBox.getItems().add(table.getTableName());

    }


    @FXML
    private void executeCommandBtn(){
        String command = sqlCommandTextArea.getText();
    }




}
