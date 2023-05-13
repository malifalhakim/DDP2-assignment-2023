package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        GridBagConstraints constr = new GridBagConstraints();

        idLabel = new JLabel("Masukan ID Anda:");
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridwidth = 1;
        mainPanel.add(idLabel);

        idTextField = new JTextField();
        constr.gridx = 0;
        constr.gridy = 2;
        constr.gridwidth = 5;
        mainPanel.add(idTextField);

        passwordLabel = new JLabel("Masukan password Anda:");
        constr.gridx = 0;
        constr.gridy = 4;
        constr.gridwidth = 1;
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        constr.gridx = 0;
        constr.gridy = 6;
        constr.gridwidth = 5;
        mainPanel.add(passwordField);

        loginButton = new JButton("Login");
        constr.gridx = 3;
        constr.gridy = 8;
        constr.gridwidth = 1;
        mainPanel.add(loginButton);

        backButton = new JButton("Kembali");
        constr.gridx = 3;
        constr.gridy = 10;
        constr.gridwidth = 1;
        mainPanel.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText("");
        passwordField.setText("");
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        MainFrame mainFrame = MainFrame.getInstance();
        boolean isValid = mainFrame.login(idTextField.getText(),String.valueOf(passwordField.getPassword()));
        if (isValid){
            SystemCLI systemCLI = loginManager.getSystem(idTextField.getText());
            idTextField.setText("");
            passwordField.setText("");
            if (systemCLI instanceof MemberSystem)
                mainFrame.navigateTo(MemberSystemGUI.KEY);
            else if (systemCLI instanceof EmployeeSystem)
                mainFrame.navigateTo(EmployeeSystemGUI.KEY);
        } else{
            JOptionPane optionPane = new JOptionPane();
            optionPane.showMessageDialog(mainPanel,"ID atau password anda salah","Login Gagal",JOptionPane.ERROR_MESSAGE);
        }
    }
}
