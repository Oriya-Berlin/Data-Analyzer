package Classes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class AnalyzerTableView extends TableView<Row> {

    private String tableName = null;
    private boolean isDataAdded;

    public AnalyzerTableView(String tableName){
        this.tableName = tableName;
        this.isDataAdded = false;
    }


    public String getTableName() {
        return tableName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    // TODO: we need to put here functions to get the data from the source


    private void makeHeader(TableColumn<?, ?> target, String name) {
        VBox vBox = new VBox(new Label(name));
        target.setText("");
        target.setSortable(false);
        vBox.setAlignment(Pos.CENTER);
        target.setGraphic(vBox);
    }


}
