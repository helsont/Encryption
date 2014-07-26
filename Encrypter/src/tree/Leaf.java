package tree;

import java.io.File;

public class Leaf extends Node {

	private Branch branch;

	public Leaf(Branch b, File f) {
		super(f);
		if (b == null)
			new Error("Branch is null");
		this.branch = b;
	}

	public Leaf(String dir) {
		super(dir);
	}

	public Branch getBranch() {
		return branch;
	}
}
