package Classes;

import java.util.HashMap;

public class Row {


	private HashMap<String, Object> row; // since the number of columns can change across tables, a HashMap is


	public Row(HashMap<String, Object> row) {
		this.row = row;
	}



	public HashMap<String, Object> getRow() {

		if (row == null) {
			row = new HashMap<String, Object>();
		}
		return row;
	}



	public void setAttribute(String key, Object value) {
		row.put(key, value);
	}



	public Object getAttribute(String key) {
		return row.get(key);
	}

}
