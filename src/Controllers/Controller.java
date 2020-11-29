package Controllers;

import Classes.AlertsMaker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;



public class Controller {

    @FXML
    public static AnchorPane mainBoard = null;
    public GraphController graph;

    public static Controller controller = null;

    public AnchorPane getMainBoard(){
        return mainBoard;
    }

    @FXML
    public void onDragDetected(MouseEvent event){
        Button buttonImage = (Button)event.getSource();
        Dragboard db = buttonImage.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        Node node = buttonImage.getGraphic();
        ImageView imageView = (ImageView) node;
        Image image = imageView.getImage();
        content.putImage(image);
        db.setContent(content);
        event.consume();
    }


    @FXML
    public void onDragDone(DragEvent event) throws IOException {

        System.out.println("dasda");
        System.out.println(event.getX());
        boolean isTableAdded = true;
        String btnId = ((Button) event.getGestureSource()).getId();

        switch (btnId) {
            case "table":
                if (isTableAdded)
                    graph = new GraphController("LINE_CHART", 200, 200);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;
        }
    }


    @FXML
    public void initialize() {
        controller = this;

    }


    public static Controller getInstance(){
        return controller;
    }

}
