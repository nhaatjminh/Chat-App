package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.net.InetAddress;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ClientUI extends JFrame implements ActionListener {	

	// Socket Related
	private static Socket clientSocket;
	private static int PORT;
	private PrintWriter out;

	private static Connection cnn = null;
	private static Statement stmt = null;

	// JFrame related
	// Chat frame
	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnLogout;
	private JPanel panelNorth;
	private JLabel lblChatClient;
	private JPanel panelNorthSouth;
	//private JLabel lblPort;
	private JLabel lblName;
	private JPanel panelSouth;
	private JButton btnSend;
	private JTextField txtMessage;
	private JTextField txtNickname;
	//private JTextField txtPort;
	private String clientName;
	// Login frame
	final static boolean shouldFill = true;
	final static boolean RIGHT_TO_LEFT = false;
	private JFrame frame = new JFrame("Login");
	private JLabel lblUsername;
	private JTextField txtUsername;
	private JLabel lblPassword, lblConfirm;
	private JPasswordField txtPassword, txtConfirm;
	private JButton btnLogin, btnSignin;
	private JButton btnRegister, btnSignup;
	// notiframe
	private JFrame notiFrame;
	private JLabel notiMsg;
	private JButton exitBtn;


	public GridBagConstraints c = new GridBagConstraints();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientUI cu = new ClientUI();
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(cu);
			// System.setOut(new PrintStream(new TextAreaOutputStream(cu.txtAreaLogs)));
			ConnectDB();
			cu.LoginUI();
			// //Logs
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void ConnectDB() {
        String url = "jdbc:postgresql://ec2-54-156-73-147.compute-1.amazonaws.com:5432/d3jin8vk8l0oce";
        String user = "tcwqysuhuqrrmj";
        String password = "0aaa2e2c622797132360debb3dd3a9ea40be6805e3d8eb6276c282e7ba6beeb3";

        try {
            cnn = DriverManager.getConnection(url, user, password);
            
            stmt = cnn.createStatement();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

	/**
	 * Create the frame.
	 */
	public void CreateComponents(int gridx,int gridy, int ipadx,int ipady){
        c.gridx = gridx;
        c.gridy = gridy;
        c.insets = new Insets(5,5,5,5); 
        c.ipady = ipady; 
        c.ipadx = ipadx; 
	}

	public void FirstOfAddComponents(Container pane){
        if (RIGHT_TO_LEFT) 
            {
                pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
        pane.setLayout(new GridBagLayout());
        if (shouldFill) 
            {
                c.fill = GridBagConstraints.HORIZONTAL;
            }
	}
	
	public void Notification(String msg) {
		notiFrame = new JFrame("Notice");

		notiFrame.getContentPane().removeAll();
		notiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container notiPane = notiFrame.getContentPane();
		FirstOfAddComponents(notiPane);

		CreateComponents(1, 0, 30, 20);
		notiMsg = new JLabel(msg);
		notiPane.add(notiMsg, c);

		CreateComponents(1, 1, 20, 10);
		exitBtn = new JButton("OK");
		notiPane.add(exitBtn, c);
		exitBtn.addActionListener(this);

		notiFrame.pack();
		notiFrame.setVisible(true);
	}
	
	public void LoginUI() {
		frame.getContentPane().removeAll();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = frame.getContentPane();
		FirstOfAddComponents(pane);
		setBounds(200, 200, 400, 300);

		CreateComponents(0, 1, 15, 5);
		lblUsername = new JLabel("Username");
		pane.add(lblUsername, c);

		CreateComponents(1, 1, 60, 5);
		txtUsername = new JTextField();
		pane.add(txtUsername, c);

		CreateComponents(0, 2, 15, 5);
		lblPassword = new JLabel("Password");
		pane.add(lblPassword, c);

		CreateComponents(1, 2, 60, 5);
		txtPassword = new JPasswordField(15);
		pane.add(txtPassword, c);

		CreateComponents(1, 3, 20, 5);
		btnSignin = new JButton("Sign in");
		pane.add(btnSignin, c);
		btnSignin.addActionListener(this);
		btnSignin.setFont(new Font("Tahoma", Font.PLAIN, 12));

		CreateComponents(1, 4, 20, 5);
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(this);
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pane.add(btnRegister, c);

		frame.pack();
        frame.setVisible(true);
	}

	public void RegisterUI() {

		frame.getContentPane().removeAll();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = frame.getContentPane();
		FirstOfAddComponents(pane);
		setBounds(200, 200, 400, 300);
		CreateComponents(0, 2, 15, 5);
		lblUsername = new JLabel("Username");
		pane.add(lblUsername, c);

		CreateComponents(1, 2, 60, 5);
		txtUsername = new JTextField();
		pane.add(txtUsername, c);

		CreateComponents(0, 3, 15, 5);
		lblPassword = new JLabel("Password");
		pane.add(lblPassword, c);

		CreateComponents(1, 3, 60, 5);
		txtPassword = new JPasswordField(15);
		pane.add(txtPassword, c);

		CreateComponents(0, 4, 15, 5);
		lblConfirm = new JLabel("Confirm password");
		pane.add(lblConfirm, c);

		CreateComponents(1, 4, 60, 5);
		txtConfirm = new JPasswordField(15);
		pane.add(txtConfirm, c);

		CreateComponents(1, 6, 20, 5);
		btnLogin = new JButton("Login");
		pane.add(btnLogin, c);
		btnLogin.addActionListener(this);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));

		CreateComponents(1, 5, 20, 5);
		btnSignup = new JButton("Sign up");
		btnSignup.addActionListener(this);
		btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pane.add(btnSignup, c);

		frame.pack();
        frame.setVisible(true);
	}

	public void ChatClientUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panelNorth = new JPanel();
		contentPane.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new BorderLayout(0, 0));

		lblChatClient = new JLabel("CHAT CLIENT");
		lblChatClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatClient.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelNorth.add(lblChatClient, BorderLayout.NORTH);

		panelNorthSouth = new JPanel();
		panelNorth.add(panelNorthSouth, BorderLayout.SOUTH);
		panelNorthSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		lblName = new JLabel("Username:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setForeground(Color.RED);
		panelNorthSouth.add(lblName);

		lblUsername = new JLabel(this.clientName);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setForeground(Color.RED);
		panelNorthSouth.add(lblUsername);

		// lblPort = new JLabel("Port");
		// panelNorthSouth.add(lblPort);

		// txtPort = new JTextField();
		// panelNorthSouth.add(txtPort);
		// txtPort.setColumns(10);

		btnLogout = new JButton("LOG OUT");
		panelNorthSouth.add(btnLogout);
		btnLogout.addActionListener(this);
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		txtAreaLogs = new JTextArea();
		txtAreaLogs.setBackground(Color.BLACK);
		txtAreaLogs.setForeground(Color.WHITE);
		txtAreaLogs.setLineWrap(true);
		txtAreaLogs.setEditable(false);
		txtAreaLogs.setColumns(48);
		scrollPane.setViewportView(txtAreaLogs);

		panelSouth = new JPanel();
		FlowLayout fl_panelSouth = (FlowLayout) panelSouth.getLayout();
		fl_panelSouth.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panelSouth, BorderLayout.SOUTH);

		txtMessage = new JTextField();
		panelSouth.add(txtMessage);
		txtMessage.setColumns(50);

		btnSend = new JButton("SEND");
		btnSend.addActionListener(this);
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelSouth.add(btnSend);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogout) {
				stop();
				this.setVisible(false);
				this.LoginUI();
		} else if(e.getSource() == btnSend) {
			String message = txtMessage.getText().trim();
			if(!message.isEmpty()) {
				out.println(message);
				txtMessage.setText("");
			}
		} else if (e.getSource() == btnRegister) {
			this.RegisterUI();
		} else if (e.getSource() == btnLogin) {
			this.LoginUI();
		} else if (e.getSource() == btnSignin) {
			try {
				ResultSet rs = stmt.executeQuery("select password from users where username = '" + txtUsername.getText() + "';");
				if (rs.next()) {}
				char[] tmp_pass = txtPassword.getPassword();
				String pass = new String(tmp_pass);
				if (rs.getString("password").equals(pass)) {

					frame.getContentPane().removeAll();
					frame.setVisible(false);
					this.clientName = txtUsername.getText();
					
					this.ChatClientUI();
					System.setOut(new PrintStream(new TextAreaOutputStream(this.txtAreaLogs)));
					start();
					this.setVisible(true);
				}
				else {
					this.Notification("Wrong username or password!");
				}
			} catch (Exception ex) {

				this.Notification("Wrong username or password!");
			}
			
		} else if (e.getSource() == btnSignup) {
			try {
				char[] tmp_pass = txtPassword.getPassword();
				String pass = new String(tmp_pass);
				char[] tmp_con = txtConfirm.getPassword();
				String con = new String(tmp_con);
				if (pass.equals(con)) {
					ResultSet rs = stmt.executeQuery("insert into users values('" + txtUsername.getText() + "','"+ pass +"');");
				}
				else {
					this.Notification("Password doesnot match!");
				}
			} catch (Exception ex) {
				//ex.printStackTrace();
				this.Notification("This username existed!");
			}
			
		} else if (e.getSource() == exitBtn) {
			notiFrame.getContentPane().removeAll();
			notiFrame.setVisible(false);
		}
		//Refresh UI
		refreshUIComponents();
	}

	public void refreshUIComponents() {

	}

	public void start() {
		try {
			//PORT = Integer.parseInt(txtPort.getText().trim());
			//clientName = txtNickname.getText().trim();
			clientSocket = new Socket("192.168.1.4", 8000);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			new Thread(new Listener()).start();
			//send name
			out.println(clientName);
		} catch (Exception err) {
			addToLogs("[ERROR] "+err.getLocalizedMessage());
		}
	}

	public void stop(){
		if(!clientSocket.isClosed()) {
			try {
				clientSocket.close();
			} catch (IOException e1) {}
		}
	}

	public static void addToLogs(String message) {
		System.out.printf("%s %s\n", ServerUI.formatter.format(new Date()), message);
	}

	private static class Listener implements Runnable {
		private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for(;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty())) addToLogs(read);
				}
			} catch (IOException e) {
				return;
			}
		}

	}
}
