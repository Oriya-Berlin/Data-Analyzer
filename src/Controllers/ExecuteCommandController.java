package Controllers;

import Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;


import java.util.HashMap;


public class ExecuteCommandController {


    @FXML
    ComboBox<String> comboBox;
    @FXML
    TextArea sqlCommandTextArea;
    @FXML
    TableView<GeneralConnection> tableFrames;


    public static ExecuteCommandController instance = null;


    @FXML
    public void initialize() {

        instance = this;

        TablesRegistry instance = TablesRegistry.getInstance();
        HashMap<String,AnalyzerTableView> tables = instance.getItems();

        for(String tableName : tables.keySet())
            comboBox.getItems().add(tableName);



        ConnectionsRegistry connectionsRegistry = ConnectionsRegistry.getInstance();
        HashMap<String, GeneralConnection> connections = connectionsRegistry.getItems();

        for(GeneralConnection conn: connections.values()){
            System.out.println();
            tableFrames.getItems().add(conn);
        }

    }


    @FXML
    private void executeCommandBtn(){
        String command = sqlCommandTextArea.getText();
        // TODO: to be continue...
    }


    public static ExecuteCommandController getInstance(){
        return instance;
    }


    public ComboBox<String> getComboBox(){
        return comboBox;
    }
}
