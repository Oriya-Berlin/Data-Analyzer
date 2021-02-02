package Classes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

public class CustomizedColumn extends TableColumn {


    private String columnName;

    // we put Vbox to every column in order to make it responsive to mouse clicking
    // at every place of the column header
    public CustomizedColumn(String columnName){

        VBox vBox = new VBox(new Label(columnName));
        vBox.setAlignment(Pos.CENTER);
        this.columnName = columnName;
        this.setText("");
        this.setSortable(false);
        this.setGraphic(vBox);
    }


    public String getColumnName(){
        return this.columnName;
    }


}
