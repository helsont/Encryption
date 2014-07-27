package frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tree.Branch;
import tree.Leaf;
import tree.Node;
import encrypter.DecryptPerformed;
import encrypter.EncryptPerformed;
import encrypter.Encrypter;
import encrypter.EncryptionType;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6374524456760099433L;
	private JPanel contentPane;
	private JTextField txtDoubleClickTo;
	private JSeparator separator;
	private JLabel lblPercent;
	public static MainFrame frame;
	private JSplitPane splitPane;
	private JButton btnDecrypt;
	private ArrayList<File> _list = new ArrayList<File>();
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuBar menuBar_1;
	private JMenu mnEncrpytionType;
	private JMenuBar menuBar_2;
	private JMenuItem mntmAes;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmAescbcpkcspadding;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmAesecbpkcspadding;
	private JMenuItem mntmAesecbpkcspadding_1;
	private JMenu mnNewMenu_2;
	private JMenuItem mntmPkcspadding;
	private JMenuItem mntmExit;

	private Node selected_file = null;
	public boolean processing;
	public int encrypt_count = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Winter Encryption");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setBackground(Color.white);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		mnEncrpytionType = new JMenu("Encrpytion Type");
		// menuBar.add(mnEncrpytionType);

		mnNewMenu = new JMenu("AES");
		mnEncrpytionType.add(mnNewMenu);

		mntmAes = new JMenuItem("AES/CBC/NoPadding");
		mnNewMenu.add(mntmAes);
		mnNewMenu.addActionListener(this);

		mntmNewMenuItem = new JMenuItem("AES/CBC/PKCS5Padding");
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.addActionListener(this);

		mntmAescbcpkcspadding = new JMenuItem("AES/ECB/NoPadding");
		mnNewMenu.add(mntmAescbcpkcspadding);
		mntmAescbcpkcspadding.addActionListener(this);

		mntmAesecbpkcspadding = new JMenuItem("AES/ECB/PKCS5Padding");
		mnNewMenu.add(mntmAesecbpkcspadding);

		mnNewMenu_1 = new JMenu("DES");
		mnEncrpytionType.add(mnNewMenu_1);

		mntmAesecbpkcspadding_1 = new JMenuItem("DES/CBC/NoPadding");
		mnNewMenu_1.add(mntmAesecbpkcspadding_1);
		mntmAesecbpkcspadding_1.addActionListener(this);

		mnNewMenu_2 = new JMenu("RSA");
		mnEncrpytionType.add(mnNewMenu_2);

		mntmPkcspadding = new JMenuItem("RSA/ECB/PKCS1Padding");
		mnNewMenu_2.add(mntmPkcspadding);
		mntmPkcspadding.addActionListener(this);

		menuBar_2 = new JMenuBar();
		mnEncrpytionType.add(menuBar_2);

		menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 82, 287, 78 };
		gbl_contentPane.rowHeights = new int[] { 29, 0, 0, 0, 0, 156, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		txtDoubleClickTo = new JTextField();
		txtDoubleClickTo.setForeground(Color.GRAY);
		txtDoubleClickTo.setText("Click to select file");
		txtDoubleClickTo.addMouseListener(new CustomMouseAdapter());
		txtDoubleClickTo.setHorizontalAlignment(SwingConstants.CENTER);
		txtDoubleClickTo.setFocusable(false);
		txtDoubleClickTo.setCaretPosition(0);
		GridBagConstraints gbc_txtDoubleClickTo = new GridBagConstraints();
		gbc_txtDoubleClickTo.fill = GridBagConstraints.BOTH;
		gbc_txtDoubleClickTo.insets = new Insets(0, 0, 5, 5);
		gbc_txtDoubleClickTo.gridx = 1;
		gbc_txtDoubleClickTo.gridy = 1;
		contentPane.add(txtDoubleClickTo, gbc_txtDoubleClickTo);
		txtDoubleClickTo.setColumns(10);

		splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 5);
		gbc_splitPane.fill = GridBagConstraints.VERTICAL;
		gbc_splitPane.gridx = 1;
		gbc_splitPane.gridy = 2;
		contentPane.add(splitPane, gbc_splitPane);

		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEncrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				EncryptPerformed ep = new EncryptPerformed(frame,
						selected_file, processing, encrypt_count);
				ep.actionPerformed(evt);
			}

		});
		this.setLocationRelativeTo(null);
		splitPane.setLeftComponent(btnEncrypt);
		btnEncrypt
				.setToolTipText("Either encrypts or decrypts the file, depending on its state.");
		btnEncrypt.setMnemonic('e');

		btnDecrypt = new JButton("Decrypt");
		splitPane.setRightComponent(btnDecrypt);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DecryptPerformed dp = new DecryptPerformed(frame,
						selected_file, processing, encrypt_count);
				dp.actionPerformed(evt);
			}
		});

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.BASELINE;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 3;
		contentPane.add(separator, gbc_separator);

		lblPercent = new JLabel("Percent");
		GridBagConstraints gbc_lblPercent = new GridBagConstraints();
		gbc_lblPercent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPercent.insets = new Insets(0, 0, 5, 5);
		gbc_lblPercent.gridx = 1;
		gbc_lblPercent.gridy = 4;
		//contentPane.add(lblPercent, gbc_lblPercent);
	}

	public void actionPerformed(ActionEvent event) {
		Encrypter.encryptionType = new EncryptionType(
				((JMenuItem) event.getSource()).getText());
	}

	class CustomMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// Create a file chooser
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
			FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null,
					"Choose File", FileDialog.LOAD);
			fd.setDirectory(System.getProperty("user.home"));
			fd.setVisible(true);
			System.setProperty("apple.awt.fileDialogForDirectories", "false");

			File selected = null;
			String dir = fd.getDirectory();
			String name = fd.getFile();
			String filename = dir + name;
			System.out.println("Filename:" + filename);
			if (name == null)
				return;
			else
				selected = new File(filename);

			if (selected.isDirectory())
				selected_file = new Branch(selected);
			else
				selected_file = new Leaf(selected);

			txtDoubleClickTo.setText(selected.getAbsolutePath());
			txtDoubleClickTo.setForeground(Color.black);

		}
	}

	public void setFileLocation(File file) {
		Logger.getLogger("Files").log(Level.ALL,
				"Adding " + file.getAbsolutePath());
		_list.add(file);
	}

	public boolean isFolder(File f) {
		return f.isDirectory();
	}
}
