package database;

import java.util.ArrayList;

public class Table {

	private ArrayList<Column<?>> columns;
	
	private boolean entryMode;

	public Table() {
		columns = new ArrayList<Column<?>>();

	}

	public <T> void add(Column<T> c, T data) {
		// To check if the data we are inputing matches
		// the data the column receives
		if (c.type.getClass() == data.getClass()) {
			
		}
	}

	public <T> void newCol(String name, T type) {
		columns.add(new Column<T>(this, name));
	}

	public void addEntry() {
		entryMode = true;
	}
}
