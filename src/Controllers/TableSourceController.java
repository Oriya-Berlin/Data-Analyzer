package Controllers;

import Classes.GeneralConnection;
import Classes.MySQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableSourceController {


    @FXML
    TextField local_username_tf, local_password_tf, local_address_tf;
    @FXML
    TextField remote_username_tf, remote_password_tf, remote_address_tf;
    @FXML
    Button local_btn, remote_btn;
    @FXML
    ComboBox<String> LocalComboBox, RemoteComboBox;


    public static TableSourceController tableSourceController = null;
    public static Object DB_connection = null;
    public ArrayList<GeneralConnection> connections = new ArrayList<>();

    public enum ConnectionsType{
        MySQL,
        MSSQL,
        MongoDB,
    }

    // TODO: we need to add also oracle, sql server, etc
    // TODO: handle ports


    @FXML
    public void initialize() {
        tableSourceController = this;

        for (ConnectionsType type : ConnectionsType.values()) {
            RemoteComboBox.getItems().add(String.valueOf(type));
            LocalComboBox.getItems().add(String.valueOf(type));
        }
    }



    public static TableSourceController getInstance(){
        return tableSourceController;
    }



    @FXML
    private void Connect(ActionEvent actionEvent) throws SQLException {

        Button btn = (Button) actionEvent.getSource();
        String connectionType = "";
        String username = "";
        String password = "";
        String address = "";


        // TODO: we need to add input validation

        if(btn.getId().equals("local_btn"))
        {
            connectionType = LocalComboBox.getValue();
            System.out.println(connectionType);
            username = local_username_tf.getText();
            password = local_password_tf.getText();
            address = local_address_tf.getText();
        }

        if(btn.getId().equals("remote_btn"))
        {
            connectionType = RemoteComboBox.getValue();
            username = remote_username_tf.getText();
            password = remote_password_tf.getText();
            address = remote_address_tf.getText();
        }


        Stage window = (Stage) ((Node)actionEvent.getTarget()).getScene().getWindow();
        window.close();


        switch (connectionType){

            case "MySQL":
                DB_connection = new MySQL(address, username, password);
                //connections.add(DB_connection);
                break;

            case "MSSQL":
                // TODO...
                break;

            case "MongoDB":
                // TODO...
                break;
        }



        // TEST
//        ResultSet rs = ((MySQL) DB_connection).setCommandAndGetResultSet("select * from Persons");
//        System.out.println(((MySQL) DB_connection).isConnected());


    }




}
