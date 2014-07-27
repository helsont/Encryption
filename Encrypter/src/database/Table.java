package database;

import java.io.Serializable;

public class Table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7718202403101352253L;
	Array<Column> columns;
	private int curr_row;
	private boolean entryMode;

	/**
	 * Creates a table with five columns.
	 */
	public Table() {
		this(5);
	}

	/**
	 * Creates a table with specified number of columns.
	 * 
	 * @param size
	 */
	public Table(int size) {
		columns = new Array<Column>(size);
	}

	/**
	 * Call before adding any data to the database.
	 */
	public void addEntry() {
		entryMode = true;
	}

	/**
	 * Call when you have completed adding data for a row.
	 */
	public void nextEntry() {
		curr_row++;
	}

	/**
	 * Call when you are finished adding data.
	 */
	public void endEntry() {
		entryMode = false;
	}

	/**
	 * Creates a new column in the table.
	 * 
	 * @param name
	 *            The name of the column
	 * @param type
	 *            The type of data it receives
	 */
	public <T> void newCol(String name, T type) {
		columns.push(new Column<T>(this, name));
	}

	/**
	 * Creates a new column in the table.
	 * 
	 * @param name
	 *            The name of the column
	 * @param type
	 *            The type of data it receives
	 */
	public <T> void newCol(Column<T> col) {
		columns.push(col);
	}

	/**
	 * Creates a new column in the table.
	 * 
	 * @param name
	 *            The name of the column
	 * @param type
	 *            The type of data it receives
	 */
	public <T> void newCol(String name, T type, boolean unique) {
		columns.push(new Column<T>(this, name, unique));
	}

	/**
	 * Must be in entryMode. Call addEntry before using this method.
	 * 
	 * Adds specified object to the column with the specified name. The name
	 * must exactly match the one used with the method newCol().
	 * 
	 * @param column
	 *            The name of column where the object goes.
	 * @param entry
	 *            The object.
	 * @return The object added.
	 */
	public Object add(String column, Object entry) {
		if (!entryMode)
			throw new TableError(
					"Must be call addEntry() before adding any data.");

		// Adding the object to the appropriate column
		for (int i = 0; i < columns.length(); i++)
			if (columns.get(i).getName().equals(column))
				return columns.get(i).push(entry);

		throw new TableError("Could not finding matching column type for "
				+ entry.getClass());
	}

	/**
	 * Adds the data with the format object[odd] is the name of the column and
	 * object [odd+1] is the data to be entered. The column name must match with
	 * the exact case as the column name entered initially.
	 * 
	 * Because this method fills an entire row, nextEntry is automatically
	 * called.
	 * 
	 * @param entry
	 */
	public void add(Object... entry) {
		if (!entryMode)
			throw new TableError(
					"Must be call addEntry() before adding any data.");
		// Ensure that the user has a String column name followed by the
		// appropriate data type.
		// If the length is odd, we are missing an object
		// If the length does not match the number of columns, the data
		// is not correct.
		if (entry.length % 2 == 1 || entry.length != columns.size() * 2)
			throw new TableError(
					"You must specify a data value for each column. Params size: "
							+ entry.length + ", columns size: "
							+ columns.size() + ".");

		// Adds the data appropriately.
		int col_num = 0;
		for (int i = 0; col_num < columns.size(); i += 2) {
			if (columns.get(col_num).getName().equals(entry[i])) {
				columns.get(col_num).push(entry[i + 1]);
				col_num++;
				continue;
			}
		}

		nextEntry();
	}

	public void printTable() {
		for (int i = 0; i < columns.size(); i++) {
			System.out.println("Column:" + columns.get(i).getName());
			columns.get(i).printArray();
			System.out.println("--------------------------");
		}
	}
}
