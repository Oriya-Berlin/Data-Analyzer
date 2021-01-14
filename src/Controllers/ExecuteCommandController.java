package Controllers;

import Classes.AnalyzerTableView;
import Classes.Frame;
import Classes.MySQL;
import Classes.Row;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class ExecuteCommandController {


    @FXML
    ComboBox comboBox;
    @FXML
    TextArea sqlCommandTextArea;
    @FXML
    TableView<Object> tableFrames; // here we need to put connections in <>



    @FXML
    public void initialize() {

        Controller controller = Controller.getInstance();
        ArrayList<AnalyzerTableView> tables = controller.getTables();

        for(AnalyzerTableView table : tables)
            comboBox.getItems().add(table.getTableName());


        TableSourceController tableSourceController = TableSourceController.getInstance();
        ArrayList<Frame> frames = tableSourceController.getFrames();

        for (Frame frane: frames){
            tableFrames.getItems().addAll(1,3,4,5);  // ((MySQL)connection).
        }
//        Row row = tableFrames.getItems().get(2); // find out how to add row
        // build function to get row from any connection object
        tableFrames.getItems().addAll(1,2,3,4);
    }


    @FXML
    private void executeCommandBtn(){
        String command = sqlCommandTextArea.getText();
    }




}
