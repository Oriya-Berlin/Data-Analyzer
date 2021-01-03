package Controllers;

import Classes.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Controller {

    // TODO: build class for initialize view
    @FXML
    AnchorPane mainWindow = new AnchorPane();
    @FXML
    TabPane tabPane = new TabPane();
    @FXML
    CanvasPane canvas = new CanvasPane();
    @FXML
    Separator separator = new Separator();

    public GraphController graph;
    public RoundGraphController roundGraph;
    public static Controller controller = null;
    public ArrayList<AnalyzerTableView> tables = new ArrayList<AnalyzerTableView>();

    double xCoordinate;
    double yCoordinate;

    boolean isTableAdded = false;


    @FXML
    public void setOnDragDroppedHandle(DragEvent event) {

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage()){
            xCoordinate = event.getX() - 135;
            yCoordinate = event.getY() - 135;
        }
        String btnId = ((Button) event.getGestureSource()).getId();
        if(btnId.equals("table"))
            isTableAdded = true;

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

//        GraphController graph;

        String btnId = ((Button) event.getGestureSource()).getId();

        switch (btnId) {

            case "table":
                if (isTableAdded) {
                    //TODO: add table window here
                    AnalyzerTableView table = new AnalyzerTableView();
                    table = createDefaultTable();
                    table.setLayoutX(xCoordinate);
                    table.setLayoutY(yCoordinate);


                    DragResizeMod.makeResizable(table);
                    canvas.addChildren(table);
                    setTableNameWindow();

                    TableNameController tableNameController = TableNameController.getInstance();
                    String tableName = tableNameController.getTableName();
                    table.setTableName(tableName);
                    tables.add(table);
                }
//                else
//                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "lineChartBtn":
                if (isTableAdded)
                    graph = new GraphController("LINE_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "stackedBarChartBtn":
                if (isTableAdded)
                    graph = new GraphController("STACKED_BAR_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "scatterChartBtn":
                if (isTableAdded)
                    graph = new GraphController("SCATTER_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "horizontalBarChartBtn":
                if (isTableAdded)
                    graph = new GraphController("HORIZONTAL_BAR_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "barChartBtn":
                if (isTableAdded)
                    graph = new GraphController("BAR_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "stackedAreaChartBtn":
                if (isTableAdded)
                    graph = new GraphController("STACKED_AREA_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "areaChartBtn":
                if (isTableAdded)
                    graph = new GraphController("AREA_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "ringChartBtn":
                if (isTableAdded)
                    roundGraph = new RoundGraphController("RING_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;

            case "pieChartBtn":
                if (isTableAdded)
                    roundGraph = new RoundGraphController("PIE_CHART", xCoordinate, yCoordinate);
                else
                    AlertsMaker.showErrorMessage("Invalid Action", "You must add table first.");
                break;
        }
    }



    @FXML
    public void initialize() {
        controller = this;

        separator.prefWidthProperty().bind(mainWindow.widthProperty());
        tabPane.prefWidthProperty().bind(mainWindow.widthProperty());
        canvas.prefWidthProperty().bind(mainWindow.widthProperty());
        canvas.prefHeightProperty().bind(mainWindow.heightProperty());
    }



    public static Controller getInstance(){
        return controller;
    }



    // data for example
    public AnalyzerTableView createDefaultTable() {

        AnalyzerTableView tableA = new AnalyzerTableView();
        ObservableList<Row> table_dataA;

        // populate the data for table A
        table_dataA = FXCollections.observableArrayList(new Row(new HashMap<String, Double>() {
            {
                put("1", 10000.0);
                put("2", 120000.0);
                put("3", 5000.0);
                put("4", 30000.0);
                put("5", 0.0);
            }
        }), new Row(new HashMap<String, Double>() {
            {
                put("1", 31000.0);
                put("2", 74000.0);
                put("3", 43000.0);
                put("4", 17000.0);
                put("5", 0.0);
            }
        }), new Row(new HashMap<String, Double>() {
            {
                put("1", 14000.0);
                put("2", 53000.0);
                put("3", 20000.0);
                put("4", 77000.0);
                put("5", 0.0);
            }
        }), new Row(new HashMap<String, Double>() {
            {
                put("1", 52000.0);
                put("2", 33000.0);
                put("3", 20000.0);
                put("4", 24000.0);
                put("5", 0.0);
            }
        }));

        // set tables columns for table A
        TableColumn<Row, String> columnA1 = new TableColumn<>("Att1");
        TableColumn<Row, String> columnA2 = new TableColumn<>("Att2");
        TableColumn<Row, String> columnA3 = new TableColumn<>("Att3");
        TableColumn<Row, String> columnA4 = new TableColumn<>("Att4");
        TableColumn<Row, String> columnA5 = new TableColumn<>("Att5");

        // set Row Cell Value Factory for each column so the can display the data
        columnA1.setCellValueFactory(new RowCellValueFactory(1));
        columnA2.setCellValueFactory(new RowCellValueFactory(2));
        columnA3.setCellValueFactory(new RowCellValueFactory(3));
        columnA4.setCellValueFactory(new RowCellValueFactory(4));
        columnA5.setCellValueFactory(new RowCellValueFactory(5));

        /* this piece of code will put index on every row */
        TableColumn<Row, Number> indexColumn = new TableColumn<>("#");
        indexColumn.setSortable(false);
        indexColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tableA.getItems().indexOf(column.getValue())));
        /* ---------------------------------------------- */

        /* this code will give us the option to select column with the mouse and get his name & index */
        /*
        tableA.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {

            if(newVal.getTableColumn() != null){
                tableA.getSelectionModel().selectRange(0, newVal.getTableColumn(), tableA.getItems().size(), newVal.getTableColumn());
                System.out.println("Selected TableColumn: "+ newVal.getTableColumn().getText());
                System.out.println("Selected column index: "+ newVal.getColumn());
            }
        });
        */
        /* ---------------------------------------------------------------------------------------- */

        tableA.setEditable(true);
        tableA.setMinWidth(100);
        tableA.setItems(table_dataA);
        tableA.getColumns().addAll(Arrays.asList(indexColumn, columnA1, columnA2, columnA3, columnA4, columnA5));
        tableA.getSelectionModel().setCellSelectionEnabled(true);

        DragResizeMod.makeResizable(tableA);


        return tableA;
    }



    @FXML
    private void BrowseDB() throws IOException {

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/TableSourceView.fxml"));
        Scene scene = new Scene(myLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Table Source");
        stage.setScene(scene);
        stage.show();

    }



    @FXML
    private void ExecuteCommand() throws IOException {

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/ExecuteCommandView.fxml"));
        Scene scene = new Scene(myLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Execute Command");
        stage.setScene(scene);
        stage.show();

    }



    private void setTableNameWindow() throws IOException {

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/TableNameView.fxml"));
        Scene scene = new Scene(myLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Table Name");
        stage.setScene(scene);
        stage.showAndWait();
    }


    public ArrayList<AnalyzerTableView> getTables(){
        return tables;
    }






















}
