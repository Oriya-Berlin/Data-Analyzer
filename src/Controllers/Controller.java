package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Controller {

    public static Controller controller = null;


    @FXML
    public void onDragDetected(DragEvent event){
        Button btn = (Button)event.getSource();
        Dragboard db = btn.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        // below seems to be wrong
        //content.put(dataFormat,btn_1.toString());
        db.setContent(content);

        event.consume();

    }


    @FXML
    public void onDragDone(){

    }


    @FXML
    public void initialize() {
        controller = this;
    }


    public static Controller getInstance(){
        return controller;
    }

}
