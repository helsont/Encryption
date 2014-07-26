package tree;

import java.io.File;

public class Branch extends Node {

	private Node[] nodes;

	public Branch(File source) {
		super(source);
		if (!source.isDirectory())
			throw new Error("This is not a directory:"
					+ source.getAbsolutePath());
		this.nodes = getNodes(source);
	}

	public Branch(Node[] nodes) {
		super(nodes[0].getParent().getPath());

		if (!isBranch(nodes))
			throw new Error("These leaves do not belong on the same branch.");

		this.nodes = nodes;
		setPath(nodes[0].getPath());

	}

	public Branch(Branch branch, File source) {
		super(source);
		setParent(branch);
	}

	public Branch(String string) {
		this(new File(string));
	}

	public Node[] getNodes() {
		return nodes;
	}

	private boolean isBranch(Node[] nodes) {
		String parent = nodes[0].getPath();
		for (int idx = 1; idx < nodes.length; idx++) {
			if (nodes[idx].getParent().getPath() != parent)
				return false;
		}
		return true;
	}

	private Node[] getNodes(File f) {
		File[] dir = f.listFiles();
		Node[] leaves = new Node[dir.length];
		for (int idx = 0; idx < dir.length; idx++) {
			if (dir[idx].isDirectory()) {
				leaves[idx] = new Branch(this, dir[idx]);
			}
			leaves[idx] = new Leaf(this, dir[idx]);
		}
		return leaves;
	}
}
