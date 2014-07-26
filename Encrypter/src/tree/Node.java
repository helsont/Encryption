package tree;

import java.io.File;

import encrypter.FileUtils;

public class Node {
	private String path;
	private File file;
	private Node parent;

	public Node(File file) {
		if (file == null)
			new Error("File is null");
		this.file = file;
		setPath(file.getAbsolutePath());
		if (!FileUtils.getParentDirectory(file).getAbsolutePath().equals("/"))
			parent = new Node(FileUtils.getParentDirectory(file)
					.getAbsolutePath());
	}

	public Node(String dir) {
		this(new File(dir));
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setFile(String file) {
		this.file = new File(file);
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		if (parent == null)
			return new Node(FileUtils.getParentDirectory(file)
					.getAbsolutePath());
		return parent;
	}
}
