package database;

public class Column<T> {

	public Table table;
	public String name;
	public T type;
	
	public Column(Table table, String name) {
		if (table == null)
			throw new IllegalArgumentException("Table is null.");
		this.table = table;
		if (name == null)
			throw new IllegalArgumentException("Name is null.");
		this.name = name;
	}
}
