package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;


//CellValueFactories are like toString methods - they are intended to present
// existing data in a readable fashion

public class RowCellValueFactory
		implements Callback<CellDataFeatures<Row, String>, ObservableValue<String>> {
																									
	private Integer columnNumber;

	public RowCellValueFactory(int column_number) {
		this.columnNumber = column_number;
	}

	//the call method is used by the CellValueFactory in order to retrieve
	// the data from the cell and presenting it to the column
	
	@Override
	public ObservableValue<String> call(CellDataFeatures<Row, String> param) {
		return new SimpleStringProperty(param.getValue().getAttribute(columnNumber.toString()).toString());
	}
}