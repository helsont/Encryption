package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeTable {

	private Table table;

	public SerializeTable(Table table) {
		this.table = table;
	}

	public void serialize(String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(table);
			// for (int i = 0; i < table.columns.size(); i++) {
			// // for (int x = 0; x < table.columns.get(i).size(); x++) {
			// // Object obj = table.columns.get(i).get(x);
			// Object obj = table.columns.get(i);
			// out.writeObject(obj);
			// // }
			// }
			out.close();
			fileOut.close();
			System.out.println("Serialized table");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static Table deserialize(String filename) {
		Table t = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			t = (Table) in.readObject();
			
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return null;
		}
		return t;
	}
}
