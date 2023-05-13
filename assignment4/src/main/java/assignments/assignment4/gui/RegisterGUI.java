package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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

        nameLabel = new JLabel("Masukan nama Anda:");
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridwidth = 1;
        mainPanel.add(nameLabel);

        nameTextField = new JTextField();
        constr.gridx = 0;
        constr.gridy = 2;
        constr.gridwidth = 5;
        mainPanel.add(nameTextField);

        phoneLabel = new JLabel("Masukan nomor handphone Anda:");
        constr.gridx = 0;
        constr.gridy = 4;
        constr.gridwidth = 1;
        mainPanel.add(phoneLabel);

        phoneTextField = new JTextField();
        constr.gridx = 0;
        constr.gridy = 6;
        constr.gridwidth = 5;
        mainPanel.add(phoneTextField);

        passwordLabel = new JLabel("Masukan password Anda:");
        constr.gridx = 0;
        constr.gridy = 8;
        constr.gridwidth = 1;
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        constr.gridx = 0;
        constr.gridy = 10;
        constr.gridwidth = 5;
        mainPanel.add(passwordField);

        registerButton = new JButton("Register");
        constr.gridx = 3;
        constr.gridy = 12;
        constr.gridwidth = 1;
        mainPanel.add(registerButton);

        backButton = new JButton("Kembali");
        constr.gridx = 3;
        constr.gridy = 14;
        constr.gridwidth = 1;
        mainPanel.add(backButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
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
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // TODO
        JOptionPane optionPane = new JOptionPane();

        String name = nameTextField.getText();
        String phoneNumber = phoneTextField.getText();
        String password = String.valueOf(passwordField.getPassword());
        if (name.length() == 0 | phoneNumber.length() == 0 | password.length() == 0){
            optionPane.showMessageDialog(mainPanel,"Semua field di atas wajib diisi");
            return;
        }

        if (!isNumeric(phoneNumber)){
            optionPane.showMessageDialog(mainPanel,"Nomor handphone harus berisi angka!");
            phoneTextField.setText("");
            return;
        }

        Member registeredMember = loginManager.register(name,phoneNumber,password);
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame mainFrame = MainFrame.getInstance();
        if (registeredMember == null){
            optionPane.showMessageDialog(mainPanel,String.format("User dengan nama %s dan %s sudah ada",name,phoneNumber));
            mainFrame.navigateTo(HomeGUI.KEY);
        } else{
            String id = generateId(name,phoneNumber);
            optionPane.showMessageDialog(mainPanel,String.format("Berhasil membuat user dengan ID %s",id));
            mainFrame.navigateTo(HomeGUI.KEY);
        }
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    public static String generateId(String nama, String nomorHP) {
        String id = "";
        id += (nama.split(" ")[0] + "-").toUpperCase();
        id += nomorHP;

        int checksum = 0;
        for (char c : id.toCharArray()) {
            if (Character.isDigit(c))
                checksum += c - '0';
            else if (Character.isLetter(c))
                checksum += (c - 'A') + 1;
            else
                checksum += 7;
        }
        id += String.format("-%02d", checksum % 100);
        return id;
    }
}
