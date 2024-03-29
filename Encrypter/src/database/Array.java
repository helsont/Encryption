package database;

import java.io.Serializable;

public class Array<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7688205097820310267L;
	private T[] data;
	private int idx;
	private int size;

	public static final int SPACE_EFFICIENT = 1, SPEED_EFFICIENT = 2;
	public int efficiency = SPEED_EFFICIENT;

	@SuppressWarnings("unchecked")
	public Array(int size) {
		if (size < 0)
			throw new Error("Size must be greater than 0");
		data = (T[]) new Object[size];
		idx = 0;
	}

	public void add(T element) {
		if (!addAt(idx, element)) {
			if (efficiency == SPEED_EFFICIENT) {
				// Just expand the arraylist!
				expand();
				add(element);
			} else if (efficiency == SPACE_EFFICIENT) {

				// Give it a chance to look for null elements
				if (!addToOpenSpot(element)) {
					// When none are found, expand
					expand();
					add(element);
				}
			}
		}
	}

	public boolean addAt(int index, T element) {
		if (index >= 0 && index < data.length) {
			data[idx] = element;
			size++;
			idx++;
			if (index == idx)
				idx++;
			return true;
		}
		return false;
	}

	public void addAll(T... element) {
		for (int i = 0; i < element.length; i++)
			add(element[i]);
	}

	private boolean addToOpenSpot(T element) {
		for (int i = data.length - 1; i >= 0; i--)
			if (data[i] == null) {
				data[i] = element;
				return true;
			}
		return false;
	}

	/**
	 * Removes the element from the array.
	 * 
	 * @param element
	 */
	public void remove(T element) {
		for (int i = data.length - 1; i >= 0; i--) {
			if (data[i] != null && data[i].equals(element)) {
				removeAt(i);

				// To keep track of the element with the highest index, we
				// update it after its just been removed.
				if (i == idx)
					idx = getHighestIndex(i, LEFT);
			}
		}
	}

	private static int LEFT = 10, RIGHT = 11;

	private int getHighestIndex(int point, int direction) {
		int i = point;
		if (direction == LEFT) {
			while (data[i] == null && i > 0) {
				i--;
			}
			return i;
		} else if (direction == RIGHT) {
			while (data[i] == null && i < data.length) {
				i++;
			}
			return i;
		}
		throw new Error("Could not find highest index");
	}

	public void removeAt(int index) {
		if (index >= 0 && index < data.length)
			data[index] = null;
		else
			throw new Error("Index not within range: " + index
					+ ". The length of the array is " + data.length + ".");
		size--;
	}

	/**
	 * Returns the element at the top of the array.
	 * 
	 * @return
	 */
	public T pop() {
		int i = idx;
		while (data[i] == null && i > 0) {
			i--;
		}
		if (data[i] == null)
			throw new Error("No top element found. Array size: " + size + ".");
		T element = data[i];
		removeAt(i);
		idx = i;
		return element;
	}

	/**
	 * Add to the top of the array.
	 * 
	 * @param element
	 * @return
	 */
	public T push(T element) {
		if (idx < data.length) {
			data[idx] = element;
		} else {
			// Because we want this element to be on top,
			// We skip right to expansion if it doesn't
			// fit in the array.
			expand();
			data[idx] = element;
		}
		int i = idx;
		idx++;
		size++;
		return data[i];
	}

	private void expand() {
		int new_size = (int) (data.length * 1.75 + 1);
		T[] old = data;
		data = (T[]) new Object[new_size];
		System.arraycopy(old, 0, data, 0, old.length);
		old = null;
	}

	public void trim() {
		int new_idx = (idx >= data.length) ? idx - 1 : idx;
		int new_size = getHighestIndex(new_idx, LEFT);
		if (new_size == 0)
			new_size = 1;
		T[] old = data;
		data = (T[]) new Object[new_size];
		System.arraycopy(old, 0, data, 0, new_size);
		old = null;
	}

	public void compact() {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				for (int y = i; y < data.length; y++) {
					if (data[y] != null) {
						data[i] = data[y];
						data[y] = null;
						break;
					}
				}
			}
		}
		trim();
	}

	public T get(int index) {
		return data[index];
	}

	/**
	 * Prints the elements of the array.
	 */
	public void printArray() {
		for (int i = 0; i < data.length; i++)
			System.out.println("[" + i + "]=" + data[i]);
	}

	/**
	 * Clears all elements in the array.
	 */
	public void clear() {
		for (int i = data.length - 1; i >= 0; i--)
			data[i] = null;
		idx = 0;
		size = 0;
	}

	/**
	 * Returns total capacity of the array.
	 * 
	 * @return
	 */
	public int length() {
		return data.length;
	}

	/**
	 * Returns the total number of elements in the array.
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

}
