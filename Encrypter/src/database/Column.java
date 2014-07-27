package database;

import java.io.Serializable;

public class Column<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7575932450184437451L;
	private transient Table table;
	private String name;
	private Array<T> data;
	private boolean unique;

	public Column(Table table, String name) {
		if (table == null)
			throw new IllegalArgumentException("Table is null.");
		this.table = table;
		if (name == null)
			throw new IllegalArgumentException("Name is null.");
		this.name = name;
		data = new Array<T>(10);
	}

	/**
	 * Creates a table. A unique table makes sure that all elements in the array
	 * do not equal each other. Calls the element's equals() function.
	 * 
	 * @param table
	 * @param name
	 * @param unique
	 */
	public Column(Table table, String name, boolean unique) {
		this(table, name);
		this.unique = unique;
	}

	/**
	 * The name of the column.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds the element to the array.
	 * 
	 * @param element
	 */
	public void add(T element) {
		// If we don't care about unique elements, just add it to the array
		if (!unique) {
			data.add(element);
			return;
		}
		// We do care about unique elements. Check to make sure that this
		// element
		// does not match any other element.
		if (isUnique(element))
			data.add(element);
		else
			throw new TableError("Element \"" + element + "\" is not unique.");
	}

	/**
	 * Adds the element to the array at the specified index.
	 * 
	 * @param element
	 */
	public void addAt(int index, T element) {
		// If we don't care about unique elements, just add it to the array
		if (!unique) {
			data.addAt(index, element);
			return;
		}
		// We do care about unique elements. Check to make sure that this
		// element
		// does not match any other element.
		if (isUnique(element))
			data.addAt(index, element);
		else
			throw new TableError("Element \"" + element + "\" is not unique.");
	}

	/**
	 * Pushes the element to the array.
	 * 
	 * @param element
	 */
	public T push(T element) {
		// If we don't care about unique elements, just add it to the array
		if (!unique) {
			return data.push(element);
		}
		// We do care about unique elements. Check to make sure that this
		// element
		// does not match any other element.
		if (isUnique(element))
			return data.push(element);
		else
			throw new TableError("Element \"" + element + "\" is not unique.");
	}

	public T get(int index) {
		return data.get(index);
	}

	/**
	 * Removes the element from the array.
	 * 
	 * @param element
	 */
	public void remove(T element) {
		data.remove(element);
	}

	/**
	 * Removes the element from the array.
	 * 
	 * @param element
	 */
	public void removeAt(int index) {
		data.removeAt(index);
	}

	/**
	 * Checks that this element does not equal any element already added to the
	 * array.
	 * 
	 * @param element
	 * @return
	 */
	private boolean isUnique(T element) {
		for (int i = 0; i < data.size(); i++)
			if (data.get(i).equals(element))
				return false;
		return true;
	}

	/**
	 * Calls the array's printArray method.
	 */
	public void printArray() {
		data.printArray();
	}

	/**
	 * Returns the number of elements in the array.
	 * 
	 * @return
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Returns the size of the array.
	 * 
	 * @return
	 */
	public int length() {
		return data.length();
	}

	public Table getTable() {
		return table;
	}
}
