package Controllers;

import Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class GraphController {

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


    // TODO: that problem doesn't fixed yet
    // the dataBar position on Canvas
    private static final double dataBarYPos = 566, dataBarXPos = 400;


////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private HBox xAxisItem;
    private AnchorPane dataBarRoot;
    public Stage graphUIstage;
    public AnalyzerTableView selectedTable;

    public XYChart chart;
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

    public static GraphController graphController;
    Controller controller = Controller.getInstance();

    /* -------------------------------------------------------------- */
    // round charts stuff
    public ArrayList<Number> finalRoundChartValues = new ArrayList<>();
    /* -------------------------------------------------------------- */

    // constructor
    public GraphController(String graphType, double layout_X, double layout_Y) throws IOException {

        this.graphType = graphType;
        this.graphUIstage = new Stage();

        switch (graphType) {

            case "LINE_CHART":
                this.chart = new LineChart<Number, Number>(X_axis_NUMBER, Y_axis); ///
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                //disPlayGraphInterface_ttt();
                break;

            case "STACKED_BAR_CHART":
                this.chart = new StackedBarChart<String, Number>(X_axis_CATEGORY, Y_axis);
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                break;

            case "SCATTER_CHART":
                this.chart = new ScatterChart<>(X_axis_NUMBER, Y_axis);
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                break;

            case "AREA_CHART":
                this.chart = new AreaChart<>(X_axis_NUMBER, Y_axis);
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                break;

            case "BAR_CHART":
                this.chart = new BarChart<>(X_axis_CATEGORY, Y_axis);
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                break;

            case "STACKED_AREA_CHART":
                this.chart = new StackedAreaChart<>(X_axis_NUMBER, Y_axis);
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                break;

            case "HORIZONTAL_BAR_CHART":
                this.chart = new BarChart<>(Y_axis, X_axis_CATEGORY);
                //Y_axis.setTickLabelRotation(0);
                displayChart(layout_X, layout_Y);
                disPlayGraphInterface();
                break;

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



    private void displayChart(double layout_X, double layout_Y) {

        chart.setLayoutX(layout_X);
        chart.setLayoutY(layout_Y);
        chart.setPrefWidth(320);
        chart.setPrefHeight(280);

        // when chart get clicked, it will get focused and get some border line
        chart.setOnMouseClicked(event -> chart.requestFocus());
        chart.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue)
                chart.setStyle("-fx-border-color: #039ED3 ; -fx-border-width: 1 ; ");
            else
                chart.setStyle("-fx-border-color: blue ; -fx-border-width: 0 ; ");
        });

        DragResizeMod.makeResizable(chart);
        //controller.canvas.getChildren().add(chart);

        controller.canvas.addChildren(chart);

    }


    public void disPlayGraphInterface() throws IOException {

//        graphUIstage = new Stage();
        graphUIstage.setOnCloseRequest(event -> cancelTableAbilities());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ChartInterface.fxml"));
        loader.setController(this);  //////////////////////////////////////////
        AnchorPane root = loader.load();
        graphUIstage.setTitle("Graph Builder");
        graphUIstage.setScene(new Scene(root));
        graphUIstage.show();
    }
    // #################################################################
    // TEST
    public void disPlayGraphInterface_ttt() throws IOException {

//        graphUIstage = new Stage();
        graphUIstage.setOnCloseRequest(event -> cancelTableAbilities());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/3DChartsInterface.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        graphUIstage.setTitle("Graph Builder");
        graphUIstage.setScene(new Scene(root));
        graphUIstage.show();
    }
    // #################################################################


    // update dataBar when user choose COLUMNS as axes
    public void updateDataBar_columns(AnalyzerTableView table, String name, int selected_index){

        if(selected_legends.contains(name) && user_selecting_Y_axis)  // user_selecting_Y_axis,
            return;

        // user choose now Y axis
        if(user_selecting_Y_axis)
        {
            // if columns:
            if (row_or_column.getSelectedToggle() == colRadio)
            {
                // if multi:
                if (multi_or_single.getSelectedToggle() == multiRadio) {
                    selected_Y_columns.add(table.getColumns().get(selected_index)); // maybe we mee to put it on initialize
                    selected_legends.add(name);
                    dataBar_field.getItems().add(name + ";");
                }
                // if single:
                if (multi_or_single.getSelectedToggle() == singleRadio)
                {
                    selected_Y_columns.clear();
                    selected_legends.clear();
                    dataBar_field.getItems().clear();
                    selected_Y_columns.add(table.getColumns().get(selected_index));
                    selected_legends.add(name);
                    dataBar_field.getItems().add(name + ";");
                }
            }
        }

        // user choose now X axis
        if(user_selecting_X_axis)
        {
            // if columns:
            if (row_or_column.getSelectedToggle() == colRadio)
            {
                selected_X_column = table.getColumns().get(selected_index);
                selected_X_column_NAME = name;
                dataBar_field.getItems().clear();
                dataBar_field.getItems().add(name);
            }
        }

    }



    // update dataBar when user choose ROWS as axes
    public void updateDataBar_rows(AnalyzerTableView table, int selected_index){

        if(selected_Y_rows.contains(selected_index) && user_selecting_Y_axis)
            return;

        selectedTable = table;

        // user choose now Y axis
        if(user_selecting_Y_axis){
            // if rows:
            if (row_or_column.getSelectedToggle() == rowRadio)
            {
                // if multi:
                if (multi_or_single.getSelectedToggle() == multiRadio)
                {
                    selected_Y_rows.add(selected_index);
                    selected_legends.add(String.valueOf(selected_index));
                    dataBar_field.getItems().add(String.valueOf(selected_index) + ";");
                }
                // if single:
                if (multi_or_single.getSelectedToggle() == singleRadio)
                {
                    selected_Y_rows.clear();
                    selected_legends.clear();
                    dataBar_field.getItems().clear();

                    selected_Y_rows.add(selected_index);
                    selected_legends.add(String.valueOf(selected_index));
                    dataBar_field.getItems().add(String.valueOf(selected_index) + ";");
                }
            }
        }

        // user choose now X axis
        if(user_selecting_X_axis)
        {
            // if rows:
            if (row_or_column.getSelectedToggle() == rowRadio)
            {
                selected_X_row = selected_index;
                dataBar_field.getItems().clear();
                dataBar_field.getItems().add(selected_index);
            }
        }


    }



    @FXML
    private void cleanGraphInterfaceFields(){

        // clear fields
        Legends_textField.clear();
        x_Selection_field.getChildren().clear();
        y_Selection_field.getItems().clear();

        // initiate all the arrays and variables
        selected_Y_columns = new ArrayList<>();
        selected_X_column = null;
        selected_X_column_NAME = null;

        selected_Y_rows = new ArrayList<>();
        selected_X_row = null;

        selected_legends = new ArrayList<>();
    }



    @FXML
    public void dataBar_OK_btn(){

        dataBarRoot.setVisible(false);

        // if ROUND CHARTS
        if(graphType.equals("PIE_CHART") || graphType.equals("RING_CHART"))
        {

            if (row_or_column.getSelectedToggle() == colRadio)
            {
                updateGraphInterfaceFields_roundChart_columns();
                selected_legends = extractLegendsFromLegendTextField();
                ChartsDataHandling.columnsData(graphType, selected_X_column, selected_Y_columns, selected_legends, finalRoundChartValues);
            }
            if (row_or_column.getSelectedToggle() == rowRadio)
            {
                updateGraphInterfaceFields_roundChart_rows(); ////////////////////////////////
                ChartsDataHandling.rowsData(graphType, selectedTable, selected_X_row, selected_Y_rows, selected_legends, finalRoundChartValues);
            }

        }else {

            // unable and disable buttons according to user selection
            XY_SelectionButtonsHandling();

            if (row_or_column.getSelectedToggle() == colRadio) {
                ChartsDataHandling.columnsData(graphType, selected_X_column, selected_Y_columns, selected_legends, finalRoundChartValues);
                updateGraphInterfaceFields_columns(selected_X_column_NAME, selected_legends);
            }

            if (row_or_column.getSelectedToggle() == rowRadio) {
                ChartsDataHandling.rowsData(graphType, selectedTable, selected_X_row, selected_Y_rows, selected_legends, finalRoundChartValues);
                updateGraphInterfaceFields_rows(selected_X_row, selected_legends);
            }
        }

        graphUIstage.show();

    }



    private void displayDataBar() throws IOException {
        graphUIstage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/DataBar.fxml"));
        loader.setController(this);
        dataBarRoot = loader.load();
        dataBarRoot.setLayoutX(dataBarXPos);
        dataBarRoot.setLayoutY(dataBarYPos);
        //controller.target.getAnchorPane().getChildren().add(dataBarRoot);

        // set placeholder on data bar field according to user selection (row/column)
        if(row_or_column.getSelectedToggle() == rowRadio)
            dataBar_field.setPlaceholder(new Label("Select Row"));
        else
            dataBar_field.setPlaceholder(new Label("Select Column"));
    }



    @FXML
    private void XY_Selection_btn(MouseEvent mouseEvent) throws IOException {

        graphUIstage.close();
        String Xbtn_or_Ybtn = ((Button) mouseEvent.getSource()).getId();

        // y
        if(Xbtn_or_Ybtn.equals("select_Y_axis_btn")) {
            user_selecting_Y_axis = true;
            user_selecting_X_axis = false;
        }
        // x
        if(Xbtn_or_Ybtn.equals("select_X_axis_btn")) {
            user_selecting_Y_axis = false;
            user_selecting_X_axis = true;
        }

        displayDataBar();
    }



    private void updateGraphInterfaceFields_columns(String selected_X_column_NAME, ArrayList<String> selected_legends){  // columns

        if(selected_X_column_NAME != null)
        {
            x_Selection_field.getChildren().clear();
            xAxisItem = createItem(selected_X_column_NAME);
            x_Selection_field.getChildren().add(xAxisItem);
        }

        y_Selection_field.getItems().clear();
        boolean first_time = true;
        for(Object name: selected_legends)
        {
            if(first_time)
                Legends_textField.setText(name + "; ");
            else
                Legends_textField.setText(Legends_textField.getText() + name + "; ");

            first_time = false;
            y_Selection_field.getItems().add(name + "; ");
        }
    }



    private void updateGraphInterfaceFields_rows(Integer selected_X_row, ArrayList<String> selected_legends){

        if(selected_X_row != null)
        {
            x_Selection_field.getChildren().clear();
            xAxisItem = createItem(String.valueOf(selected_X_row));
            x_Selection_field.getChildren().add(xAxisItem);
        }

        y_Selection_field.getItems().clear();
        boolean first_time = true;
        for(Object name: selected_legends)
        {
            if(first_time)
                Legends_textField.setText(name + "; ");
            else
                Legends_textField.setText(Legends_textField.getText() + name + "; ");

            first_time = false;
            y_Selection_field.getItems().add(name + "; ");
        }
    }



    @FXML
    private void graphInterface_OK_btn(){

        ArrayList<String> final_legends = extractLegendsFromLegendTextField();

        if(graphType.equals("PIE_CHART") || graphType.equals("RING_CHART"))
        {
            if (row_or_column.getSelectedToggle() == rowRadio)
            {
                extractValuesFrom_Y_textField();
                ChartsDataHandling.rowsData(graphType, selectedTable, selected_X_row, selected_Y_rows, final_legends, finalRoundChartValues);
            }
            if (row_or_column.getSelectedToggle() == colRadio)
            {
                extractValuesFrom_Y_textField();
                ChartsDataHandling.columnsData(graphType, selected_X_column, selected_Y_columns, final_legends, finalRoundChartValues);
            }

        }else{

            // if columns
            if (row_or_column.getSelectedToggle() == colRadio)
                ChartsDataHandling.columnsData(graphType, selected_X_column, selected_Y_columns, final_legends, finalRoundChartValues);

            // if rows
            if (row_or_column.getSelectedToggle() == rowRadio)
                ChartsDataHandling.rowsData(graphType, selectedTable, selected_X_row, selected_Y_rows, final_legends, finalRoundChartValues);
        }

        //cancelTableAbilities();
        graphUIstage.close();
        cancelTableAbilities();

    }



    // unable and disable buttons according to user selection
    private void XY_SelectionButtonsHandling(){

        // columns
        if (row_or_column.getSelectedToggle() == colRadio)
        {
            // y
            if (user_selecting_Y_axis && selected_Y_columns.size() > 0)
            {
                select_Y_axis_btn.setDisable(true);
                select_X_axis_btn.setDisable(false);
            }
            // x
            if(user_selecting_X_axis && selected_X_column != null)
                select_X_axis_btn.setDisable(true);
        }

        // rows
        if (row_or_column.getSelectedToggle() == rowRadio)
        {
            // y
            if (user_selecting_Y_axis && selected_Y_rows.size() > 0)
            {
                select_Y_axis_btn.setDisable(true);
                select_X_axis_btn.setDisable(false);
            }
            // x
            if(user_selecting_X_axis && selected_X_row != null)
                select_X_axis_btn.setDisable(true);
        }

    }


    private HBox createItem(String itemName) {
        HBox item;
        item = new HBox(new Label(itemName));
        HBox.setHgrow(item, Priority.ALWAYS);
        return item;
    }



    private ArrayList<String> extractLegendsFromLegendTextField(){

        // final_legends = the legends after the user maybe edited
        ArrayList<String> final_legends = new ArrayList<>();
        String[] legends_adapter = Legends_textField.getText().split(";");

        // TODO: could crash, fix that
        // extract legends, and if user keep 1 or more empty, we will give him the default legend name
        for(int i=0; i<legends_adapter.length; i++)
        {
            if(!legends_adapter[i].equals(""))
                final_legends.add(legends_adapter[i]);
            else
                final_legends.add(selected_legends.get(i));
        }
        return final_legends;
    }



    // check if table data (column/row) can be convert to Number object  {only for beta version}
    public boolean isConvertible(AnalyzerTableView table, int selected_index, String rowORcolumn){ // maybe boolean

        boolean isConvertible = true;

        // if the current graph is one of the "numeric x axis" graphs
        if(graphType.equals("LINE_CHART") || graphType.equals("SCATTER_CHART") || graphType.equals("AREA_CHART") || graphType.equals("STACKED_AREA_CHART")) {

            // if column:
            if (rowORcolumn.equals("column")) {
                try {
                    //Double check = Double.parseDouble(table.getColumns().get(selected_index).getCellObservableValue(0).getValue().toString());
                } catch (Exception e) {
                    isConvertible = false;
                    System.out.println("* NOT Convertible *");
                }
            }

            // if row:
            if (rowORcolumn.equals("row")) {
                // TODO: in the future we need to add that also here
            }
        }
        return isConvertible;
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void initialize() {

        graphController = this;

        // this piece of code will activate event handlers on every table that contains data
//        ArrayList<AnalyzerTableView> tables = Controller.getTables();
//        for (AnalyzerTableView table : tables)
//        {
//            if (table.isDataAdded == true)
//            {
//                addColumnEventHandler(table);
//                addRowEventHandler(table);
//            }
//        }
    }



    public static GraphController getInstance() {

        return graphController;
    }



    // this function use to handle column selection when we want to choose columns axes
    public void addColumnEventHandler(AnalyzerTableView table) {

        graphController = GraphController.getInstance();

        for (int i = 0; i < table.getColumns().size(); i++) {

            TableColumn current_col = table.getColumns().get(i);
            int colIndex = i;

            current_col.getGraphic().setOnMouseClicked(mouseEvent -> {

            try {
                if (graphController.row_or_column.getSelectedToggle() == graphController.colRadio)
                {
                    if (dataBarRoot != null && dataBarRoot.getScene().getWindow().isShowing())
                    {
                        /* ---------------------------- */
                        /* this code will select all the column when we touch the header */
                        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        table.getSelectionModel().setCellSelectionEnabled(true);
                        TableColumn current_column = table.getColumns().get(colIndex);
                        table.getSelectionModel().clearSelection();
                        table.getSelectionModel().selectRange(0, current_column, table.getItems().size() - 1, current_column);
                        /* ---------------------------- */

                        VBox column_label = (VBox) table.getColumns().get(colIndex).getGraphic();
                        Label label = (Label) column_label.getChildren().get(0);

                        // TODO: need to test that
                        boolean is_convertible = graphController.isConvertible(table, colIndex, "column");
                        if(is_convertible)
                            graphController.updateDataBar_columns(table, label.getText(), colIndex);
                        else
                            AlertsMaker.showErrorMessage("Invalid Data Type", "The current graph can take only numeric types values as X axis.");
                    }
                }
            }catch (Exception e){}
            });
        }

    }



    // just a shortcut to activate the class below
    public void addRowEventHandler(AnalyzerTableView table) {
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new RowMouseClickedEventHandler());
    }



    // this class use to handle row selection when we want to choose rows axes
    public class RowMouseClickedEventHandler implements EventHandler<MouseEvent> {

        public AnalyzerTableView table;
        public GraphController graphController;

        @Override
        public void handle(MouseEvent event) {

            table = (AnalyzerTableView) event.getSource();
            graphController = GraphController.getInstance();

            try {
                if (event.getClickCount() == 1) {
                    if (graphController.row_or_column.getSelectedToggle() == graphController.rowRadio)
                    {
                        if (dataBarRoot != null && dataBarRoot.isVisible())
                        {//.getScene().getWindow().isShowing()
                            /* ---------------------------- */
                            /* this code will select all the row when we touch it */
                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                            table.getSelectionModel().setCellSelectionEnabled(false);
                            /* ---------------------------- */

                            int rowIndex = table.getSelectionModel().getSelectedCells().get(0).getRow();

                            graphController.updateDataBar_rows(table, rowIndex);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }



    // disable the abilities that we gave the table in the functions 'addRowEventHandler' and 'addColumnEventHandler'
    private void cancelTableAbilities(){

        dataBarRoot = null;
        //row_or_column = null;
        //graphController.colRadio = null;
        //graphController.rowRadio = null;

//        ArrayList<AnalyzerTableView> tables = Controller.getTables();
//
//        for (AnalyzerTableView table : tables) {
//            table.getSelectionModel().clearSelection();
//            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//            table.setEditable(true);
//            table.getSelectionModel().setCellSelectionEnabled(true);
//        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////// Round Charts handling ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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

        //DragResizeMod.makeResizable(roundChart);
        //controller.target.getAnchorPane().addChildren(roundChart);
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

















}
