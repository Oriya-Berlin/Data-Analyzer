package Controllers;

import Classes.AlertsMaker;
import Classes.CanvasPane;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;



public class Controller {

    // TODO: build class for initialize view
    @FXML
    AnchorPane mainWindow = new AnchorPane();
    @FXML
    TabPane tabPane = new TabPane();
    @FXML
    CanvasPane canvas = new CanvasPane();


    public GraphController graph;
    public static Controller controller = null;

    double xCoordinate;
    double yCoordinate;



    @FXML
    public void setOnDragDroppedHandle(DragEvent event) {

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage()){
            xCoordinate = event.getX() - 135;
            yCoordinate = event.getY() - 135;
        }
    }



    @FXML
    public void setOnDragOverHandle(DragEvent event) {
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node
         * and if it has a string data */
        if (event.getGestureSource() != canvas &&
                event.getDragboard().hasImage()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
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

        boolean isTableAdded = true;
        String btnId = ((Button) event.getGestureSource()).getId();

        switch (btnId) {
            case "table":
                if (isTableAdded)
                    graph = new GraphController("LINE_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;
        }
    }



    @FXML
    public void initialize() {
        controller = this;

//        separator.prefWidthProperty().bind(mainBoard.widthProperty());
        tabPane.prefWidthProperty().bind(mainWindow.widthProperty());
        canvas.prefWidthProperty().bind(mainWindow.widthProperty());
        canvas.prefHeightProperty().bind(mainWindow.heightProperty());
    }



    public static Controller getInstance(){
        return controller;
    }


}
