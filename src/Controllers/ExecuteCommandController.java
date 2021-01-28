package Controllers;

import Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class ExecuteCommandController {


    @FXML
    ComboBox<String> comboBox;
    @FXML
    TextArea sqlCommandTextArea;
    @FXML
    TableView<GeneralConnection> framesTable;


    public static ExecuteCommandController instance = null;

    // TODO: need to check if to put that in 'initialize' function
    TablesRegistry tablesRegistryInstance = TablesRegistry.getInstance();
    ConnectionsRegistry connectionsRegistry = ConnectionsRegistry.getInstance();

    @FXML
    public void initialize() throws SQLException {

        instance = this;

        // add tables name to combo box
        HashMap<String,AnalyzerTableView> tables = tablesRegistryInstance.getItems();

        for(AnalyzerTableView table: tables.values())
            comboBox.getItems().add(table.toString());


        // add all the connections to frames table
        initializeFramesTable();
        HashMap<String, GeneralConnection> connections = connectionsRegistry.getItems();

        for(GeneralConnection conn: connections.values())
            framesTable.getItems().add(conn);

    }


    @FXML
    private void executeCommandBtn(ActionEvent actionEvent) throws SQLException {

        GeneralConnection selectedConnection = framesTable.getSelectionModel().getSelectedItem();
        String command = sqlCommandTextArea.getText();
        String selectedTableName = comboBox.getValue();
        AnalyzerTableView selectedTable = tablesRegistryInstance.get(selectedTableName);

        ResultSet rs = selectedConnection.setCommandAndGetResultSet(command);
        selectedTable.setData(rs);
        // TODO: to be continue...

        // close the window
        Stage window = (Stage) ((Node)actionEvent.getTarget()).getScene().getWindow();
        window.close();
    }


    @FXML
    private void cancelBtn(ActionEvent actionEvent){
        // close the window
        Stage window = (Stage) ((Node)actionEvent.getTarget()).getScene().getWindow();
        window.close();
    }


    public static ExecuteCommandController getInstance(){
        return instance;
    }


    public ComboBox<String> getComboBox(){
        return comboBox;
    }


    private void initializeFramesTable(){

        TableColumn connName = new TableColumn<>("Conn Name");
        connName.setCellValueFactory(new PropertyValueFactory<>("connectionName"));

        TableColumn dbType = new TableColumn<>("DB Type");
        dbType.setCellValueFactory(new PropertyValueFactory<>("DbType"));

        TableColumn address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("connectionString"));

        TableColumn isConnected = new TableColumn<>("Connected");
        isConnected.setCellValueFactory(new PropertyValueFactory<>("isConnected"));



        framesTable.getColumns().addAll(connName, dbType, address, isConnected);
    }



}
