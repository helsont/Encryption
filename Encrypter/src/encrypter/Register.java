package encrypter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import password.PasswordCreator;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private static JFrame jframe;
	private JButton btnSave;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final PasswordCreator pswd;

	/**
	 * Create the frame.
	 */
	public Register() {
		jframe = this;
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.MIN_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.MIN_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblWelcomeToW = new JLabel(
				"Welcome to Winter Encryption Technology! ");
		contentPane.add(lblWelcomeToW, "8, 2, center, default");

		JLabel lblNewLabel_1 = new JLabel(
				"To begin, please create a username and ");
		contentPane.add(lblNewLabel_1, "8, 4, center, default");

		JLabel lblPasswordSoThat = new JLabel(
				"password so that you can encrypt and decrypt ");
		contentPane.add(lblPasswordSoThat, "8, 6, center, default");

		JLabel lblYourDataLater = new JLabel(
				"your data later. Do not lose this information!");
		contentPane.add(lblYourDataLater, "8, 8, center, default");

		JLabel lblUsername = new JLabel("Username");
		contentPane.add(lblUsername, "6, 10, right, default");

		textField = new JTextField();
		contentPane.add(textField, "8, 10, fill, default");
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Password");
		contentPane.add(lblNewLabel, "6, 12, right, default");

		passwordField = new JPasswordField();
		contentPane.add(passwordField, "8, 12, fill, default");
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnSave.doClick();
			}
		});

		pswd = new PasswordCreator();

		btnSave = new JButton("Save");
		contentPane.add(btnSave, "8, 14, left, default");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = textField.getText();
				char[] password = passwordField.getPassword();
				String message = PasswordCreator.checkCredentials(user,
						password);
				if (message != PasswordCreator.VALID) {
					JOptionPane.showMessageDialog(jframe, message);
				} else {
					pswd.setUserAndPassword(user, password);
					int input = JOptionPane.showOptionDialog(null,
							"User created", "Successful",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if (input == JOptionPane.OK_OPTION) {
						new MainFrame();
						MainFrame.main(null);
						jframe.dispose();
					}
				}
			}

		});
	}
}
