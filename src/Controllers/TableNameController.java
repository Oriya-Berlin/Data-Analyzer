package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Random;



public class TableNameController {


    @FXML
    TextField tableName_tf;
    @FXML
    Button okBtn;

    String tableName = "";

    public static TableNameController tableNameController = null;



    public static TableNameController getInstance(){
        return tableNameController;
    }



    @FXML
    public void initialize() {
        tableNameController = this;
    }



    @FXML
    private void okBtnClicked(ActionEvent event){

        tableName = tableName_tf.getText();

        if(tableName.equals("") || tableName == null){
            Random random = new Random();
            tableName = "table_" + String.format("%05d", random.nextInt(100000));
        }

        Stage window = (Stage) ((Node)event.getTarget()).getScene().getWindow();
        window.close();
    }



    public String getTableName(){
        return tableName;
    }
}
