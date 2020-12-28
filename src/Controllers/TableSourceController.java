package Controllers;

import Classes.MySQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableSourceController {

    @FXML
    TextField local_username_tf, local_password_tf, local_adress_tf;
    @FXML
    TextField remote_username_tf, remote_password_tf, remote_adress_tf;
    @FXML
    Button local_btn, remote_btn;


    public static TableSourceController tableSourceController = null;
    public static Object DB_connection = null;

    // TODO: we need to add also oracle, sql server, etc
    // TODO: handle ports


    @FXML
    public void initialize() {
        tableSourceController = this;
    }



    public static TableSourceController getInstance(){
        return tableSourceController;
    }



    @FXML
    private void Connect(ActionEvent actionEvent) throws SQLException {

        Button btn = (Button) actionEvent.getSource();

        String username = "";
        String password = "";
        String adress = "";


        if(btn.getId().equals("local_btn")){

            username = local_username_tf.getText();
            password = local_password_tf.getText();
            adress = local_adress_tf.getText();
        }

        if(btn.getId().equals("remote_btn")){
            username = remote_username_tf.getText();
            password = remote_password_tf.getText();
            adress = remote_adress_tf.getText();
        }


        // TODO: add 'switch' for all the others
        DB_connection = new MySQL(adress, username, password);

        // TEST
//        ResultSet rs = ((MySQL) DB_connection).setCommandAndGetResultSet("select * from Persons");
//        System.out.println(((MySQL) DB_connection).isConnected());


    }




}
