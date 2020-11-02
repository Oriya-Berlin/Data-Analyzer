package Classes;

import Controllers.GraphController;
import Utils.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ChartsDataHandling {



    // COLUMNS
    public static void columnsData(String graph, TableColumn x_axis, ArrayList<TableColumn> y_axis_array, ArrayList<String> legends, ArrayList<Number> roundChartValues) {

        switch (graph) {

            case "LINE_CHART":
            case "SCATTER_CHART":
            case "AREA_CHART":
            case "STACKED_AREA_CHART":
                column_number_number(x_axis, y_axis_array, legends);
                break;

            case "STACKED_BAR_CHART":
            case "BAR_CHART":
                column_category_number(x_axis, y_axis_array, legends, true);
                break;

            case "HORIZONTAL_BAR_CHART":
                column_category_number(x_axis, y_axis_array, legends,false);
                break;

            case "RING_CHART":
            case "PIE_CHART":
                roundChart_column(y_axis_array, roundChartValues,legends);
                break;
        }
    }


    // ROWS
    public static void rowsData(String graph, AnalyzerTableView table, Integer x_axis, ArrayList<Integer> y_axis_array, ArrayList<String> legends , ArrayList<Number> roundChartValues){

        switch (graph) {

            case "LINE_CHART":
            case "SCATTER_CHART":
            case "AREA_CHART":
            case "STACKED_AREA_CHART":
                row_number_number(table, x_axis, y_axis_array, legends);
                break;

            case "STACKED_BAR_CHART":
            case "BAR_CHART":
                row_category_number(table, x_axis, y_axis_array, legends, true);
                break;

            case "HORIZONTAL_BAR_CHART":
                row_category_number(table, x_axis, y_axis_array, legends, false);
                break;

            case "RING_CHART":
            case "PIE_CHART":
                roundChart_row(y_axis_array, table, roundChartValues, legends);
                break;
        }
    }



    // for columns && category axis
    public static void column_category_number(TableColumn x_axis, ArrayList<TableColumn> y_axis_array,
                                              ArrayList<String> legends, boolean isGraphVertical){

        GraphController graphController = GraphController.getInstance();
        graphController.chart.getData().clear();
        graphController.chart.setAnimated(false);

        Double y;
        String x;

        for(int index=0; index<y_axis_array.size(); index++)
        {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(legends.get(index));

            int column_length = y_axis_array.get(index).getTableView().getItems().size();

            for (int i=0; i<column_length; i++)
            {
                y = Double.parseDouble(y_axis_array.get(index).getCellObservableValue(i).getValue().toString());

                // if x axis was not been choosed yet
                if(x_axis != null)
                    x = String.valueOf(x_axis.getCellObservableValue(i).getValue());
                else
                    x = String.valueOf(i+1);

                // if graph is vertical or horizontal
                if(isGraphVertical)
                    series.getData().add(new XYChart.Data(x, y));
                else
                    series.getData().add(new XYChart.Data(y, x));
            }
            graphController.chart.getData().add(series);
        }

    }



    public static void column_number_number(TableColumn x_axis, ArrayList<TableColumn> y_axis_array, ArrayList<String> legends){


        GraphController graphController = GraphController.getInstance();
        graphController.chart.getData().clear();
        graphController.chart.setAnimated(false);

        Double y;
        Double x;

        for(int index=0; index<y_axis_array.size(); index++)
        {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(legends.get(index));

            int column_length = y_axis_array.get(index).getTableView().getItems().size();

            for (int i=0; i<column_length; i++)
            {
                y = Double.parseDouble(y_axis_array.get(index).getCellObservableValue(i).getValue().toString());

                // if x axis was not been choosed yet
                if(x_axis != null)
                    x = Double.parseDouble(x_axis.getCellObservableValue(i).getValue().toString());
                else
                    x = Double.valueOf(i);

                series.getData().add(new XYChart.Data(x, y));
            }
            graphController.chart.getData().add(series);
        }

    }



    public static void row_category_number(AnalyzerTableView table, Integer x_axis, ArrayList<Integer> y_axis_array,
                                           ArrayList<String> legends, boolean isGraphVertical){


        GraphController graphController = GraphController.getInstance();
        graphController.chart.getData().clear();
        graphController.chart.setAnimated(false);

        Double y;
        String x;
        Row row_y = null;
        Row row_x = null;

        for(int index=0; index<y_axis_array.size(); index++)
        {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(legends.get(index));

            int row_length = table.getColumns().size();

            row_y = table.getItems().get(y_axis_array.get(index));

            // if x axis has been choosed
            if(x_axis != null)
                row_x = table.getItems().get(x_axis);


            for (int i=0; i<row_length; i++)
            {
                TableColumn column = table.getColumns().get(i);

                y = Double.parseDouble(column.getCellObservableValue(row_y).getValue().toString());

                // if x axis was not been choosed yet
                if(x_axis != null)
                    x =  String.valueOf(column.getCellObservableValue(row_x).getValue());
                else
                    x = String.valueOf(i+1);

                // if graph is vertical or horizontal
                if(isGraphVertical)
                    series.getData().add(new XYChart.Data(x, y));
                else
                    series.getData().add(new XYChart.Data(y, x));
            }
            graphController.chart.getData().add(series);
        }

    }


    public static void row_number_number(AnalyzerTableView table, Integer x_axis, ArrayList<Integer> y_axis_array, ArrayList<String> legends){


        GraphController graphController = GraphController.getInstance();
        graphController.chart.getData().clear();
        graphController.chart.setAnimated(false);

        Double y;
        Double x;
        Row row_y = null;
        Row row_x = null;

        for(int index=0; index<y_axis_array.size(); index++)
        {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(legends.get(index));

            int row_length = table.getColumns().size();

            row_y = table.getItems().get(y_axis_array.get(index));

            // if x axis has been choosed
            if(x_axis != null)
                row_x = table.getItems().get(x_axis);


            for (int i=0; i<row_length; i++)
            {
                TableColumn column = table.getColumns().get(i);

                y = Double.parseDouble(column.getCellObservableValue(row_y).getValue().toString());

                // if x axis was not been choosed yet
                if(x_axis != null)
                    x =  Double.parseDouble(column.getCellObservableValue(row_x).getValue().toString());
                else
                    x = Double.valueOf(i);

                series.getData().add(new XYChart.Data(x, y));
            }
            graphController.chart.getData().add(series);
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////// Round Charts handling ///////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Double column_sum = null;
    public static Double row_sum = null;



    public static void roundChart_row(ArrayList<Integer> singleRowIndexArray, AnalyzerTableView table, ArrayList<Number> roundChartValues, ArrayList<String> legends){

        GraphController graphController = GraphController.getInstance();
        graphController.roundChart.getData().clear();


        int row_length = table.getColumns().size();
        int rowIndex = singleRowIndexArray.get(0);
        Row row = table.getItems().get(rowIndex);

        row_sum = calculateRowSum(rowIndex, table);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        TableColumn column;
        Double value;


        // if it's the first time that we update the round chart
        if(roundChartValues == null || roundChartValues.isEmpty())
        {
            for (int i = 0; i < row_length; i++)
            {
                column = table.getColumns().get(i);
                value = Double.parseDouble(column.getCellObservableValue(row).getValue().toString());
                data.add(new PieChart.Data(getColumnName(column), value));
            }
        }
        else  // if it's the final time the user send he's data
        {
            for(int i=0; i<roundChartValues.size(); i++)
            {
                data.add(new PieChart.Data(legends.get(i), (Double) roundChartValues.get(i)));
            }
        }

        if(graphController.graphType.equals("RING_CHART")) {

            double x = graphController.roundChart.getLayoutX();
            double y = graphController.roundChart.getLayoutY();
            graphController.roundChart.setVisible(false);

            graphController.roundChart = new RingChart();
            graphController.roundChart.setData(data);
            graphController.displayRoundChart(x, y);

        }else
            graphController.roundChart.setData(data);

    }




    public static void roundChart_column(ArrayList<TableColumn> single_column_array, ArrayList<Number> roundChartValues, ArrayList<String> legends){

        GraphController graphController = GraphController.getInstance();
        graphController.roundChart.getData().clear();


        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        TableColumn column = single_column_array.get(0);  // only one column in this array
        column_sum = calculateColumnSum(column);

        int column_length = column.getTableView().getItems().size();
        Double value;


        // if it's the first time that we update the round chart
        if(roundChartValues == null || roundChartValues.isEmpty())
        {
            for (int i = 0; i < column_length; i++)
            {
                value = Double.parseDouble(column.getCellObservableValue(i).getValue().toString());
                data.add(new PieChart.Data(legends.get(i), value));
            }
        }
        else  // if it's the final time the user send he's data
        {
            for(int i=0; i<roundChartValues.size(); i++)
            {
                data.add(new PieChart.Data(legends.get(i), (Double) roundChartValues.get(i)));
            }
        }



        if(graphController.graphType.equals("RING_CHART")) {

            double x = graphController.roundChart.getLayoutX();
            double y = graphController.roundChart.getLayoutY();
            graphController.roundChart.setVisible(false);

            graphController.roundChart = new RingChart();
            graphController.roundChart.setData(data);
            graphController.displayRoundChart(x, y);

        }else
            graphController.roundChart.setData(data);

    }












    public static Double calculateSum(TableColumn column ,Integer rowIndex, AnalyzerTableView table){

        Double totalSum = 0.0;

        if(column != null)
        {
            int column_length = column.getTableView().getItems().size();

            for (int i=0; i<column_length; i++)
                totalSum = totalSum + Double.parseDouble(column.getCellObservableValue(i).getValue().toString());

            return totalSum;

        }else
            {
                int row_length = table.getColumns().size();
                Row row = table.getItems().get(rowIndex);

                for(int i=0; i<row_length; i++)
                {
                    TableColumn current_column = table.getColumns().get(i);
                    totalSum = totalSum + Double.parseDouble(current_column.getCellObservableValue(row).getValue().toString());
                }

                return totalSum;
            }
    }







    public static Double calculateColumnSum(TableColumn column){

        Double totalSum = 0.0;
        int column_length = column.getTableView().getItems().size();

        for (int i=0; i<column_length; i++)
            totalSum = totalSum + Double.parseDouble(column.getCellObservableValue(i).getValue().toString());

        return totalSum;
    }



    public static Double calculateRowSum(Integer rowIndex, AnalyzerTableView table){

        Double totalSum = 0.0;
        int row_length = table.getColumns().size();
        Row row = table.getItems().get(rowIndex);

        for(int i=0; i<row_length; i++)
        {
            TableColumn column = table.getColumns().get(i);
            totalSum = totalSum + Double.parseDouble(column.getCellObservableValue(row).getValue().toString());
        }

        return totalSum;
    }


    public static String calculatePercent(Double totalSum, Double value){
        Double percent = (value/totalSum) * 100;
        return  "(" + new DecimalFormat("##.##").format(percent) + "%)";
    }



    private static String getColumnName(TableColumn column){

        VBox vBox = (VBox) column.getGraphic();
        Label label = (Label) vBox.getChildren().get(0);

        return label.getText();
    }























}
