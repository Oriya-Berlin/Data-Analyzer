package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class RoundGraphController {


    @FXML
    ListView dataBar_field;
    @FXML
    ListView y_Selection_field;
    @FXML
    TextField Legends_textField;
    @FXML
    Button select_Y_axis_btn, select_X_axis_btn;
    @FXML
    HBox x_Selection_field;
    @FXML
    RadioButton multiRadio, singleRadio;
    @FXML
    public ToggleGroup row_or_column, multi_or_single;
    @FXML
    public RadioButton colRadio, rowRadio;







    private HBox xAxisItem;
    private AnchorPane dataBarRoot;
    public Stage graphUIstage;
    public AnalyzerTableView selectedTable;


    public PieChart roundChart;
    public String graphType;
    public NumberAxis X_axis_NUMBER = new NumberAxis();
    public CategoryAxis X_axis_CATEGORY = new CategoryAxis();
    public NumberAxis Y_axis = new NumberAxis();

    // columns
    public ArrayList<TableColumn> selected_Y_columns = new ArrayList<>();
    public TableColumn selected_X_column = null;
    public String selected_X_column_NAME = null;

    // rows
    public ArrayList<Integer> selected_Y_rows = new ArrayList<>();
    public Integer selected_X_row = null;

    public ArrayList<String> selected_legends = new ArrayList<>();

    boolean user_selecting_Y_axis = false;
    boolean user_selecting_X_axis = false;

    private boolean is_Y_selected = false; // TODO: also we need to put them on clean function
    private boolean is_X_selected = false;

    public static RoundGraphController roundGraphController;
    Controller controller = Controller.getInstance();

    /* -------------------------------------------------------------- */
    // round charts stuff
    public ArrayList<Number> finalRoundChartValues = new ArrayList<>();
    /* -------------------------------------------------------------- */







    // constructor
    public RoundGraphController(String graphType, double layout_X, double layout_Y) throws IOException {

        this.graphType = graphType;
        this.graphUIstage = new Stage();

        switch (graphType) {

            case "RING_CHART":
                this.roundChart = new RingChart();
                createDefaultChart(roundChart);
                displayRoundChart(layout_X, layout_Y);
                disPlayGraphInterface();
                disableGraphInterfaceElements();
                break;

            case "PIE_CHART":
                this.roundChart = new PercentagePieChart();
                createDefaultChart(roundChart);
                displayRoundChart(layout_X, layout_Y);
                disPlayGraphInterface();
                disableGraphInterfaceElements();
                break;
        }
    }



    public void disPlayGraphInterface() throws IOException {

//        graphUIstage = new Stage();
        this.graphUIstage.setOnCloseRequest(event -> cancelTableAbilities());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/RoundChartInterface.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        this.graphUIstage.setTitle("Graph Builder");
        this.graphUIstage.setScene(new Scene(root));
        this.graphUIstage.show();
    }



    public void createDefaultChart(PieChart roundChart){

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        for (int i=1; i<6; i++)
            data.add(new PieChart.Data("Legend" + i, 20));

        roundChart.setData(data);
    }



    public void displayRoundChart(double layout_X, double layout_Y) {

        roundChart.setLayoutX(layout_X);
        roundChart.setLayoutY(layout_Y);
        roundChart.setPrefWidth(320);
        roundChart.setPrefHeight(280);

        // when chart get clicked, it will get focused and get some border line
        roundChart.setOnMouseClicked(event -> roundChart.requestFocus());
        roundChart.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue)
                roundChart.setStyle("-fx-border-color: #039ED3 ; -fx-border-width: 1 ; ");
            else
                roundChart.setStyle("-fx-border-color: blue ; -fx-border-width: 0 ; ");
        });

        DragResizeMod.makeResizable(roundChart);
        controller.canvas.addChildren(roundChart);
    }



    private void disableGraphInterfaceElements(){
        x_Selection_field.setDisable(true);
        multiRadio.setDisable(true);
    }



    public void updateGraphInterfaceFields_roundChart_columns(){

        y_Selection_field.getItems().clear();
        Legends_textField.clear();

        TableColumn column = selected_Y_columns.get(0);
        int column_length = column.getTableView().getItems().size();

        Double value;

        for (int i=0; i<column_length; i++)
        {
            value = Double.parseDouble(column.getCellObservableValue(i).getValue().toString());
            Legends_textField.setText(Legends_textField.getText() + "Legend" + i + "; ");
            y_Selection_field.getItems().add(value);
        }
    }



    private void updateGraphInterfaceFields_roundChart_rows(){

        y_Selection_field.getItems().clear();
        Legends_textField.clear();

        int row_length = selectedTable.getColumns().size();
        int rowIndex = selected_Y_rows.get(0);
        Row row = selectedTable.getItems().get(rowIndex);
        Double value;

        for (int i=0; i<row_length; i++)
        {
            TableColumn column = selectedTable.getColumns().get(i);
            value = Double.parseDouble(column.getCellObservableValue(row).getValue().toString());
            Legends_textField.setText(Legends_textField.getText() + getColumnName(column) + "; ");
            y_Selection_field.getItems().add(value);

        }
    }



    private static String getColumnName(TableColumn column){

        VBox vBox = (VBox) column.getGraphic();
        Label label = (Label) vBox.getChildren().get(0);

        return label.getText();
    }



    private void extractValuesFrom_Y_textField(){

        for(Object val: y_Selection_field.getItems())
            finalRoundChartValues.add((Number) val);
    }



    // disable the abilities that we gave the table in the functions 'addRowEventHandler' and 'addColumnEventHandler'
    private void cancelTableAbilities() {

        dataBarRoot = null;
        row_or_column = null;
        colRadio = null;
        rowRadio = null;
    }

























}
