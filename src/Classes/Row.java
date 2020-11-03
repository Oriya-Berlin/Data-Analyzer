package Classes;

import java.util.HashMap;

public class Row { // rows populate the tables with data

	private HashMap<String, Double> attributes;// since the number of columns can change across tables, a HashMap is
												// used to simulate the columns of data

	public Row(HashMap<String, Double> attributes) {
		this.attributes = attributes;
	}

	public HashMap<String, Double> getAttributeMap() {

		if (attributes == null) {
			attributes = new HashMap<String, Double>();
		}

		return attributes;
	}

	public void setAttribute(String key, Double value) {
		attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

}
