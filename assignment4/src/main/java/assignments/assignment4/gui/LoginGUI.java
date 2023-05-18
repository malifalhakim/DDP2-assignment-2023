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
        super(new BorderLayout()); // Setup layout
        this.loginManager = loginManager;

        // Set up main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Menambahkan semua component pada mainPanel dengan GridBagLayout
     * */
    private void initGUI() {
        // Menambahkan semua component pada mainPanel
        GridBagConstraints constr = new GridBagConstraints();
        constr.gridx = 0;
        constr.gridy = GridBagConstraints.RELATIVE;
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.anchor = GridBagConstraints.CENTER;
        constr.weightx = 1.0;
        constr.weighty = 1.0;
        constr.insets = new Insets(10,10,10,10);

        idLabel = new JLabel("Masukan ID Anda:");
        mainPanel.add(idLabel,constr);

        idTextField = new JTextField();
        mainPanel.add(idTextField,constr);

        passwordLabel = new JLabel("Masukan password Anda:");
        mainPanel.add(passwordLabel,constr);

        passwordField = new JPasswordField();
        mainPanel.add(passwordField,constr);

        constr.fill = GridBagConstraints.NONE;

        loginButton = new JButton("Login");
        mainPanel.add(loginButton,constr);

        backButton = new JButton("Kembali");
        mainPanel.add(backButton,constr);

        // Menambahkan listener pada setiap Button
        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> handleBack());
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
        // Cek ID dan Password terdapat dalam memberSystem atau EmployeeSystem
        boolean isValid = mainFrame.login(idTextField.getText(),String.valueOf(passwordField.getPassword()));

        if (isValid){
            SystemCLI systemCLI = loginManager.getSystem(idTextField.getText()); // Mendapat SystemCLI sesuai tipe ID
            if (systemCLI instanceof MemberSystem)
                mainFrame.navigateTo(MemberSystemGUI.KEY);
            else if (systemCLI instanceof EmployeeSystem)
                mainFrame.navigateTo(EmployeeSystemGUI.KEY);
        } else{
            JOptionPane.showMessageDialog(mainPanel,"ID atau password invalid","Invalid ID or Password",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Kosongkan Field
        idTextField.setText("");
        passwordField.setText("");
    }
}
