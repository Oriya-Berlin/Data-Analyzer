package Classes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class AnalyzerTableView extends TableView<Row> {


    private String tableName = null;
    private boolean isDataAdded;



    public AnalyzerTableView(){
        this.tableName = "";
        this.isDataAdded = false;
    }

    public String getTableName() {
        return tableName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    // TODO: we need to put here functions to get the data from the source
    public void setData(ResultSet rs) throws SQLException {

        this.getItems().clear();
        this.getColumns().clear();

        int columnCount = rs.getMetaData().getColumnCount();

        // Adding the columns to the table
        for(int i=1; i<=columnCount; i++)
        {
            TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i));
            column.setCellValueFactory(new RowCellValueFactory(i));
            this.getColumns().add(column);
        }

         ObservableList<Row> data = FXCollections.observableArrayList();

        // Iterate Rows
        while (rs.next())
        {
            HashMap<String,Object> cells = new HashMap<>();
            // Iterate row cells
            for (int i = 1; i<=columnCount; i++)
                cells.put(String.valueOf(i), rs.getObject(i));

            Row row = new Row(cells);
            data.add(row);
        }


        this.setItems(data);
    }



    // TODO: put that in table column class
    private void makeHeader(TableColumn<?, ?> target, String name) {
        VBox vBox = new VBox(new Label(name));
        target.setText("");
        target.setSortable(false);
        vBox.setAlignment(Pos.CENTER);
        target.setGraphic(vBox);
    }


    public String toString(){
        return this.tableName;
    }
}
