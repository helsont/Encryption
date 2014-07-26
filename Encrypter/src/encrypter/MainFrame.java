package encrypter;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6374524456760099433L;
	private JPanel contentPane;
	private JTextField txtDoubleClickTo;
	private JSeparator separator;
	private JLabel lblPercent;
	private static MainFrame frame;
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
	public boolean processing;
	int encrypt_count = 0;

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
			public void actionPerformed(ActionEvent arg0) {
				if (processing)
					JOptionPane.showMessageDialog(frame,
							"Please wait, encryption in process.");
				if (encrypt_count > 0) {
					int result = JOptionPane
							.showConfirmDialog(
									null,
									"Are you sure you want to encrypt? "
											+ "Do not reencrypt data. "
											+ "This will result in permanent data loss.",
									"Reencrypt Data?",
									JOptionPane.YES_NO_CANCEL_OPTION);
					if (result == JOptionPane.CANCEL_OPTION)
						return;
					if (result == JOptionPane.NO_OPTION)
						return;
				}
				Encrypter en = new Encrypter();
				en.setLoc();
				processing = true;
				
				if (_list.size() > 0)
					try {
						en.encryptFolder(_list);
						// en.encryptFolderNFC(_list);
						JOptionPane.showMessageDialog(frame,
								"Successfully encrypted.");
						processing = false;
						encrypt_count++;
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(frame,
								"Encryption unsuccesful.");
						e.printStackTrace();
						processing = false;
						return;
					} catch (IllegalBlockSizeException e) {
						JOptionPane.showMessageDialog(frame,
								"Encryption unsuccesful.");
						e.printStackTrace();
						processing = false;
						return;
					} catch (BadPaddingException e) {
						JOptionPane.showMessageDialog(frame,
								"Encryption unsuccesful.");
						e.printStackTrace();
						processing = false;
						return;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,
								"Encryption unsuccesful.");
						e.printStackTrace();
						processing = false;
						return;
					}
				else
					try {
						// en.encryptFolder(_list.get(0));
						en.encryptOneFileNFC(_list.get(0));
						JOptionPane.showMessageDialog(frame,
								"Successfully encrypted.");
						encrypt_count++;
						processing = false;
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(frame,
								"Encryption unsuccesful.");
						e.printStackTrace();
						processing = false;
						return;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,
								"Encryption unsuccesful.");
						e.printStackTrace();
						processing = false;
						return;
					}
			}

		});
		splitPane.setLeftComponent(btnEncrypt);
		btnEncrypt
				.setToolTipText("Either encrypts or decrypts the file, depending on its state.");
		btnEncrypt.setMnemonic('e');

		btnDecrypt = new JButton("Decrypt");
		splitPane.setRightComponent(btnDecrypt);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (processing)
					JOptionPane.showMessageDialog(frame,
							"Please wait, encryption in process.");
				encrypt_count = 0;
				Encrypter de = new Encrypter();
				de.setLoc();
				processing = true;
				
				for (int i = 0; i < _list.size(); i++) {
					if (_list.get(i) != null) {
						if (_list.get(i).isDirectory())
							decryptDirectory(de, _list.get(i).listFiles());

						try {
							de.decrypt(_list.get(i));
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(frame,
									"Decryption unuccessful.");
							e.printStackTrace();
							processing = false;
							return;
						} catch (IllegalBlockSizeException e) {
							JOptionPane.showMessageDialog(frame,
									"Decryption unuccessful.");
							e.printStackTrace();
							processing = false;
							return;
						} catch (BadPaddingException e) {
							JOptionPane.showMessageDialog(frame,
									"Decryption unuccessful.");
							e.printStackTrace();
							processing = false;
							return;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame,
									"Decryption unuccessful.");
							processing = false;
							return;
						}
					}
				}
				JOptionPane.showMessageDialog(frame, "Successfully decrypted.");
				processing = false;
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
		// contentPane.add(lblPercent, gbc_lblPercent);
	}

	public void decryptDirectory(Encrypter de, File[] files) {
		for (int a = 0; a < files.length; a++) {
			if (files[a].isDirectory()) {
				decryptDirectory(de, files[a].listFiles());
				continue;
			}
			try {
				de.decrypt(files[a]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		Encrypter.encryptionType = new EncryptionType(
				((JMenuItem) event.getSource()).getText());
	}

	class CustomMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// Create a file chooser
			final JFileChooser fc = new JFileChooser();
			// fc.setCurrentDirectory(new java.io.File("."));
			// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			// fc.setAcceptAllFileFilterUsed(false);

			if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): "
						+ fc.getCurrentDirectory());
				System.out.println("getSelectedFile() : "
						+ fc.getSelectedFile());

				File selected = fc.getSelectedFile();

				if (isFolder(selected)) {
					addFilesFromFolderToArray(selected);
				} else {
					_list.add(selected);
				}
				txtDoubleClickTo.setText(selected.getAbsolutePath());
				txtDoubleClickTo.setForeground(Color.black);
			} else {
				System.out.println("No Selection ");
			}
		}
	}

	public void setFileLocation(File file) {
		Logger.getLogger("Files").log(Level.ALL,
				"Adding " + file.getAbsolutePath());
		_list.add(file);
	}

	public void addFilesFromFolderToArray(File folder) {
		File[] files = folder.listFiles();
		for (int i = files.length - 1; i >= 0; i--) {
			// Do not add hidden files, turn this into a flag
			if (files[i].isHidden())
				continue;
			System.out.println("Adding " + files[i].getAbsolutePath());
			Logger.getLogger("Files").log(Level.ALL,
					"Adding " + files[i].getAbsolutePath());
			_list.add(files[i]);
		}
	}

	public boolean isFolder(File f) {
		return f.isDirectory();
	}
}
